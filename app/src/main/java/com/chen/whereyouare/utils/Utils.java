package com.chen.whereyouare.utils;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by ChenHui on 2016/11/1.
 */

public class Utils {
    /**
     * 获取进程名称
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
}
