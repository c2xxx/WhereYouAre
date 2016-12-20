package com.chen.whereyouare.bean;

import com.alibaba.fastjson.JSON;

/**
 * Created by ChenHui on 2016/11/11.
 */

public class Position implements Comparable<Position> {
    private long timeSpan;//时间戳
    private String mapType;//baidu,gaode,tengxun
    private String imei;//
    private String device;//设备，努比亚，魅蓝等
    private double longtitude;//经度
    private double latitude;//纬度
    private String addrStr;//大概地址，哪条路
    private String locationdescribe;//具体地址描述
    private String poilist;//具体地址，可能的列表

    private boolean local_isDetailExpand;//详情是展开的

    public Position() {
    }

    /**
     * @param timeSpan         时间戳
     * @param mapType          地图类型
     * @param device           设备
     * @param imei             imei号
     * @param longtitude       经纬度
     * @param latitude         经纬度
     * @param addrStr          大概地址
     * @param locationdescribe 地址描述
     * @param poilist          可能的位置
     */
    public Position(long timeSpan, String mapType, String device, String imei, double longtitude, double latitude, String addrStr, String locationdescribe, String poilist) {
        this.timeSpan = timeSpan;
        this.mapType = mapType;
        this.imei = imei;
        this.device = device;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.addrStr = addrStr;
        this.locationdescribe = locationdescribe;
        this.poilist = poilist;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public long getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(long timeSpan) {
        this.timeSpan = timeSpan;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPoilist() {
        return poilist;
    }

    public void setPoilist(String poilist) {
        this.poilist = poilist;
    }

    public String getAddrStr() {
        return addrStr;
    }

    public void setAddrStr(String addrStr) {
        this.addrStr = addrStr;
    }

    public String getLocationdescribe() {
        return locationdescribe;
    }

    public void setLocationdescribe(String locationdescribe) {
        this.locationdescribe = locationdescribe;
    }

    public boolean isLocal_isDetailExpand() {
        return local_isDetailExpand;
    }

    public void setLocal_isDetailExpand(boolean local_isDetailExpand) {
        this.local_isDetailExpand = local_isDetailExpand;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    @Override
    public int compareTo(Position p) {
        if (timeSpan > p.getTimeSpan()) {
            return -1;
        } else if (timeSpan < p.getTimeSpan()) {
            return 1;
        }
        return 0;
    }
}
