package com.chen.whereyouare.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.chen.whereyouare.MyApplication;

import java.text.SimpleDateFormat;

/**
 * Created by ChenHui on 2016/11/11.
 */
public class MyUtil {

    public static String timeSpan2Str(long timeSpan) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timeSpan);
    }

    /**
     * get IMEI code
     *
     * @return imei code
     */
    public static String getDeviceImei() {
        Context context = MyApplication.getContext();
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imeiCode = manager.getDeviceId();
        if (TextUtils.isEmpty(imeiCode)) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String macAddress = info.getMacAddress();
            if (TextUtils.isEmpty(macAddress)) {
                macAddress = "12345";
            }
            return MD5Util.MD5(macAddress);
        }
        return imeiCode;
    }
}
