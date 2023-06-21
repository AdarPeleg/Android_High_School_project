package com.example.oneapptorulethemall;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class MyService extends Service {
    private MediaPlayer mMediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music1 );
        mMediaPlayer.setLooping(false);
        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //super.onStart(intent, startId);
        mMediaPlayer.start();
        Toast.makeText(this, "Service start", Toast.LENGTH_SHORT).show();
    }

    public void onDestroy(){
        mMediaPlayer.stop();
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
    }
}
