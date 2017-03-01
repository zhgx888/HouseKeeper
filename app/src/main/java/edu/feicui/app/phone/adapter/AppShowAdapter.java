package edu.feicui.app.phone.adapter;


import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.entity.AppInfo;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class AppShowAdapter extends BaseAdapter {
    Context mCtx;
    public ArrayList<AppInfo> mAppInfos;
    PackageManager pm;

    public AppShowAdapter(Context mCtx, ArrayList<AppInfo> mAppInfos) {
        this.mCtx = mCtx;
        this.mAppInfos = mAppInfos;
        pm = mCtx.getPackageManager();

    }


    @Override
    public int getCount() {
        return mAppInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //1.复用view优化listview,创建一个view作为getview的返回值用来显示一个条目
        View view = convertView;
        ViewHolder viewHolder = new ViewHolder();
        /**
         * 防止运行时会来回滚动一直生成新布局占用内存
         */
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mCtx.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.softmgr_appshow_list_style, null);
            //        2.获取view上的子控件对象
            viewHolder.mImgIcon = (ImageView) view.findViewById(R.id.img_icon);
            viewHolder.mTxtName = (TextView) view.findViewById(R.id.txt_name);
            viewHolder.mTxtVersion = (TextView) view.findViewById(R.id.txt_version);
            viewHolder.mTxtInfo = (TextView) view.findViewById(R.id.txt_info);
            viewHolder.mChk = (CheckBox) view.findViewById(R.id.CheckBox);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

//        3.获取postion位置条目对应的list集合中的数据，Bean对象
        AppInfo appInfo = mAppInfos.get(position);
        viewHolder.mChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mAppInfos.get(position).isDel = isChecked;
                } else {
                    mAppInfos.get(position).isDel = isChecked;
                }
                notifyDataSetChanged();
            }
        });
//        4.将数据设置给这些子控件做显示
        viewHolder.mImgIcon.setImageDrawable(appInfo.packageInfo.applicationInfo.loadIcon(pm));
        viewHolder.mTxtName.setText(appInfo.packageInfo.applicationInfo.loadLabel(pm).toString());
        viewHolder.mTxtVersion.setText(appInfo.packageInfo.versionName);
        viewHolder.mTxtInfo.setText(appInfo.packageInfo.applicationInfo.packageName);
        viewHolder.mChk.setChecked(appInfo.isDel);
        return view;
    }

    class ViewHolder {
        public ImageView mImgIcon;
        public TextView mTxtName;
        public TextView mTxtVersion;
        public TextView mTxtInfo;
        public CheckBox mChk;
    }
}
