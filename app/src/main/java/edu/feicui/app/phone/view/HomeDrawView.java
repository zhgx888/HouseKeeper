package edu.feicui.app.phone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import edu.feicui.app.phone.R;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public class HomeDrawView extends View {

    public HomeDrawView(Context context) {
        super(context);
    }

    public HomeDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private RectF rfRise = null;
    private RectF rfFall = null;

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        rfRise = new RectF(1, 1, viewWidth - 1, viewHeight - 1);
        rfFall = new RectF(0, 0, viewWidth , viewHeight);

    }


    private float flRise = 0;
    private float flFall = 0;
    private float flRaw = 0;
    public boolean flag;
    public int time;

    public void setParamsWithAnim(float fll) {
        time=1;
        this.flRaw = fll;

        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (flag) {
                    flFall = flFall - 3;
                    if (flFall <= 0) {
                        flRise = 0;
                        flag = false;
                    }
                } else {
                    flRise = flRise + 3;
                    if (flRise >= flRaw) {
                        flFall = flRaw;
                        flRise = flRaw;
                    }
                    if (flRise == flRaw) {
                        timer.cancel();
                        flag = true;
                        time = 0;
                    }
                }
                postInvalidate();
            }
        };
        timer.schedule(timerTask, 300, 10);
        /**
         * 2.线程结束方式停止
         */
//        Thread thread = new Thread() {
//            public void run() {
//                while (flag) {
//                    fl = fl + 4;
//                    postInvalidate();
//                    if (fl >= flRaw) {
//                        fl = flRaw;
//                    }
//                    if (fl == flRaw) {
//                        flag = false;
//                    }
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        };
//        thread.start();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(getResources().getColor(R.color.colorGreen));
        paint.setAntiAlias(true);
        canvas.drawArc(rfRise, -90, flRise, true, paint);
        if (flRise >= 120) {
            paint.setColor(getResources().getColor(R.color.colorBlue));
            paint.setAntiAlias(true);
            canvas.drawArc(rfRise, -90, flRise, true, paint);
        }
        if (flRise >= 240) {
            paint.setColor(getResources().getColor(R.color.colorRed));
            paint.setAntiAlias(true);
            canvas.drawArc(rfRise, -90, flRise, true, paint);
        }
        if (flag) {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(getResources().getColor(R.color.colorWhite));
            paint.setAntiAlias(true);
            canvas.drawArc(rfFall, flRise - 90, flFall - flRise, true, paint);
        }
    }
}
