package voicerecorder.ateam.com.voicerecorder.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import voicerecorder.ateam.com.voicerecorder.Notification.NewMessageNotification;

public class MyService extends Service {

    MediaRecorder mMediaRecorder;
    final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    boolean b;
    NewMessageNotification notification;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Strated", Toast.LENGTH_SHORT).show();
        startRecording();
        Log.e("Strated", String.valueOf(SystemClock.currentThreadTimeMillis()));
        return START_STICKY;

    }
    public void startRecording() {

        Toast.makeText(this,"Service created!!", Toast.LENGTH_SHORT).show();
        // Media Recorder
        //String path = createFileAndPath();

        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mMediaRecorder.setOutputFile(createFileAndPath().getAbsolutePath());
        Toast.makeText(this, "!Recording Started!", Toast.LENGTH_SHORT).show();

            try {
                mMediaRecorder.prepare();
                mMediaRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }


    }

    private File createFileAndPath() {
        File folder = new File(PATH + "/Voice Recordings");
        if(!folder.exists())
            folder.mkdir();
        String filename = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss",Locale.getDefault()).format(new Date());
        File file = new File(folder, "VR_"+filename+".mp4");
        return file;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Ended", String.valueOf(SystemClock.currentThreadTimeMillis()));
        notification = new NewMessageNotification();
        notification.notify(this,createFileAndPath().getName(),123,createFileAndPath().getName());
        if(mMediaRecorder!=null) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
        }
        Toast.makeText(this, "Destroyed!!", Toast.LENGTH_SHORT).show();
    }
}
