package com.chen.whereyouare.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.chen.whereyouare.utils.Logger;

/**
 * Created by ChenHui on 2016/11/16.
 */

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Logger.d(intent.getAction());
        }
    }
}