package com.example.oneapptorulethemall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.widget.Toast;


public class NetworkChangeReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (isOnline(context)) {
                Toast.makeText(context, "Internet Connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Internet NOT Connected", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public boolean isOnline(Context context){
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            boolean up = false;
            if(info != null && info.isConnected()){
                up = true;
            }
            return (info!=null&&up);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }
}
