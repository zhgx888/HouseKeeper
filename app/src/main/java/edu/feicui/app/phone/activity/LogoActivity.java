package edu.feicui.app.phone.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;

public class LogoActivity extends BaseActivity {
    ImageView mImgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        hideActionBar();
        mImgLogo = (ImageView) findViewById(R.id.img_logo);
        mImgLogo.setBackgroundResource(R.drawable.frame_logo);
        AnimationDrawable animDraw = (AnimationDrawable) mImgLogo.getBackground();
        animDraw.start();
        Animation animAlpha = AnimationUtils.loadAnimation(LogoActivity.this, R.anim.alpha);
        mImgLogo.startAnimation(animAlpha);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(HomeActivity.class,R.anim.in_down,R.anim.out_up);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onClickActionBar(int type) {

    }
}
