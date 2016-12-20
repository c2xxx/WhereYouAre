package com.chen.whereyouare.position;

import android.os.Build;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.chen.whereyouare.BuildConfig;
import com.chen.whereyouare.bean.Position;
import com.chen.whereyouare.net.UploadPositionHelper;
import com.chen.whereyouare.other.AnyEvent;
import com.chen.whereyouare.utils.Logger;
import com.chen.whereyouare.utils.MyUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by ChenHui on 2016/11/1.
 */
public class MyLocationListener implements BDLocationListener {
//    private List<BDLocation> listLocation = new ArrayList<>();

    private String imeiCode;

    @Override
    public void onReceiveLocation(BDLocation location) {
//        Logger.d("收到定位1：" + location.getAddrStr());
        if (TextUtils.isEmpty(location.getTime())) {
            return;
        }
//        listLocation.add(location);
        uploadIfNeed(location);

        if (!BuildConfig.DEBUG) {
            return;
        }
        //Receive Location
        StringBuffer sb = new StringBuffer(256);
        sb.append("定位时间 : ");
        sb.append(location.getTime());
        sb.append("\n状态码 code : ");
        sb.append(location.getLocType());
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());
//        sb.append("\nradius : ");
//        sb.append(location.getRadius());
        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());// 单位：公里每小时
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// 单位：米
            sb.append("\ndirection : ");
            sb.append(location.getDirection());// 单位度
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\ndescribe : ");
            sb.append("gps定位成功");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            //运营商信息
            sb.append("\noperationers : ");
            sb.append(location.getOperators());
            sb.append("\ndescribe : ");
            sb.append("网络定位成功");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        sb.append("\nlocationdescribe : ");
        sb.append(location.getLocationDescribe());// 位置语义化信息
        List<Poi> list = location.getPoiList();// POI数据
        if (list != null) {
            sb.append("\npoilist size = : ");
            sb.append(list.size());
            for (Poi p : list) {
                sb.append("\npoi= : ");
                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }
        Logger.i("BaiduLocationApiDemo", sb.toString());
    }

    private void uploadIfNeed(BDLocation location) {
        String splitStr = ";";
        StringBuilder poiList = new StringBuilder();
        for (Poi p : location.getPoiList()) {
            poiList.append(splitStr + p.getName());
        }
        String poiListStr = poiList.toString();
        if (poiListStr.length() > 1) {
            poiListStr = poiListStr.replaceFirst(splitStr, "");
        }
        Position position = new Position(
                System.currentTimeMillis(),
                "baidu",
                Build.USER,
                getImei(),
                location.getLongitude(),
                location.getLatitude(),
                location.getAddrStr(),
                location.getLocationDescribe(),
                poiListStr);
        UploadPositionHelper.getInstance().savePosition(position);
        EventBus.getDefault().post(new AnyEvent(AnyEvent.TYPE.RECEIVE_POSITION, position));
    }


    private String getImei() {
        if (imeiCode == null) {
            imeiCode = MyUtil.getDeviceImei();
        }
        return imeiCode;
    }
}
