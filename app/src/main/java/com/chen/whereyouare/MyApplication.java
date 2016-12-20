package com.chen.whereyouare;

import android.app.Application;
import android.content.Context;

import com.chen.whereyouare.position.BaiduPositionHelper;
import com.chen.whereyouare.utils.Logger;
import com.chen.whereyouare.utils.Utils;

/**
 * Created by ChenHui on 2016/11/1.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        Logger.d("onCreate " + Utils.getCurProcessName(this));
        if (getPackageName().equals(Utils.getCurProcessName(this))) {
            BaiduPositionHelper baiduPositionHelper = new BaiduPositionHelper(this, 5 * 60 * 1000);
            baiduPositionHelper.start();
        }
    }

    public static Context getContext() {
        return mContext;
    }
}

