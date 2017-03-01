package edu.feicui.app.phone.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.SystemBiz;

public class PhoneMgrActivity extends BaseActivity {
    TextView mTvIsAll,mTvBattery, mTvEquipmentMgr, mTvSystemVersion, mTvAllRunMemory, mTvResidueRunMemory,
            mTvCpuName, mTvCpuSize, mTvPhoneResolutio, mTvCameraResolution, mTvCameraBasebandVersion,
            mTvCameraIsroot;
    ProgressBar mPb;
    SystemBiz systemBiz;
    BatteryReceive batteryReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonemgr);
        initActionBar(this.getString(R.string.home_phoneTest), getResources().getDrawable(R.mipmap
                .ic_arrows_bar_left), null, null);
        mPb = (ProgressBar) findViewById(R.id.phonemgr_progressBar);
        mTvIsAll = (TextView) findViewById(R.id.phonemgr_txt_isAll);
        mTvBattery = (TextView) findViewById(R.id.phonemgr_txt_battery);
        /**
         * 动态注册电池广播
         */
        batteryReceive = new BatteryReceive();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceive, intentFilter);
        Systeminfo();
    }

    /**
     * 删除电池广播
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceive);
    }

    void Systeminfo() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        systemBiz = new SystemBiz(this);

        mTvEquipmentMgr = (TextView) findViewById(R.id.phonemgr_txt_equipment_mgr);
        mTvEquipmentMgr.setText("设备名称:" + systemBiz.getPhoneModelName());
        mTvSystemVersion = (TextView) findViewById(R.id.phonemgr_txt_system_version);
        mTvSystemVersion.setText("系统版本：" + systemBiz.getPhoneSystemVersion());

        mTvAllRunMemory = (TextView) findViewById(R.id.phonemgr_txt_all_run_memory);
        mTvAllRunMemory.setText("系统总运行：" + (info.totalMem >> 20) + "M");
        mTvResidueRunMemory = (TextView) findViewById(R.id.phonemgr_txt_residue_run_memory);
        mTvResidueRunMemory.setText("系统剩余内存:" + (info.availMem >> 20) + "M");

        mTvCpuName = (TextView) findViewById(R.id.phonemgr_txt_cpu_name);
        mTvCpuName.setText("CPU名称：" + systemBiz.getPhoneCPUName());
        mTvCpuSize = (TextView) findViewById(R.id.phonemgr_txt_cpu_size);
        mTvCpuSize.setText("COU数量：" + systemBiz.getPhoneCPUNumber());

        mTvPhoneResolutio = (TextView) findViewById(R.id.phonemgr_txt_phone_resolution);
        mTvPhoneResolutio.setText("手机分辨率：" + systemBiz.getResolution());
        mTvCameraResolution = (TextView) findViewById(R.id.phonemgr_txt_camera_resolution);
//        mTvCameraResolution.setText("照相机分辨率" + systemBiz.getMaxPhoneSize());

        mTvCameraBasebandVersion = (TextView) findViewById(R.id.phonemgr_txt_camera_baseband_version);
        mTvCameraBasebandVersion.setText("基带版本：" + systemBiz.getBaseband_Ver());
        mTvCameraIsroot = (TextView) findViewById(R.id.phonemgr_txt_camera_isroot);
        if (systemBiz.isRoot()) {
            mTvCameraIsroot.setText("是否获取Root权限:否");
        } else {
            mTvCameraIsroot.setText("是否获取Root权限:是");
        }
    }

    @Override
    protected void onClickActionBar(int type) {
        switch (type) {
            case ACTION_LEFT_ICON:
                finish();
                break;
        }
    }

    /**
     * 广播类
     */
    public class BatteryReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final int level = intent.getIntExtra("level", 0);//电量
            mPb.setProgress(level);
            mTvBattery.setText(level + "%");
            if (level==100){
                mTvIsAll.setBackgroundColor(getResources().getColor(R.color.colorSkyBlue));
            }else{
                mTvIsAll.setBackgroundColor(getResources().getColor(R.color.colorOrange));
            }
        }
    }
}
