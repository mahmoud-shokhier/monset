package com.example.abdelhameedahmed.monset;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;

import cafe.adriel.androidaudioconverter.AndroidAudioConverter;

/**
 * Created by abdelhameed ahmed on 6/4/2018.
 */

public class jop extends Service {
    media media;

    @Override
    public void onCreate() {

        super.onCreate();
        media=new media(getApplicationContext());


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            media.star_record();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Bundle bundle=new Bundle();
        int tag=bundle.getInt("tag");
        media.stop_service(tag);
        //Toast.makeText(getApplicationContext(),tag+"",Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
