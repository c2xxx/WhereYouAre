package com.chen.whereyouare.net;

import retrofit2.Retrofit;

/**
 * Created by ChenHui on 2016/11/14.
 */

public class QiniuApiHelper {
    private static final String BASE_URL_QINIU_UPLOAD = "http://upload.qiniu.com/";
    private static final String BASE_URL_QINIU_MYPOSITION = "http://ofyg5lt81.bkt.clouddn.com/";
//    public static final String BASE_URL_QINIU_UPLOAD = "http://192.168.2.67:8080/";

    public static QiniuApi getInstanceUpload() {
        return getRetrofitUpload().create(QiniuApi.class);
    }

    public static QiniuApi getInstanceMyPosition() {
        return getRetrofitMyPosition().create(QiniuApi.class);
    }


    private static Retrofit retrofitUpload = null;

    public static Retrofit getRetrofitUpload() {
        //创建Retrofit实例
        if (retrofitUpload == null)
            retrofitUpload = new Retrofit.Builder()
                    .baseUrl(BASE_URL_QINIU_UPLOAD)
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .build();
        return retrofitUpload;
    }


    private static Retrofit retrofitMyPosition = null;

    public static Retrofit getRetrofitMyPosition() {
        //创建Retrofit实例
        if (retrofitMyPosition == null)
            retrofitMyPosition = new Retrofit.Builder()
                    .baseUrl(BASE_URL_QINIU_MYPOSITION)
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .build();
        return retrofitMyPosition;
    }
}
