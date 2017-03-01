package edu.feicui.app.phone.biz;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/2/18 0018.
 */

public class SystemBiz {
    Context mCt;
//    String basicInfos[] = {"设备型号:", "系统版本:", "手机串号:", "运营商:", "是否ROOT:"};
//    String CPUInfos[] = {"CPU型号:", "CPU核心数:", "最高频率:", "最低频率:", "当前频率:"};
//    String resolutionInfos[] = {"摄像头像素:", "照片最大尺寸:", "闪光灯:"};
//    String pixelInfos[] = {"屏幕分辨率:", "像素密度:", "多点触控:"};
//    String WIFIInfos[] = {"WIFI连接到:", "WIFI地址:", "WIFI连接速度:", "MAC地址:", "蓝牙状态:"};

    public SystemBiz(Context context) {
        this.mCt = context;
    }

    /**
     * 设备系统基带版本
     *
     * @return
     */
    public static String getBaseband_Ver() {
        String Version = "";
        try {
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[]{String.class, String.class});
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});
            Version = (String) result;
        } catch (Exception e) {
        }
        return Version;
    }

    /**
     * 设备系统无线电
     *
     * @return
     */
    public String getPhoneSystemBasebandVersion() {
        return Build.RADIO;
    }

    /**
     * 设备系统版本号
     *
     * @return
     */
    public String getPhoneSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 设备系统SDK版本号
     *
     * @return
     */
    public int getPhoneSystemVersionSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 设备设置版本号
     *
     * @return
     */
    public String getPhoneSystemVersionID() {
        return Build.ID;
    }

    /**
     * 设备CPU类型名称 (品牌？)
     *
     * @return
     */
    public String getPhoneCPUName() {
        return Build.CPU_ABI;
    }

    /**
     * 设备品牌(moto?)
     *
     * @return
     */
    public String getPhoneName1() {
        return Build.BRAND;
    }

    /**
     * 设备型号名称
     *
     * @return
     */
    public String getPhoneModelName() {
        return Build.MODEL;
    }

    /**
     * 设备CPU名称
     *
     * @return
     */
    public String getPhoneCPUName1() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设备CPU数量?
     *
     * @return
     */
    public int getPhoneCPUNumber() {
        File dir = new File("/sys/devices/system/cpu/");
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        });
        return files.length;
    }

    /**
     * 判断当前手机是否有ROOT权限
     *
     * @return
     */
    public boolean isRoot() {
        boolean bool = false;
        if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
            bool = false;

        } else {
            bool = true;
        }
        return bool;
    }

    /**
     * 获取手机分辨率
     *
     * @return
     */
    public String getResolution() {
        String resolution = "";
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) mCt).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        resolution = metrics.widthPixels + "*" + metrics.heightPixels;
        return resolution;
    }

    /**
     * 获取照片最大分辨率
     *
     * @return
     */

    public String getMaxPhoneSize() {

        String maxSize = "";
        Camera camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> sizeList = parameters.getSupportedPictureSizes();
        Camera.Size size = null;
        for (Camera.Size s : sizeList) {
            if (size == null) {
                size = s;
            } else if (size.height * size.width < s.height * s.width) {
                size = s;
            }
        }
        maxSize = size.width + "*" + size.height;
        camera.release();
        return maxSize;
    }
}