package edu.feicui.app.phone.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.MemoryBiz;
import edu.feicui.app.phone.view.HomeDrawView;

import static edu.feicui.app.phone.R.id.drawView;

public class HomeActivity extends BaseActivity {
    TextView mTxtDraw;
    MemoryBiz memoryBiz;
    int time = 0;
    /*----------跑表----------*/
    HomeDrawView homeDrawView = null;

    /**
     * Called when the activity is first created. 第一次被创建时调用活动。
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memoryBiz=new MemoryBiz(this);
        setContentView(R.layout.activity_home);
        initActionBar();
        homeDrawView = (HomeDrawView) findViewById(drawView);
        mTxtDraw = (TextView) findViewById(R.id.txt_percentage);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        homeDrawView.setParamsWithAnim(memoryBiz.separatePercentage());
        mTxtDraw.setText(memoryBiz.separatePercentage + "%");

    }

    void initActionBar() {
        setLeftIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        setMiddleTitle(this.getString(R.string.app_name));
        setRightIcon(getResources().getDrawable(R.mipmap.ic_child_configs));
    }

    public void onClickHome(View v) {
        switch (v.getId()) {
            case R.id.phoneSpeedGo://手机加速
                super.startActivity(AccelerateActivity.class,R.anim.in_down,R.anim.out_up);
                break;
            case R.id.softwareManagement://软件管理
                super.startActivity(SoftMgrActivity.class,R.anim.in_down,R.anim.out_up);
                break;
            case R.id.phoneTest://手机检测
                super.startActivity(PhoneMgrActivity.class,R.anim.in_down,R.anim.out_up);
                break;
            case R.id.communicationBook://通讯大全
                super.startActivity(PhoneBookGridActivity.class,R.anim.in_down,R.anim.out_up);
                break;
            case R.id.ileManagement://文件管理
                super.startActivity(FileMgrActivity.class,R.anim.in_down,R.anim.out_up);
                break;
            case R.id.clearGarbage://垃圾清理
                super.startActivity(ClearGarbageActivity.class,R.anim.in_down,R.anim.out_up);
                break;
            case R.id.img_speedUp_btn://主页手机加速
                time = homeDrawView.time;
                if (time == 0) {
                    time = 1;
                    memoryBiz.killAllProcesses();
                    memoryBiz.rootDirectory();
                    float num = memoryBiz.separatePercentage();
                    homeDrawView.setParamsWithAnim(num);
                    break;
                }
        }
    }

    @Override
    protected void onClickActionBar(int type) {
        if (type==ACTION_LEFT_ICON) {
            Bundle bundle=new Bundle();
            bundle.putString("className",HomeActivity.this.getClass().getSimpleName());
            startActivity(AboutInfoActivity.class,bundle);
        } else if (type == ACTION_RIGHT_ICON) {
            startActivity(SettingActivity.class);
        }
    }
}
