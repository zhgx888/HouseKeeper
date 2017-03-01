package edu.feicui.app.phone.base.util;

import android.content.Context;
import android.media.MediaPlayer;

import edu.feicui.app.phone.R;

/**
 * Created by Administrator on 2017/1/31.
 */

public class MusicUtil {
    Context context;
    MediaPlayer mediaPlayer;

    public MusicUtil(Context mCtx) {
        context = mCtx;
    }

    public void play() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.kdc);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }
}
