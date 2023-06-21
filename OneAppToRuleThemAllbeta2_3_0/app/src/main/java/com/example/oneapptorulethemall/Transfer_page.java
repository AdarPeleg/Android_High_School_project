package com.example.oneapptorulethemall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.oneapptorulethemall.activities.MainActivity;
import com.example.oneapptorulethemall.activities.Splash_screen_book;
import com.example.oneapptorulethemall.activities.notes_page;
import com.ramotion.circlemenu.CircleMenuView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



public class Transfer_page extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_page);
        sharedPreferences = getSharedPreferences("active_user",0);
        String name = sharedPreferences.getString("name", null);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        final CircleMenuView menu = findViewById(R.id.circle_menu);
        menu.setEventListener(new CircleMenuView.EventListener() {
            @Override
            public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuOpenAnimationStart");
            }

            @Override
            public void onMenuOpenAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuOpenAnimationEnd");
            }

            @Override
            public void onMenuCloseAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuCloseAnimationStart");
            }

            @Override
            public void onMenuCloseAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuCloseAnimationEnd");
            }


            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationStart| index: " + index);
            }

            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationEnd| index: " + index);

                // sp for admin actions
                String name = sharedPreferences.getString("name", null);
                sharedPreferences = getSharedPreferences("active_user",0);

                switch (index){

                    case 0: // Main/ login
                        //SharedPreferences.Editor editor = sharedPreferences.edit();
                        //editor.putString("name","");
                        //editor.commit();
                        Intent intent0 = new Intent(getApplicationContext(), Splash_screen_book.class);
                        intent0.putExtra("activity", 5);
                        startActivity(intent0);
                        break;

                    case 1:

                        Intent intent1 = new Intent(getApplicationContext(), ExampleTH.class);
                        startActivity(intent1);
//                        Toast.makeText(Transfer_page.this,
//                                "your permission is " + name, Toast.LENGTH_SHORT).show();
                        break;

                    case 4: // notes
                        Intent intent = new Intent(getApplicationContext(), Splash_screen_book.class);
                        intent.putExtra("activity", 3);
                        startActivity(intent);
                        break;

                    case 3: // settings
                        if(name.equals("admin")){
                            Intent intent3 = new Intent(getApplicationContext(), ResultActivity.class);
                            startActivity(intent3);
                            finish();
                            break;
                        }else{
                            Toast.makeText(Transfer_page.this,"Admin only page!",
                                    Toast.LENGTH_LONG).show();
                            break;
                        }

                    case 2: // service music
                        Intent intent2 = new Intent(getApplicationContext(), MyService.class);
                        startService(intent2);
                        break;
                }
            }

            @Override
            public boolean onButtonLongClick(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonLongClick| index: " + index);
                return true;
            }

            @Override
            public void onButtonLongClickAnimationStart(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonLongClickAnimationStart| index: " + index);
            }

            @Override
            public void onButtonLongClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonLongClickAnimationEnd| index: " + index);

                switch (index){
                    case 4:
                        Intent intent = new Intent(getApplicationContext(), notes_page.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2: // service music
                        Intent intent2 = new Intent(getApplicationContext(), MyService.class);
                        stopService(intent2);
                        break;
                }
            }
        });
    }
}
