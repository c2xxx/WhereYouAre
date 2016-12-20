package com.chen.whereyouare.utils;

import android.util.Log;

/**
 * Created by ChenHui on 2016/11/1.
 */

public class Logger {
    private static final String TAG = "Logger";

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(TAG, "[" + tag + "]" + msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void e(Throwable err) {
        Log.e(TAG, Log.getStackTraceString(err));
    }
}
