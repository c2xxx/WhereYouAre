package com.chen.whereyouare;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chen.whereyouare.position.MyLocationListener;
import com.chen.whereyouare.utils.Logger;
import com.chen.whereyouare.utils.Utils;

import java.security.MessageDigest;

/**
 * Created by ChenHui on 2016/11/1.
 */

public class MyApplication extends Application {
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("onCreate " + Utils.getCurProcessName(this));
        if (getPackageName().equals(Utils.getCurProcessName(this))) {
            mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
            mLocationClient.registerLocationListener(myListener);    //注册监听函数
            initPosition();
            start();
        }
        String strMd5 = getSignMd5();
        Logger.d("strMd5= " + strMd5);
    }

    private void start() {
        mLocationClient.start();
        new Thread() {
            @Override
            public void run() {
                int index = 0;
                while (true) {
                    index++;
                    try {
//                        Logger.d("requestLocation");
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (index > 10) {
                        mLocationClient.stop();
                        break;
                    }
                }
            }
        }.start();
    }

    /**
     * 设置定位参数
     */
    private void initPosition() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 10 * 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 获取app签名md5值
     */
    public String getSignMd5() {
        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            String signStr = signatureMD5(sign);
            return signStr;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String signatureMD5(Signature  signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (signatures != null) {
                digest.update("123".getBytes());
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            return "";
        }
    }

    private static String toHexString(byte[] b) {
        char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        StringBuilder sb = new StringBuilder(b.length * 2);


        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }
}

