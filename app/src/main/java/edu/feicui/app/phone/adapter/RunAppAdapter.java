package edu.feicui.app.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.feicui.app.phone.R;
import edu.feicui.app.phone.entity.RunAppInfo;

/**
 * Created by Administrator on 2017/2/19 0019.
 */

public class RunAppAdapter extends BaseAdapter {
    Context mCtx;
    List<RunAppInfo> runAppInfos;

    public RunAppAdapter(Context mCtx, List<RunAppInfo> runAppInfos) {
        this.mCtx = mCtx;
        this.runAppInfos = runAppInfos;

    }

    @Override
    public int getCount() {
        return runAppInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return runAppInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //1.复用view优化listview,创建一个view作为getview的返回值用来显示一个条目
        View view = convertView;
        RunAppAdapter.ViewHolder viewHolder = new RunAppAdapter.ViewHolder();
        /**
         * 防止运行时会来回滚动一直生成新布局占用内存
         */
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mCtx.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.accelerate_list_style, null);
            //2.获取view上的子控件对象
            viewHolder.mChkRunApp = (CheckBox) view.findViewById(R.id.run_app_chk);
            viewHolder.mImgRunApp = (ImageView) view.findViewById(R.id.run_app_img);
            viewHolder.MTxtRunAppName = (TextView) view.findViewById(R.id.run_app_txt_name);
            viewHolder.MTxtRunAppMemory = (TextView) view.findViewById(R.id.run_app_txt_memory);
            viewHolder.MTxtRunAppType = (TextView) view.findViewById(R.id.run_app_txt_type);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //3.获取postion位置条目对应的list集合中的数据，Bean对象
        viewHolder.mChkRunApp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    runAppInfos.get(position).isUser = isChecked;
                } else {
                    runAppInfos.get(position).isUser = isChecked;
                }
                notifyDataSetChanged();
            }
        });
        //4.将数据设置给这些子控件做显示
        RunAppInfo runAppInfo = runAppInfos.get(position);
        viewHolder.mChkRunApp.setChecked(runAppInfo.isUser);
        viewHolder.mImgRunApp.setImageDrawable(runAppInfo.run_app_icon);
        viewHolder.MTxtRunAppName.setText(runAppInfo.run_app_name);
        viewHolder.MTxtRunAppMemory.setText(runAppInfo.run_app_memory);
        viewHolder.MTxtRunAppType.setText(runAppInfo.run_app_type);
        return view;
    }


    class ViewHolder {
        CheckBox mChkRunApp;
        ImageView mImgRunApp;
        TextView MTxtRunAppName;
        TextView MTxtRunAppMemory;
        TextView MTxtRunAppType;
    }
}
