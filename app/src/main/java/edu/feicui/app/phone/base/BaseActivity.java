package edu.feicui.app.phone.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.feicui.app.phone.R;

/**
 * Created by ${Mr. Zhang} on 2016/12/27 0027.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    protected abstract void onClickActionBar(int type);

    protected final int ACTION_LEFT = 0x01;// 左边标题点击
    protected final int ACTION_MIDDLE = 0x02;// 中间标题点击
    protected final int ACTION_RIGHT = 0x03;// 右边标题点击
    protected final int ACTION_RIGHT_ICON = 0x04;// 右边ICON点击
    protected final int ACTION_LEFT_ICON = 0x05;// 左边标题点击
    private final int COMMON_ACTION_BAR_RESOURCE = R.layout.custom_action_bar;

    private View mCommonActionBarLayout;

    private ActionBar mActionBar;

    private LinearLayout ll_common_back;

    private ImageView mIvCommonLeftIcon, mIvCommonRightIcon;

    private TextView tv_common_left_title, tv_common_middle_title, tv_common_right_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        initEvent();
    }

    /**
     * 跳转方法
     */
    protected void startActivity(Class<?> targetClass) {
        Intent intent = new Intent(this, targetClass);
        super.startActivity(intent);
    }

    /**
     * 跳转方法
     */
    protected void startActivity(Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
        super.startActivity(intent);
    }

    /**
     * 跳转方法
     */
    protected void startActivity(Class<?> cls, int inAnimID, int outAnimID) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        overridePendingTransition(inAnimID, outAnimID);

    }


    /**
     * 初始化ActionBar
     */
    private void initActionBar() {
        mCommonActionBarLayout = LayoutInflater.from(this).inflate(COMMON_ACTION_BAR_RESOURCE, null);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            mActionBar.setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimary));
            initView(mCommonActionBarLayout);
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            mActionBar.setCustomView(mCommonActionBarLayout, params);

        }
    }

    protected void hideActionBar() {
        if (mActionBar != null) {
            mActionBar.hide();
        }
    }

    /**
     * 事件注册
     */
    private void initEvent() {
        /**
         * 返回
         */
        ll_common_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                onBackPressed();
                onClickActionBar(ACTION_LEFT_ICON);
            }
        });

        /**
         * 右边标题点击
         */
        tv_common_right_title.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickActionBar(ACTION_RIGHT);
            }
        });

        /**
         * 左边标题点击
         */
        tv_common_left_title.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickActionBar(ACTION_LEFT);
            }
        });

        /**
         * 中间标题点击
         */
        tv_common_middle_title.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickActionBar(ACTION_MIDDLE);
            }
        });
        mIvCommonRightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickActionBar(ACTION_RIGHT_ICON);
            }
        });
    }

    /**
     * 注册控件
     *
     * @param rootView
     */
    private void initView(View rootView) {
        ll_common_back = (LinearLayout) rootView.findViewById(R.id.ll_common_back);
        mIvCommonLeftIcon = (ImageView) rootView.findViewById(R.id.iv_common_left_icon);
        mIvCommonRightIcon = (ImageView) rootView.findViewById(R.id.iv_common_right_icon);
        tv_common_left_title = (TextView) rootView
                .findViewById(R.id.tv_common_left_title);
        tv_common_middle_title = (TextView) rootView
                .findViewById(R.id.tv_common_middle_title);
        tv_common_right_title = (TextView) rootView
                .findViewById(R.id.tv_common_right_title);
    }

    /**
     * 设置左边的标题
     *
     * @param leftTitle
     */
    protected void setLeftTitle(String leftTitle) {
        tv_common_left_title.setText(leftTitle);
    }

    /**
     * 设置中间标题
     *
     * @param middleTitle
     */
    protected void setMiddleTitle(String middleTitle) {
        tv_common_middle_title.setText(middleTitle);
    }

    /**
     * 设置右边标题
     *
     * @param rightTitle
     */
    protected void setRightTitle(String rightTitle) {
        tv_common_middle_title.setText(rightTitle);
    }

    /**
     * 主页的Actionbar样式
     */

    /**
     * 左图标
     */
    protected void setLeftIcon(Drawable drawable) {
        if (drawable == null) {
            ll_common_back.setVisibility(View.GONE);
        } else {
//            if (ll_common_back.setVisibility(View.VISIBLE)) {
//            }
            ll_common_back.setVisibility(View.VISIBLE);
            mIvCommonLeftIcon.setImageDrawable(drawable);
        }
    }

    /**
     * 右图标
     */
    protected void setRightIcon(Drawable drawable) {
        if (drawable == null) {
            mIvCommonRightIcon.setVisibility(View.GONE);
        } else {
            mIvCommonRightIcon.setVisibility(View.VISIBLE);
            mIvCommonRightIcon.setImageDrawable(drawable);
        }
    }

    public void initActionBar(String title, Drawable leftIcon, Drawable rightIcon, View.OnClickListener
            onClickListener) {
        setMiddleTitle(title);
        if (leftIcon != null) {
            setLeftIcon(leftIcon);
        }
        if (rightIcon != null) {
            setRightIcon(rightIcon);
        }
    }
}
