package com.example.admin.idle;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.admin.aidlexample.IMainService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyBoundService extends Service {
    public static final String TAG="MyBoundServiceTag";
    IBinder iBinder = new MyBinder();
    List<String> dummyData = new ArrayList<>();

    public MyBoundService() {
    }

    public class MyBinder extends Binder {

        public MyBoundService getService(){

            return MyBoundService.this;
        }
    }

    private final IMainService.Stub mBinder = new IMainService.Stub() {
        public List<String> listFiles() throws RemoteException {
            initData(10);
            List<String> toSend = getDummyData(); // dummyData
            return toSend;
        }

        @Override
        public void exit() throws RemoteException {
            stopSelf();
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG, "onBind: "+ intent.getStringExtra("data"));
    return mBinder;//iBinder;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
      //  stopSelf();
        super.onDestroy();
    }


    public void initData(int initializedStr){
        for (int i = 0; i < initializedStr; i++) {
            dummyData.add(String.valueOf(new Random().nextInt(100)));
        }
    }



    public List<String> getDummyData(){
        return dummyData;
    }
}
