package edu.feicui.app.phone.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.adapter.AppShowAdapter;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.base.util.AppShow;
import edu.feicui.app.phone.entity.AppInfo;

public class SoftMgrAppShowActivity extends BaseActivity {
    ListView mLst;
    ProgressBar mPrg;
    CheckBox mChk;
    Context mCtx;
    ArrayList<AppInfo> mAppInfos;
    Button mBtn;
    AppShowAdapter appShowAdapter;
    private AppDelRecevice appDelRecevice;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCtx = this;
        setContentView(R.layout.activity_softmgr_appshow);
        id = this.getIntent().getExtras().getInt("id");
        initActionBar(getIntent().getStringExtra("name"), getResources().getDrawable(R.mipmap
                .ic_arrows_bar_left), null, null);
        mLst = (ListView) findViewById(R.id.soft_lst);
        mPrg = (ProgressBar) findViewById(R.id.soft_prg);
        mChk = (CheckBox) findViewById(R.id.chk_all);
        mBtn = (Button) findViewById(R.id.btn_del);
        String title = getResources().getString(R.string.home_softwareMgr);
        switch (id) {
            case R.id.allSoftware:
                title = getResources().getString(R.string.soft_allSoftware);
                break;
            case R.id.systemSoftware:
                title = getResources().getString(R.string.soft_systemSoftware);
                break;
            case R.id.userSoftware:
                title = getResources().getString(R.string.soft_userSoftware);
                break;
        }
        mChk.setOnCheckedChangeListener(changeAllListener);
        mBtn.setOnClickListener(onScrollListener);
        asynLoadApp();
        // 调用广播接收器
        appDelRecevice = new AppDelRecevice();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        filter.addAction(AppDelRecevice.ACTION_APPDEL);
        registerReceiver(appDelRecevice, filter);

    }

    /**
     * 应用删除广播接收器
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(appDelRecevice);
    }

    public void asynLoadApp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                AppShow appShow = new AppShow(mCtx);
                switch (id) {
                    case R.id.allSoftware:
                        mAppInfos = appShow.getAllPackageInfo();
                        break;
                    case R.id.systemSoftware:
                        mAppInfos = appShow.getSystemPackageInfo();
                        break;
                    case R.id.userSoftware:
                        mAppInfos = appShow.getUserPackageInfo();
                        break;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPrg.setVisibility(View.INVISIBLE);
                        mLst.setVisibility(View.VISIBLE);
                        appShowAdapter = new AppShowAdapter(mCtx, mAppInfos);
                        mLst.setAdapter(appShowAdapter);
                    }
                });
            }
        }).start();
    }

    private CompoundButton.OnCheckedChangeListener changeAllListener = new CompoundButton
            .OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            AppShowAdapter appListAdapter = (AppShowAdapter) mLst.getAdapter();
            List<AppInfo> appInfos = appListAdapter.mAppInfos;
            if (isChecked) {
                for (AppInfo appInfo : appInfos) {
                    appInfo.isDel = isChecked;
                }
            } else {
                for (AppInfo appInfo : appInfos) {
                    appInfo.isDel = isChecked;
                }
            }
            appShowAdapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener onScrollListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int viewID = v.getId();
            switch (viewID) {
                case R.id.btn_del:
                    AppShowAdapter appShowAdapter = (AppShowAdapter) mLst.getAdapter();
                    List<AppInfo> appInfos = appShowAdapter.mAppInfos;
                    for (AppInfo appInfo : appInfos) {
                        if (appInfo.isDel) {
                            String packageName = appInfo.packageInfo.packageName;
                            Intent intent = new Intent(Intent.ACTION_DELETE);
                            intent.setData(Uri.parse("package:" + packageName));
                            startActivity(intent);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onClickActionBar(int type) {
        switch (type) {
            case ACTION_LEFT_ICON:
                finish();
                break;
        }
    }

    /**
     * 注册广播接收器
     */
    public class AppDelRecevice extends BroadcastReceiver {
        public static final String ACTION_APPDEL = "com.androidy.app.phone.del";

        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_PACKAGE_REMOVED) || action.equals(ACTION_APPDEL)) {
                asynLoadApp();
            }
        }
    }
}
