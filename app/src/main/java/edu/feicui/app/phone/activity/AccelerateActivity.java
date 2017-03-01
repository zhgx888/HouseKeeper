package edu.feicui.app.phone.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.adapter.RunAppAdapter;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.MemoryBiz;
import edu.feicui.app.phone.biz.RunAppBiz;
import edu.feicui.app.phone.biz.SystemBiz;
import edu.feicui.app.phone.entity.RunAppInfo;

public class AccelerateActivity extends BaseActivity {
    static final int SUCCESS_GET_SYSTEM_RUN_APPL_ICAITON = 0;
    static final int SUCCESS_GET_USER_RUN_APPL_ICAITON = 1;
    boolean isDell = true;
    MemoryBiz memoryBiz;
    SystemBiz systemBiz;
    TextView mTxtBrand;
    TextView mTxtSdk;
    ProgressBar progressBar;
    TextView mTxtRamMemory;
    Button mBtnShow;
    ListView mLv;
    ProgressBar progressBar_list;
    CheckBox mChk;
    Button mBtnClear;

    RunAppAdapter runAppAdapter;
    RunAppBiz runAppBiz;
    List<RunAppInfo> runAppInfos;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS_GET_SYSTEM_RUN_APPL_ICAITON:
                case SUCCESS_GET_USER_RUN_APPL_ICAITON:
                    runAppAdapter = new RunAppAdapter(getBaseContext(), runAppInfos);
                    mLv.setAdapter(runAppAdapter);
                    progressBar_list.setVisibility(View.INVISIBLE);
                    mLv.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerate);
        initActionBar(this.getString(R.string.home_phoneSpeedGo), getResources().getDrawable(R.mipmap
                .ic_arrows_bar_left), null, null);
        memoryBiz = new MemoryBiz(this);
        systemBiz = new SystemBiz(this);
        runAppBiz = new RunAppBiz(this);
        progressBar_list = (ProgressBar) findViewById(R.id.soft_prg);
        mLv = (ListView) findViewById(R.id.soft_lst);
        mBtnShow = (Button) findViewById(R.id.btn_show);
        show();
        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar_list.setVisibility(View.VISIBLE);
                mLv.setVisibility(View.INVISIBLE);
                show();
            }
        });
        mChk = (CheckBox) findViewById(R.id.chk_all);
        allCheng();
        mBtnClear= (Button) findViewById(R.id.btn_del);
        mBtnClear.setText(this.getString(R.string.speedup_clearMaster));
        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoryBiz.killAllProcesses();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        modifyControlProperties();//修改空间属性

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
     * 修改空间属性
     */
    private void modifyControlProperties() {
        mTxtBrand = (TextView) findViewById(R.id.speed_txt_Brand);
        mTxtBrand.setText(this.getString(R.string.speedup_brand) + systemBiz.getPhoneName1());

        mTxtSdk = (TextView) findViewById(R.id.speed_txt_Sdk);
        mTxtSdk.setText(this.getString(R.string.speedup_SDK) + systemBiz.getPhoneSystemVersionSDK());

        progressBar = (ProgressBar) findViewById(R.id.speed_progressBar);//ProgressBar
        progressBar.setMax((int) memoryBiz.getPhonbeTotalRanMemory());
        progressBar.setProgress((int) memoryBiz.getPhonbeTotalRanMemory() - (int) memoryBiz
                .getPhoneAvailableRamMemory());

        mTxtRamMemory = (TextView) findViewById(R.id.speed_txt_useMemory);//内存显示值
        mTxtRamMemory.setText(this.getString(R.string.speedup_availableMemory) + ((int) memoryBiz
                .getPhoneAvailableRamMemory() >> 20) + this.getString(R.string.letter_M) + this.getString(R
                .string.symbol_slash) + this.getString(R.string.speedup_allMemory) + ((int) memoryBiz
                .getPhonbeTotalRanMemory() >> 20) + this.getString(R.string.letter_M));
    }

    /**
     * 点击换
     *
     * @return
     */
    boolean show() {
        if (isDell) {
            mBtnShow.setText("只显示用户正在进行进程");
            runAppInfos = runAppBiz.getSystemApp();
            getRunAppInfos();
            isDell = false;//给什么
        } else {
            mBtnShow.setText("只显示系统正在进行进程");
            runAppInfos = runAppBiz.getUserApp();
            getRunAppInfos();
            isDell = true;
        }
        return isDell;
    }


    /**
     * 获取用户运行APP
     */
    private void getRunAppInfos(){
        runAppAdapter = new RunAppAdapter(getBaseContext(), runAppInfos);
        mLv.setAdapter(runAppAdapter);
        progressBar_list.setVisibility(View.INVISIBLE);
        mLv.setVisibility(View.VISIBLE);
    }

    /**
     * 全选按键
     */
    private void allCheng() {
        mChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (RunAppInfo appRunInfo : runAppInfos) {
                        appRunInfo.isUser = true;
                        if (appRunInfo.isUser) {
                            runAppAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    for (RunAppInfo appRunInfo : runAppInfos) {
                        appRunInfo.isUser = false;
                        if (!appRunInfo.isUser) {
                            runAppAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}
