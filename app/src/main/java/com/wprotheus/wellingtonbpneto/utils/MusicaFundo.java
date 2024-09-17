package com.wprotheus.wellingtonbpneto.utils;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.wprotheus.wellingtonbpneto.R;

public class MusicaFundo extends Service {
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this.getApplicationContext(),
                R.raw.beach_party_kevin_macleod_musica_sem_direitos_autorais);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}