package com.example.oneapptorulethemall;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneapptorulethemall.activities.MainActivity;

import java.util.jar.Attributes;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);



        // output data
        SQLHelper helper = new SQLHelper(this);
        ListView listView = (ListView) findViewById(R.id.listview);
        MyAdapter adapter = new MyAdapter(this,helper.getCursor());
        listView.setAdapter(adapter);
        TextView textView = (TextView) findViewById(R.id.output);



        listView.setClickable(true);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor item= (Cursor) adapter.getItem(position);
                String  Name= "";
                item.moveToPosition(position);
                if(item.getColumnIndex("name") != -1){
                    @SuppressLint("Range") String text = item.getString(item.getColumnIndex("name"));
                    Log.d("PP", text);
                    Name = text;
                }
                if(helper.doesUsernameExist(Name)){
                    User user;
                    user = new User();
                    helper.setProfile(user,Name);
                    if(user.permission.equals("default")){
                        Log.d("PP", "default to editor:");
                        helper.updatePer(Name, "editor");
                        textView.setText(Name + " is now editor");
                    }else if(user.permission.equals("editor")){
                        Log.d("PP", "editor to admin:");
                        helper.updatePer(Name, "admin");
                        textView.setText(Name + " is now admin");
                    }else{
                        Log.d("PP", "admin to default:");
                        helper.updatePer(Name, "default");
                        textView.setText(Name + " is now default");
                    }
                }
            }
        });
    }






    public void back(View view) {
        startActivity(new Intent(this, Transfer_page.class));
        finish();
    }
}

