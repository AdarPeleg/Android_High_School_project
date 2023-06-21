package com.example.oneapptorulethemall;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.oneapptorulethemall.activities.Splash_screen_book;
import com.google.android.material.button.MaterialButton;


public class Register_page extends AppCompatActivity implements View.OnClickListener {

    EditText edName,edPass,edAge,edEmail;
    SQLHelper helper;
    SharedPreferences sharedPreferences;
    Cursor cursor;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        getSupportActionBar().hide();



        edName = (EditText) findViewById(R.id.username_input);
        edPass = (EditText) findViewById(R.id.password_input1);
        edAge = (EditText) findViewById(R.id.age_input);
        edEmail = (EditText) findViewById(R.id.email_input);


        helper = new SQLHelper(this);
        cursor = helper.getCursor();
        cursor.moveToFirst();
        save = findViewById(R.id.savebtn);
        save.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("active_user",0);

    }


//    public void save(View view) {
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.getWritableDatabase().close(); //close the data base
    }

    @Override
    public void onClick(View view) {
        // data from filed

        // can't get the same name!
        if(helper.doesUsernameExist(edName.getText().toString())){
            Toast.makeText(this, "name is taken!", Toast.LENGTH_SHORT).show();
            return;
        }
        String name = edName.getText().toString();
        int age = Integer.parseInt(edAge.getText().toString());
        String email = edEmail.getText().toString();
        String pass = edPass.getText().toString();

        // sheared pref
        String per = "default"; // default permission
        // active login
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",name);
        editor.commit();
        // int interger= cursor.getInt(cursor.getInt(0));
        // editor.putInt("id",interger);
        MyAdapter adapter = new MyAdapter(this,helper.getCursor());




        helper.insertData(new User(name,age,email,pass, per));
        Toast.makeText(this, "data saved", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Splash_screen_book.class);
        intent.putExtra("activity", 4);
        startActivity(intent);
        finish();
    }
}