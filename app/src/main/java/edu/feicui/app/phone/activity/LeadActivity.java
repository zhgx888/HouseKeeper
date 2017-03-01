package edu.feicui.app.phone.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.base.BaseActivity;
import edu.feicui.app.phone.service.LeadService;

public class LeadActivity extends BaseActivity implements View.OnClickListener {
    ViewPager mVpg;
    Context mCtx;
    PagerTabStrip mTabStrip;
    ArrayList<String> titleContainer = new ArrayList<>();
    LeadService leadService;
    Intent serviceIntent;
    int position;
    Button mBtnJump;
    ImageView mImg1, mImg2, mImg3;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            leadService = ((LeadService.MusicPlay) service).getService();
            leadService.play();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCtx = this;
        setContentView(R.layout.activity_lead);
        hideActionBar();
        mVpg = (ViewPager) findViewById(R.id.lead_viewpager);
        mVpg.setVisibility(ViewPager.VISIBLE);
        mTabStrip = (PagerTabStrip) findViewById(R.id.lead_pagertitle);
        mBtnJump = (Button) findViewById(R.id.lead_btn);
        mImg1 = (ImageView) findViewById(R.id.lead_img1);
        mImg2 = (ImageView) findViewById(R.id.lead_img2);
        mImg3 = (ImageView) findViewById(R.id.lead_img3);
        initTitles();
        mTabStrip.setTabIndicatorColor(Color.GREEN);
        mTabStrip.setTextColor(Color.GREEN);
        mTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        ArrayList<View> views = initViews();
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        adapter.setViews(views);
        mVpg.setAdapter(adapter);
        mVpg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                position = arg0;
                switch (position) {
                    case 0:
                        mImg1.setImageDrawable(getResources().getDrawable(R.mipmap.adware_style_selected));
                        mImg2.setImageDrawable(getResources().getDrawable(R.mipmap.adware_style_default));
                        mImg3.setImageDrawable(getResources().getDrawable(R.mipmap.adware_style_default));
                        mBtnJump.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        mImg1.setImageDrawable(getResources().getDrawable(R.mipmap.adware_style_default));
                        mImg2.setImageDrawable(getResources().getDrawable(R.mipmap.adware_style_selected));
                        mImg3.setImageDrawable(getResources().getDrawable(R.mipmap.adware_style_default));
                        mBtnJump.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        mImg1.setImageDrawable(getResources().getDrawable(R.mipmap.adware_style_default));
                        mImg2.setImageDrawable(getResources().getDrawable(R.mipmap.adware_style_default));
                        mImg3.setImageDrawable(getResources().getDrawable(R.mipmap.adware_style_selected));
                        mBtnJump.setVisibility(View.VISIBLE);
                        break;
                    default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        SharedPreferences setting = getSharedPreferences("ho", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {//第一次
            setting.edit().putBoolean("FIRST", false).commit();
            serviceIntent = new Intent();
            serviceIntent.setClass(this, LeadService.class);
            bindService(serviceIntent, conn, BIND_AUTO_CREATE);
        } else {
            super.startActivity(HomeActivity.class);
            finish();
        }
        mBtnJump.setOnClickListener(this);
    }

    private void initTitles() {//界面标题
        titleContainer.add(this.getString(R.string.lead_a_key_to_accelerate));
        titleContainer.add(this.getString(R.string.lead_deep_clean));
        titleContainer.add(this.getString(R.string.lead_all_software));
    }

    private ArrayList<View> initViews() {//界面图片
        ArrayList<View> views = new ArrayList<>();
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT));
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.adware_style_applist));
        views.add(imageView);
        imageView = new ImageView(this);
        imageView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT));
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.adware_style_banner));
        views.add(imageView);
        imageView = new ImageView(this);
        imageView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT));
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.adware_style_creditswall));
        views.add(imageView);
        return views;
    }

    @Override
    public void onClick(View v) {
        super.startActivity(LogoActivity.class, R.anim.in_down, R.anim.out_up);
        unbindService(conn);
        finish();
    }

    @Override
    protected void onClickActionBar(int type) {

    }

    private class ViewPagerAdapter extends PagerAdapter {
        private Context mCtx;
        private ArrayList<View> views = new ArrayList<>();

        public ViewPagerAdapter(Context context) {
            mCtx = context;
        }

        public void setViews(ArrayList<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleContainer.get(position);
        }
    }
}
