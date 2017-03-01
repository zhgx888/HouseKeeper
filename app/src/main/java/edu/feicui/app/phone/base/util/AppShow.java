package edu.feicui.app.phone.base.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

import edu.feicui.app.phone.entity.AppInfo;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class AppShow {
    private ArrayList<AppInfo> allAppInfos;
    private ArrayList<AppInfo> systemAppInfos;
    private ArrayList<AppInfo> userAppInfos;
    private AppInfo appInfo;
    Context context;

    public AppShow(Context context) {
        this.context = context;
    }

    /**
     * 用来返回所有应用程序列表
     */

    public ArrayList<AppInfo> getAllPackageInfo() {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> pakageinfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        allAppInfos = new ArrayList<AppInfo>();
        for (PackageInfo packageInfo : pakageinfos) {
            appInfo = new AppInfo();
            appInfo.packageInfo = packageInfo;
            allAppInfos.add(appInfo);
        }
        return allAppInfos;
    }

    /**
     * 用来返回所有系统应用程序列表
     */
    public ArrayList<AppInfo> getSystemPackageInfo() {
        getAllPackageInfo();
        systemAppInfos = new ArrayList<AppInfo>();
        for (AppInfo appInfo : allAppInfos) {
            if ((appInfo.packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                systemAppInfos.add(appInfo);// 系统软件
            } else {
            }
        }
        return systemAppInfos;
    }

    /**
     * 用来返回所有用户应用程序列表
     */
    public ArrayList<AppInfo> getUserPackageInfo() {
        getAllPackageInfo();
        userAppInfos = new ArrayList<AppInfo>();
        for (AppInfo appInfo : allAppInfos) {
            if ((appInfo.packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
            } else {
                userAppInfos.add(appInfo);// 用户软件
            }
        }
        return userAppInfos;
    }
}
