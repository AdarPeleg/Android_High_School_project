package com.example.oneapptorulethemall.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.oneapptorulethemall.Transfer_page;
import com.example.oneapptorulethemall.R;
import com.example.oneapptorulethemall.Register_page;
import com.example.oneapptorulethemall.Transfer_page;

import java.util.Map;
import java.util.HashMap;

public class Splash_screen_book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_book);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // gets a value what activity to go to
                int id = getIntent().getIntExtra("activity", 0);
                // use of hashmap for easy search
                Map<Integer, Class<?>> activityMap = new HashMap<>();
                activityMap.put(2, Register_page.class);
                activityMap.put(3, notes_page.class);
                activityMap.put(4, Transfer_page.class);
                activityMap.put(5, MainActivity.class);
                // Add more entries to the map as needed

                // Log.d("", String.valueOf(id)); // for debugg
                Class<?> cls = activityMap.get(id);
                if (cls == null) {
                    cls = MainActivity.class;
                }

                Intent intent = new Intent(getApplicationContext(), cls);
                startActivity(intent);
                finish();

                /*
                old code

                //int p= intent.getExtras().getInt("activity");
                //@SuppressLint("ResourceType") int x = findViewById(R.layout.activity_the_shelf).getId();
                //Log.d("sssssssssss",String.valueOf(x));
                //if(p ==x){startActivity(new Intent(getApplicationContext(),TheShelf.class));
                //}

                 */

            }
        },3000);


    }
}