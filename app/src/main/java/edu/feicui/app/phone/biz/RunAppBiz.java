package edu.feicui.app.phone.biz;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Debug;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import edu.feicui.app.phone.entity.RunAppInfo;

/**
 * Created by Administrator on 2017/2/19 0019.
 */

public class RunAppBiz {
    private ActivityManager mAmgr;
    private PackageManager mPmgr;
    private DecimalFormat df = new DecimalFormat("0.00");
    RunAppInfo runAppInfo;
    /**
     * 用来保存所有应用程序包activity的列表
     */
    public List<RunAppInfo> runSystemList = new ArrayList<RunAppInfo>();
    public List<RunAppInfo> runUserList = new ArrayList<RunAppInfo>();

    public RunAppBiz(Context mCtxRunApp) {
        mAmgr = (ActivityManager) mCtxRunApp.getSystemService(Context.ACTIVITY_SERVICE);
        mPmgr = mCtxRunApp.getPackageManager();
    }

    public List<RunAppInfo> getSystemApp() {
        runSystemList.clear();
        if (!filterApp()) {
            runUserList.clear();
            runAppInfo.run_app_type = "系统应用";
            runSystemList.add(runAppInfo);
        }
        return runSystemList;
    }

    public List<RunAppInfo> getUserApp() {
        runUserList.clear();
        if (filterApp()) {
            runSystemList.clear();
            runAppInfo.run_app_type = "用户应用";
            runUserList.add(runAppInfo);
        }
        return runUserList;
    }

    private boolean filterApp() {//用来判断是否是系统还是用户应用
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = mAmgr.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            runAppInfo = new RunAppInfo();
            List<ApplicationInfo> runApps = mPmgr.getInstalledApplications(PackageManager
                    .GET_UNINSTALLED_PACKAGES);
            for (ApplicationInfo runApp : runApps) {
                if ((appProcessInfo.processName).equals(runApp.processName)) {
                    runAppInfo.run_app_name = runApp.loadLabel(mPmgr).toString();
                    runAppInfo.run_app_icon = runApp.loadIcon(mPmgr);
                    // 获得该进程占用的内存
                    int[] myMempid = new int[]{appProcessInfo.pid};
                    // 此MemoryInfo位于android.os.Debug.MemoryInfo包中，用来统计进程的内存信息
                    Debug.MemoryInfo[] memoryInfo = mAmgr.getProcessMemoryInfo(myMempid);
                    // 获取进程占内存用信息 kb单位
                    int memSize = memoryInfo[0].getTotalPrivateDirty();
                    float memory = (memoryInfo[0].dalvikPss >> 10) + (memoryInfo[0].nativePss >> 10);
                    String appMemory = "内存:" + df.format(memory) + "M";
                    runAppInfo.run_app_memory = appMemory;
                    if ((runApp.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {//原来是系统应用，用户手动升级
                        return true;
                    } else if ((runApp.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {//用户自己安装的应用程序
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
