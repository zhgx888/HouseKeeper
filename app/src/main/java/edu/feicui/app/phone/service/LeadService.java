package edu.feicui.app.phone.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import edu.feicui.app.phone.base.util.MusicUtil;

public class LeadService extends Service {
    MusicUtil musicUtil;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicPlay();
    }
    public class MusicPlay extends Binder {
        public LeadService getService(){
            return LeadService.this;
        }
    }
    public void play(){
        musicUtil=new MusicUtil(this);
        musicUtil.play();
    }
    public void stop(){
        if (musicUtil != null) {
            musicUtil.stop();
        }
    }

    @Override
    public void onDestroy() {
        Log.i("@@@","onDestroy");
        super.onDestroy();
        stop();
    }
}
