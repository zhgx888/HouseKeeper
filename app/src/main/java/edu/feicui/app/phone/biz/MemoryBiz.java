package edu.feicui.app.phone.biz;


import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import edu.feicui.app.phone.R;

/**
 * Created by Administrator on 2017/1/7 0007.
 */

public class MemoryBiz {
    Context context;
    float blockSize;//块大小★
    float availableBlocks;//可用的块★
    float blockCount;//块总数★
    public float dataBlockCountSize;//内存总大小
    public float dataAvailableBlocksSize;//内存可用大小
    public float dataUsedBlocksSize;//内存已用大小
    public float EsBlockCountSize;//外存总大小
    public float EsAvailableBlocksSize;//外存可用大小
    public float EsUsedBlocksSize;//外存已用大小
    //    public String dataBlockCountSizes;
//    public String dataAvailableBlocksSizes;
//    public String EsBlockCountSizes;
//    public String EsAvailableBlocksSizes;
    public String[] dataBlockCountSizes;
    public String[] dataAvailableBlocksSizes;
    public String[] EsBlockCountSizes;
    public String[] EsAvailableBlocksSizes;
    public String dataInfo;
    public String esInfo;

    public float separatePercentage;//已用占比
    ActivityManager activityManager;
    PackageManager packageManager;

    public MemoryBiz(Context mCtx) {
        context = mCtx;
        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        packageManager = context.getPackageManager();
    }

    public void dataDirectory() {//手机存储空间
        File file = Environment.getDataDirectory();
        StatFs sfs = new StatFs(file.getAbsolutePath());
        sfs.getBlockSize();//获得块大小★
        sfs.getAvailableBlocks();//获得可用的块★
        sfs.getBlockCount();//获得块计数★
        blockSize = sfs.getBlockSize();
        availableBlocks = sfs.getAvailableBlocks();
        blockCount = sfs.getBlockCount();
    }

    public void rootDirectory() {//手机运行内存空间
        File file = Environment.getRootDirectory();
        StatFs sfs = new StatFs(file.getAbsolutePath());
        sfs.getBlockSize();//获得块大小★
        sfs.getAvailableBlocks();//获得可用的块★
        sfs.getBlockCount();//获得块计数★
        blockSize = sfs.getBlockSize();
        availableBlocks = sfs.getAvailableBlocks();
        blockCount = sfs.getBlockCount();
    }

    public void getExternalStorageDirectory() {//手机外存空间
        File file = Environment.getExternalStorageDirectory();
        StatFs sfs = new StatFs(file.getAbsolutePath());
        sfs.getBlockSize();//获得块大小★
        sfs.getAvailableBlocks();//获得可用的块★
        sfs.getBlockCount();//获得块计数★
        blockSize = sfs.getBlockSize();
        availableBlocks = sfs.getAvailableBlocks();
        blockCount = sfs.getBlockCount();
    }

    public float getPhoneAvailableRamMemory() {//运行内存
        MemoryInfo memoryInfo = new MemoryInfo();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

        public float getPhonbeTotalRanMemory() {//设备完整运行内存 单位B
        MemoryInfo info = new MemoryInfo();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(info);
        return info.totalMem;
    }
    public float getPhoneTablRanMemory() {
        try {
            FileReader fr = new FileReader("/proc/meminfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split("\\s+");
            return Float.valueOf(array[1]) * 1024;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return 0;
    }

    public void killAllProcesses() {
        List<RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            String packageName = appProcessInfo.processName;
            ApplicationInfo applicationInfo = null;
            try {
                applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager
                        .GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES);
                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                } else {
                    activityManager.killBackgroundProcesses(packageName);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public float separatePercentage() {
        float availableMemory = getPhoneAvailableRamMemory();
        float totalMemory = getPhoneTablRanMemory();
        float useMemory = totalMemory - availableMemory;
        float separatePercentages = (useMemory / totalMemory) * 100;
        DecimalFormat df = new DecimalFormat("#.00");
        separatePercentage = Float.parseFloat(df.format(separatePercentages));//已用占比
        Float angle = separatePercentage * 3.6f;//得到已用占比角度
        return angle;
    }

    public float dataAndExternalStoragePercentage() {
        dataDirectory();
        float dataBlockCountSize = blockSize * blockCount;
        getExternalStorageDirectory();
        float ESBlockCountSize = blockSize * blockCount;
        float separatePercentageAngles = dataBlockCountSize / (dataBlockCountSize + ESBlockCountSize) * 100
                * 3.6f;//得到内存占比角度
        DecimalFormat df = new DecimalFormat("0.00");
        float separatePercentageAngle = Float.parseFloat(df.format(separatePercentageAngles));
        return separatePercentageAngle;//返回内存占比角度
    }


    public void dataBlockCountSizes() {
        dataDirectory();
        dataBlockCountSizes = fileSize(blockSize * blockCount);
        dataAvailableBlocksSizes = fileSize(blockSize * availableBlocks);
        dataBlockCountSize = Float.parseFloat(dataBlockCountSizes[0]);
        dataAvailableBlocksSize = Float.parseFloat(dataAvailableBlocksSizes[0]);
        dataUsedBlocksSize = dataBlockCountSize - dataAvailableBlocksSize;
        dataInfo = context.getResources().getString(R.string.soft_available)+ ":" + dataAvailableBlocksSizes[0] + dataAvailableBlocksSizes[1] + "/" +
                dataBlockCountSizes[0] + dataBlockCountSizes[1];
    }

    public void getExternalStorageDirectorys() {
        getExternalStorageDirectory();
        EsBlockCountSizes = fileSize(blockSize * blockCount);
        EsAvailableBlocksSizes = fileSize(blockSize * availableBlocks);
        EsBlockCountSize = Float.parseFloat(EsBlockCountSizes[0]);
        EsAvailableBlocksSize = Float.parseFloat(EsAvailableBlocksSizes[0]);
        EsUsedBlocksSize = EsBlockCountSize - EsAvailableBlocksSize;
        esInfo = context.getResources().getString(R.string.soft_available)+":" + EsAvailableBlocksSizes[0] + EsAvailableBlocksSizes[1] + "/" + EsBlockCountSizes[0]
                + EsBlockCountSizes[1];
    }

    //用来定义存储空间显示格式
    public String[] fileSize(float size) {
        String str = "";
        if (size > 1024) {
            str = "KB";
            size /= 1024;
            if (size > 1024) {
                str = "M";
                size /= 1024;
                if (size > 1024) {
                    str = "G";
                    size /= 1024;
                }
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String[] result = new String[3];
        result[0] = df.format(size);
        result[1] = str;
        return result;
    }
//        public void dataBlockCountSizes() {
//        dataDirectory();
//        dataBlockCountSize = blockSize * blockCount;
//        dataAvailableBlocksSize = blockSize * availableBlocks;
//        if (dataBlockCountSize >= 1024 * 1024 * 1024) {
//            dataBlockCountSize = dataBlockCountSize / 1024 / 1024 / 1024;
//            dataAvailableBlocksSize = dataAvailableBlocksSize / 1024 / 1024 / 1024;
//            DecimalFormat df = new DecimalFormat("0.00");
//            dataBlockCountSize = Float.parseFloat(df.format(dataBlockCountSize));
//            dataAvailableBlocksSize = Float.parseFloat(df.format(dataAvailableBlocksSize));
//            dataUsedBlocksSize = dataBlockCountSize - dataAvailableBlocksSize;
//            dataBlockCountSizes = dataBlockCountSize + "G";
//            dataAvailableBlocksSizes = dataAvailableBlocksSize + "G";
//        } else if (dataBlockCountSize >= 1024 * 1024) {
//            dataBlockCountSize = dataBlockCountSize / 1024 / 1024;
//            dataAvailableBlocksSize = dataAvailableBlocksSize / 1024 / 1024;
//            DecimalFormat df = new DecimalFormat("0.00");
//            dataBlockCountSize = Float.parseFloat(df.format(dataBlockCountSize));
//            dataAvailableBlocksSize = Float.parseFloat(df.format(dataAvailableBlocksSize));
//            dataUsedBlocksSize = dataBlockCountSize - dataAvailableBlocksSize;
//            dataBlockCountSizes = dataBlockCountSize + "M";
//            dataAvailableBlocksSizes = dataAvailableBlocksSize + "M";
//        } else if (dataBlockCountSize >= 1024) {
//            dataBlockCountSize = dataBlockCountSize / 1024;
//            dataAvailableBlocksSize = dataAvailableBlocksSize / 1024;
//            DecimalFormat df = new DecimalFormat("0.00");
//            dataBlockCountSize = Float.parseFloat(df.format(dataBlockCountSize));
//            dataAvailableBlocksSize = Float.parseFloat(df.format(dataAvailableBlocksSize));
//            dataUsedBlocksSize = dataBlockCountSize - dataAvailableBlocksSize;
//            dataBlockCountSizes = dataBlockCountSize + "KB";
//            dataAvailableBlocksSizes = dataAvailableBlocksSize + "KB";
//        } else {
//            DecimalFormat df = new DecimalFormat("0.00");
//            dataBlockCountSize = Float.parseFloat(df.format(dataBlockCountSize));
//            dataAvailableBlocksSize = Float.parseFloat(df.format(dataAvailableBlocksSize));
//            dataUsedBlocksSize = dataBlockCountSize - dataAvailableBlocksSize;
//            dataBlockCountSizes = dataBlockCountSize + "B";
//            dataAvailableBlocksSizes = dataAvailableBlocksSize + "B";
//        }
//    }
//
//    public void getExternalStorageDirectorys() {
//        getExternalStorageDirectory();
//        EsBlockCountSize = blockSize * blockCount;
//        EsAvailableBlocksSize = blockSize * availableBlocks;
//        if (EsBlockCountSize >= 1024 * 1024 * 1024) {
//            EsBlockCountSize = EsBlockCountSize / 1024 / 1024 / 1024;
//            EsAvailableBlocksSize = EsAvailableBlocksSize / 1024 / 1024 / 1024;
//            EsUsedBlocksSize = EsBlockCountSize - EsAvailableBlocksSize;
//            DecimalFormat df = new DecimalFormat("0.00");
//            EsBlockCountSize = Float.parseFloat(df.format(EsBlockCountSize));
//            EsAvailableBlocksSize = Float.parseFloat(df.format(EsAvailableBlocksSize));
//            EsUsedBlocksSize = EsBlockCountSize - EsAvailableBlocksSize;
//            EsBlockCountSizes = EsBlockCountSize + "G";
//            EsAvailableBlocksSizes = EsAvailableBlocksSize + "G";
//        } else if (EsBlockCountSize >= 1024 * 1024) {
//            EsBlockCountSize = EsBlockCountSize / 1024 / 1024;
//            EsAvailableBlocksSize = EsAvailableBlocksSize / 1024 / 1024;
//            DecimalFormat df = new DecimalFormat("0.00");
//            EsBlockCountSize = Float.parseFloat(df.format(EsBlockCountSize));
//            EsAvailableBlocksSize = Float.parseFloat(df.format(EsAvailableBlocksSize));
//            EsUsedBlocksSize = EsBlockCountSize - EsAvailableBlocksSize;
//            EsBlockCountSizes = EsBlockCountSize + "M";
//            EsAvailableBlocksSizes = EsAvailableBlocksSize + "M";
//        } else if (EsBlockCountSize >= 1024) {
//            EsBlockCountSize = EsBlockCountSize / 1024;
//            EsAvailableBlocksSize = EsAvailableBlocksSize / 1024;
//            DecimalFormat df = new DecimalFormat("0.00");
//            EsBlockCountSize = Float.parseFloat(df.format(EsBlockCountSize));
//            EsAvailableBlocksSize = Float.parseFloat(df.format(EsAvailableBlocksSize));
//            EsUsedBlocksSize = EsBlockCountSize - EsAvailableBlocksSize;
//            EsBlockCountSizes = EsBlockCountSize + "KB";
//            EsAvailableBlocksSizes = EsAvailableBlocksSize + "KB";
//        } else {
//            DecimalFormat df = new DecimalFormat("0.00");
//            EsBlockCountSize = Float.parseFloat(df.format(EsBlockCountSize));
//            EsAvailableBlocksSize = Float.parseFloat(df.format(EsAvailableBlocksSize));
//            EsUsedBlocksSize = EsBlockCountSize - EsAvailableBlocksSize;
//            EsBlockCountSizes = EsBlockCountSize + "B";
//            EsAvailableBlocksSizes = EsAvailableBlocksSize + "B";
//        }
//    }
}
