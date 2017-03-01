package edu.feicui.app.phone.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.biz.MemoryBiz;
import edu.feicui.app.phone.view.SoftMgrDrawView;

public class SoftMgrActivity extends BaseActivity implements View.OnClickListener {
    SoftMgrDrawView mSmdv = null;
    MemoryBiz memoryBiz;
    ProgressBar mPgbBuiltInSpace;
    ProgressBar mPgbOuterSpace;
    TextView mTxtBuiltInSpace;
    TextView mTxtOuterSpace;
    RelativeLayout mRelaAllSoftware;
    RelativeLayout mRelaSystemSoftware;
    RelativeLayout mRelaUserSoftware;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memoryBiz = new MemoryBiz(this);
        setContentView(R.layout.activity_softmgr);
        initActionBar(this.getString(R.string.home_softwareMgr), getResources().getDrawable(R.mipmap.ic_arrows_bar_left), null, null);
        mSmdv = (SoftMgrDrawView) findViewById(R.id.soft_dv);
        mSmdv.setParamsWithAnim(memoryBiz.dataAndExternalStoragePercentage());
        mPgbBuiltInSpace = (ProgressBar) findViewById(R.id.progressBar_builtInSpace);
        mTxtBuiltInSpace = (TextView) findViewById(R.id.txt_builtInSpace);
        memoryBiz.dataBlockCountSizes();
        mPgbBuiltInSpace.setMax((int) memoryBiz.dataBlockCountSize);
        mPgbBuiltInSpace.setProgress((int) memoryBiz.dataUsedBlocksSize);
        mTxtBuiltInSpace.setText(memoryBiz.dataInfo);
        mPgbOuterSpace = (ProgressBar) findViewById(R.id.progressBar_outerSpace);
        mTxtOuterSpace = (TextView) findViewById(R.id.txt_outerSpace);
        memoryBiz.getExternalStorageDirectorys();
        mPgbOuterSpace.setMax((int) memoryBiz.EsBlockCountSize);
        mPgbOuterSpace.setProgress((int) memoryBiz.EsUsedBlocksSize);
        mTxtOuterSpace.setText(memoryBiz.esInfo);
        mRelaAllSoftware= (RelativeLayout) findViewById(R.id.allSoftware);
        mRelaSystemSoftware= (RelativeLayout) findViewById(R.id.systemSoftware);
        mRelaUserSoftware= (RelativeLayout) findViewById(R.id.userSoftware);
        mRelaAllSoftware.setOnClickListener(this);
        mRelaSystemSoftware.setOnClickListener(this);
        mRelaUserSoftware.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allSoftware:
                bundle = new Bundle();
                bundle.putInt("id", R.id.allSoftware);
                bundle.putString("name", this.getString(R.string.soft_allSoftware));
                super.startActivity(SoftMgrAppShowActivity.class,bundle);
                break;
            case R.id.systemSoftware:
                bundle = new Bundle();
                bundle.putInt("id", R.id.systemSoftware);
                bundle.putString("name", this.getString(R.string.soft_systemSoftware));
                super.startActivity(SoftMgrAppShowActivity.class,bundle);
                break;
            case R.id.userSoftware:
                bundle = new Bundle();
                bundle.putInt("id", R.id.userSoftware);
                bundle.putString("name", this.getString(R.string.soft_userSoftware));
                super.startActivity(SoftMgrAppShowActivity.class,bundle);
                break;
            default:
                break;
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
}
