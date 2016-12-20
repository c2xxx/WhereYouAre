package com.chen.whereyouare.position;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by ChenHui on 2016/11/1.
 */

public class BaiduPositionHelper {

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();


    /**
     * @param application
     * @param span        时间间隔，毫秒，0则只定位一次
     */
    public BaiduPositionHelper(Context application, int span) {
        mLocationClient = new LocationClient(application);     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initPosition(span);
    }

    /**
     * 设置定位参数
     *
     * @param span 时间间隔，毫秒，0则只定位一次
     */
    private void initPosition(int span) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span = 10 * 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public void start() {
        mLocationClient.start();
    }

    public void stop() {
        mLocationClient.stop();
    }
}
