package com.chen.whereyouare.utils;

import android.widget.Toast;

import com.chen.whereyouare.MyApplication;

/**
 * Created by ChenHui on 2016/11/11.
 */
public class ToastUtil {

    private static Toast toast = Toast.makeText(MyApplication.getContext(), "", Toast.LENGTH_LONG);

    public static void show(String msg) {
        toast.setText(msg);
        toast.show();
    }
}
