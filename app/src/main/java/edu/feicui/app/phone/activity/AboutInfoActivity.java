package edu.feicui.app.phone.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;

public class AboutInfoActivity extends BaseActivity {
    TextView mTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_info);
        mTxt = (TextView) findViewById(R.id.about_txt_info);
        mTxt.setText(getVersion());
        String fromClassName = getIntent().getStringExtra("className");
        // 无值默认是从主页面进入的
        if (fromClassName == null || fromClassName.equals("")) {
            startActivity(HomeActivity.class);
            finish();
            return;
        } else if (fromClassName.equals("SettingActivity")) {
            initActionBar(this.getString(R.string.set_about), getResources().getDrawable(R.mipmap
                    .ic_arrows_bar_left), null, null);
        } else {
            initActionBar(this.getString(R.string.set_about), null, getResources().getDrawable(R.mipmap
                    .ic_arrows_bar_right), null);
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return this.getString(R.string.app_current_version) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.app_current_version_no);
        }
    }

    @Override
    protected void onClickActionBar(int type) {
        switch (type) {
            case ACTION_RIGHT_ICON:
                finish();
            case ACTION_LEFT_ICON:
                finish();
        }
    }
}
