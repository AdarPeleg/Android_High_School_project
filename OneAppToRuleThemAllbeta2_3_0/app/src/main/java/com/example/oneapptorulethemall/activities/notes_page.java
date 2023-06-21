package com.example.oneapptorulethemall.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oneapptorulethemall.R;
import com.example.oneapptorulethemall.activities.CreateNoteActivity;
import com.example.oneapptorulethemall.adapters.NotesAdapter;
import com.example.oneapptorulethemall.database.NotesDatabase;
import com.example.oneapptorulethemall.entities.Note;
import com.example.oneapptorulethemall.listeners.NotesListener;

import java.util.ArrayList;
import java.util.List;

public class notes_page extends AppCompatActivity implements NotesListener {


    public static final int REQUEST_CODE_ADD_NOTE = 1; // new note!!
    public static final int REQUEST_CODE_UPDATE_NOTE = 2; // update
    public static final int REQUEST_CODE_SHOW_NOTES = 3; // display all notes

    // for admin actions
    SharedPreferences sharedPreferences;


    private RecyclerView notesRecyclerView;
    private List<Note> noteList;
    private NotesAdapter notesAdapter;

    private TextView textView;
    private int noteClickedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_page);
        // sp for admin actions
        sharedPreferences = getSharedPreferences("active_user",0);
        String name = sharedPreferences.getString("name", null);
        textView = findViewById(R.id.inputSearch);
        if(name.equals("default")){
            textView.setText("you can view only!");
        }else if(name.equals("editor")){
            textView.setText("you can edit and view");
        }else{
            textView.setText("you can edit, view and delete notes");
        }
        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);


        if( name.equals("default")){
            imageAddNoteMain.setVisibility(View.GONE);
        }

        // on click
        imageAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getApplicationContext(), CreateNoteActivity.class),
                        REQUEST_CODE_ADD_NOTE
                );
            }
        });

        // notes recycler view
        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );


        noteList= new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList, this);
        notesRecyclerView.setAdapter(notesAdapter);


        // render notes
        getNotes(REQUEST_CODE_SHOW_NOTES, false);
    }



    // func for note update
    @Override
    public void onNotesClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("note", note);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);

    }

    // using async
    private void getNotes(final int requestCode, final boolean isNoteDeleted) {

        @SuppressLint("StaticFieldLeak")
        class GetNoteTask extends AsyncTask<Void, Void, List<Note>>{

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase
                        .getDataBase(getApplicationContext())
                        .noteDao().getAllNotes();
            }


            /*
            * if note list is empty it means the app just started because it's globle variable
            *  and we need to  update(add all notes) from db to list.
            * else that means that we need to add only one note
            * and scroll recycler view to the top
            * */
            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                Log.d("my_notes", notes.toString()); // debugging
                Log.d("my_notes_size", String.valueOf(noteList.size())); // debugging

                // sp for admin actions
                sharedPreferences = getSharedPreferences("active_user",0);
                String name = sharedPreferences.getString("name", null);

                if(requestCode == REQUEST_CODE_SHOW_NOTES){
                    noteList.addAll(notes);
                    notesAdapter.notifyDataSetChanged();
                }else if(requestCode == REQUEST_CODE_ADD_NOTE){
                    noteList.add(0, notes.get(0));
                    notesAdapter.notifyItemInserted(0);
                    notesRecyclerView.smoothScrollToPosition(0);
                }else if(requestCode == REQUEST_CODE_UPDATE_NOTE){
                    noteList.remove(noteClickedPosition);
                    if(isNoteDeleted){
                        notesAdapter.notifyItemRemoved(noteClickedPosition);
                    }else{
                        noteList.add(noteClickedPosition, notes.get(noteClickedPosition));
                        notesAdapter.notifyItemChanged(noteClickedPosition);
                    }
                }



                // old version:
//                if(noteList.size() ==0){
//                    noteList.addAll(notes);
//                    Log.d("my_notes_size", String.valueOf(noteList.size())); // debugging
//                    notesAdapter.notifyDataSetChanged();
//                }else{
//                    noteList.add(0, notes.get(0));
//                    notesAdapter.notifyItemInserted(0);
//                }
//                notesRecyclerView.smoothScrollToPosition(0);

            }
        }
        new GetNoteTask().execute();
    }


    // update note real time (after save button pressed!)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // sp for admin actions
        sharedPreferences = getSharedPreferences("active_user",0);
        String name = sharedPreferences.getString("name", null);

        // result code checks if createnote activity is done
        if(requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK){
            getNotes(REQUEST_CODE_ADD_NOTE, false);
        }else if(requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK
                ){// && !name.equals("default")
            if(data != null){
                getNotes(REQUEST_CODE_UPDATE_NOTE, data.getBooleanExtra("isNoteDeleted", false));
            }
        }

    }
}