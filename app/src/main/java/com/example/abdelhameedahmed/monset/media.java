package com.example.abdelhameedahmed.monset;

import android.app.Notification;
import android.content.Context;
import android.icu.util.Calendar;
import android.media.AudioFormat;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.VibrationEffect;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
import cafe.adriel.androidaudioconverter.callback.IConvertCallback;
import cafe.adriel.androidaudioconverter.callback.ILoadCallback;

/**
 * Created by abdelhameed ahmed on 6/4/2018.
 */

public class media {
    MediaPlayer player;
    MediaRecorder recorder;
    File file,file_history;
    String path;
    String wav_path;
    Context context;
    NotificationCompat.Builder builder;
    NotificationManagerCompat managerCompat;
    //processing processing;
    processing2 processing2;
    Calendar calendar;
    FileOutputStream outputStream;
    OutputStreamWriter writer;
    int tag;

    public media(Context context){
        this.context=context;
        recorder=new MediaRecorder();
        file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),"fin.m4a");
        path=file.getAbsolutePath();
        player=new MediaPlayer();
        //processing=new processing(this.context);
        processing2=new processing2(this.context);
        wav_path=null;
        tag=1;
        file_history=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"history.txt");
        try {
            outputStream=new FileOutputStream(file_history,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer=new OutputStreamWriter(outputStream);

        calendar=Calendar.getInstance();

        AndroidAudioConverter.load(context, new ILoadCallback() {
            @Override
            public void onSuccess() {
            }
            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
        //Toast.makeText(this.context,path,Toast.LENGTH_LONG).show();

    }


    public void star_record() throws IOException,IllegalStateException {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(path);
        recorder.setMaxDuration(2000);
        recorder.setAudioSamplingRate(16000);
        recorder.prepare();
        recorder.start();
        Toast.makeText(this.context,"started...",Toast.LENGTH_SHORT).show();
        stop_record();
    }


    public void stop_record(){

        recorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mediaRecorder,int i, int i1) {
                if(i==MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED){
                    mediaRecorder.stop();
                    Toast.makeText(context,"stopped...",Toast.LENGTH_SHORT).show();

                    IConvertCallback callback=new IConvertCallback() {
                        @Override
                        public void onSuccess(File file) {
                            wav_path= file.getAbsolutePath();
                            //Toast.makeText(context, "SUCCESS: " +wav_path,Toast.LENGTH_SHORT).show();
                            //processing.functionallity(wav_path);
                            String s= processing2.process_f(wav_path);
                            int image_icon=0;
                            switch(s){
                                case "babycry":
                                    image_icon=R.drawable.cryingbaby;
                                    break;
                                case "breaking":
                                    image_icon=R.drawable.wineglass;
                                    break;
                                case "doorKnock":
                                    image_icon=R.drawable.knocking;
                                    //Toast.makeText(context,"ffg",Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    image_icon=R.drawable.ear;

                            }


                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).
                                    setContentTitle("Attention").
                                    setContentText(s).
                                    setPriority(NotificationCompat.PRIORITY_MAX).
                                    setVibrate(new long[]{0,1000,1000}).
                                    setVisibility(NotificationCompat.VISIBILITY_PUBLIC).
                                    setSmallIcon(image_icon).
                                    setColor(20);

                            managerCompat= NotificationManagerCompat.from(context);
                            managerCompat.notify(0,mBuilder.build());


                            try {
                                writer.write(s+"\t"+calendar.getTime().toString()+"\r\n");
                                writer.flush();
                            } catch (IOException e) {
                                Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                            }

                            //Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                            //test_sound();

                            if(tag==1){
                                try {
                                    star_record();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else {

                                Toast.makeText(context,"canceled",Toast.LENGTH_LONG).show();

                            }




                        }


                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(context, "fail: " +e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    };


                    AndroidAudioConverter.with(context).setFile(file).
                            setFormat(cafe.adriel.androidaudioconverter.model.AudioFormat.WAV).
                            setCallback(callback).
                            convert();








                }

            }
        });
    }

    ///////test
    public void test_sound() {
        Toast.makeText(context,"played...",Toast.LENGTH_SHORT).show();
        try {

            player.setDataSource(wav_path);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void stop_service(int tag){
        this.tag=tag;
    }


}

