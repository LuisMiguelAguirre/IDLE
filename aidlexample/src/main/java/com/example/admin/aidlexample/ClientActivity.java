package com.example.admin.aidlexample;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class ClientActivity extends AppCompatActivity {
    private IMainService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);


        Intent serviceIntent = new Intent().setComponent(new ComponentName(
                "com.example.admin.idle",
                "com.example.admin.idle.MyBoundService"));
        startService(serviceIntent);
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = IMainService.Stub.asInterface(iBinder);
            performListing();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
        }
    };

    private void performListing() {
        try {
            List<String> dummyData = mService.listFiles();
            for (String number: dummyData) {
                Log.d("App2: ", "performListing: " + number);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            mService.exit();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
