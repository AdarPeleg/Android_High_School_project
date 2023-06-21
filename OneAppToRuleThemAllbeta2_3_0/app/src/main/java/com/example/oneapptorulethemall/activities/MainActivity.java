package com.example.oneapptorulethemall.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneapptorulethemall.NetworkChangeReciver;
import com.example.oneapptorulethemall.R;
import com.example.oneapptorulethemall.Register_page;
import com.example.oneapptorulethemall.SQLHelper;
import com.example.oneapptorulethemall.User;
import com.google.android.material.button.MaterialButton;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {
        // SharedPreferences sp;
    User user;
    SharedPreferences sharedPreferences;
    NetworkChangeReciver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //cosmetic
        getSupportActionBar().hide();


        sharedPreferences = getSharedPreferences("active_user",0);
        user=new User();

        SQLHelper helper= new SQLHelper(this);




        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        MaterialButton registerbtn = (MaterialButton) findViewById(R.id.registerbtn);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    Toast.makeText(MainActivity.this,"Success!", Toast.LENGTH_LONG).show();


                    // give admin permission
                    String name = "admin";
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name",name);
                    editor.commit();
                    // original
                    // Intent intent = new Intent(getApplicationContext(), TheShelf.class);
                    // startActivity(intent);



                    Intent intent = new Intent(getApplicationContext(), Splash_screen_book.class);
                    intent.putExtra("activity", 4);
                    startActivity(intent);



                }else {
                    if(helper.doesUsernameExist(username.getText().toString())){
                        helper.setProfile(user,username.getText().toString()); // setting profile!
                        if(user.getPassword().equals(password.getText().toString())){
                            Log.d("test", "user found: ");

                            String name = user.getPermission();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name",name);
                            editor.commit();

                            Intent intent = new Intent(getApplicationContext(), Splash_screen_book.class);
                            intent.putExtra("activity", 4);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"Login failed",
                                Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent register = new Intent(getApplicationContext(), Register_page.class);
                startActivity(register);
            }
        });



        broadcastReceiver = new NetworkChangeReciver();
        registerNetworkBroadcastReciver();
    }



    protected void registerNetworkBroadcastReciver(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
    protected void unregisterNetwork(){
        try{
            unregisterReceiver(broadcastReceiver);
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetwork();
    }
}