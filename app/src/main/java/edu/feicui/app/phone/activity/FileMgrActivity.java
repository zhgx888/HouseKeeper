package edu.feicui.app.phone.activity;

import android.os.Bundle;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;

public class FileMgrActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filemgr);
        initActionBar(this.getString(R.string.home_fileMgr), getResources().getDrawable(R.mipmap.ic_arrows_bar_left), null, null);
    }
    @Override
    protected void onClickActionBar(int type) {
        switch (type) {
            case ACTION_LEFT_ICON:
                finish();
                break;
        }
    }
}
