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

public class SoftMgrDrawView extends View {

    public SoftMgrDrawView(Context context) {
        super(context);
    }

    public SoftMgrDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private RectF rf = null;

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        rf = new RectF(0, 0, viewWidth, viewHeight);
    }


    private float fl = 0;
    private float flRaw = 0;
    public boolean flag;

    public void setParamsWithAnim(float fll) {
        this.flRaw = fll;
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                fl = fl + 3;
                postInvalidate();
                if (fl == 360) {
                    timer.cancel();
                    flag = true;
                }
            }
        };
        timer.schedule(timerTask, 10, 10);
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
        paint.setColor(getResources().getColor(R.color.colorSkyBlue));
        paint.setAntiAlias(true);
        canvas.drawArc(rf, -90, fl, true, paint);
        if (fl >= flRaw) {
            paint.setColor(getResources().getColor(R.color.colorLightGreen));
            paint.setAntiAlias(true);
            canvas.drawArc(rf, flRaw - 90, fl - flRaw, true, paint);
        }
    }
}
