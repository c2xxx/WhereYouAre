package com.chen.whereyouare.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ChenHui on 2016/11/2.
 */

public interface GitHubApi {

    @GET("scripts/ad.js")
    Call<ResponseBody> getSimpleGet();


    //ResponseBody是Retrofit自带的返回类，
    @GET("data/sk/101110101.html")
    Call<IpInfo> getIpInfo2();
}