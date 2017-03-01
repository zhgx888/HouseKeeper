package edu.feicui.app.phone.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initActionBar(this.getString(R.string.setting), getResources().getDrawable(R.mipmap.ic_arrows_bar_left), null, null);
        initMainButton();
//        hitSettingitem(View.inflate());
    }

    private void initMainButton() {//通知默认为
        ToggleButton tb_notif = (ToggleButton) findViewById(R.id.img_notifyIcon);
        tb_notif.setChecked(true);
        tb_notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });
    }


    @Override
    protected void onClickActionBar(int type) {
        switch (type) {
            case ACTION_LEFT_ICON:
                finish();
        }
    }

    public void onClickSetting(View view) {
        int viewID = view.getId();
        switch (viewID) {
//            case R.id.rela_help:
////                SaveInstance.getSaveInstance(SettingActivity.this).putBoolean("isFirstRun", true);
////                // 用来判断是从哪里进入的关于我们界面
//                Bundle bundle2 = new Bundle();
//                bundle2.putString("className", SettingActivity.this.getClass().getSimpleName());
//                startActivity(LeadActivity.class, bundle2);
//                break;
            case R.id.rela_about:
                // 用来判断是从哪里进入的关于我们界面
                Bundle bundle = new Bundle();
                bundle.putString("className", SettingActivity.this.getClass().getSimpleName());
                startActivity(AboutInfoActivity.class, bundle);
                break;
            default:
                break;
        }
    }
}
