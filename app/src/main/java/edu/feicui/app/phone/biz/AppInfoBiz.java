package edu.feicui.app.phone.biz;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class AppInfoBiz {
    private PackageManager mPmger;
    private ActivityManager mAmger;
    /**
     * 用来保存所有应用程序包activity的列表
     */
    private List<PackageInfo> allPackagerInfos = new ArrayList<PackageInfo>();
    private List<PackageInfo> userPackagerInfos = new ArrayList<PackageInfo>();
    private List<PackageInfo> systemPackagerInfos = new ArrayList<PackageInfo>();

    /**
     * 实例化本类时(单态了)，将去获取所有应用程序列表,保存在
     */
    public AppInfoBiz(Context mCtx) {
        mPmger = mCtx.getPackageManager();
        mAmger = (ActivityManager) mCtx.getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * 用来返回本类的唯一对象 (单态模块　且做了同步处理,还优化了一下同步处理)
     */
    private static AppInfoBiz appInfoBiz = null;

    public static AppInfoBiz getAppInfoManager(Context context) {
        if (appInfoBiz == null) {
            synchronized (context) {
                if (appInfoBiz == null) {
                    appInfoBiz = new AppInfoBiz(context);
                }
            }
        }
        return appInfoBiz;
    }

    /**
     * 清理所有正在运行的程序(级别为服务进程以上的非系统进程)
     */
    public void killAllProcesses() {
        List<RunningAppProcessInfo> appProcessInfos = mAmger.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {
                String packageName = appProcessInfo.processName;
                try {
                    ApplicationInfo applicationInfo = mPmger.getApplicationInfo(packageName, PackageManager
                            .GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES);
                    if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    } else {
                        mAmger.killBackgroundProcesses(packageName);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 用来返回所有应用程序列表
     */
    public List<PackageInfo> getAllPackageInfo(boolean isReset) {
        if (isReset) {
            loadAllActivityPackger();
        }
        return allPackagerInfos;
    }

    /**
     * 用来返回所有系统应用程序列表
     */
    public List<PackageInfo> getSystemPackagerInfo(boolean isReset) {
        if (isReset) {
            loadAllActivityPackger();
            systemPackagerInfos.clear();
            for (PackageInfo packageInfo : allPackagerInfos) {
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    systemPackagerInfos.add(packageInfo);
                } else {
                }
            }
        }
        return systemPackagerInfos;
    }

    /**
     * 用来返回所有用户应用程序列表
     */
    public List<PackageInfo> getUserPackageInfo(boolean isReset) {
        if (isReset) {
            loadAllActivityPackger();
            userPackagerInfos.clear();
            for (PackageInfo packageInfo : allPackagerInfos) {
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                } else {
                    userPackagerInfos.add(packageInfo);
                }
            }
        }
        return userPackagerInfos;
    }

    // 加载所有Activity应用程序包
    private void loadAllActivityPackger() {
        List<PackageInfo> infos = mPmger.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        allPackagerInfos.clear();
        for (PackageInfo packageInfo : infos) {
            allPackagerInfos.add((new PackageInfo()));
        }
    }
}
