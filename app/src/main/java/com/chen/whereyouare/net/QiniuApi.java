package com.chen.whereyouare.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by ChenHui on 2016/11/14.
 */

public interface QiniuApi {
    @Multipart
    @POST("/")
    Call<ResponseBody> upload(@Part("key") RequestBody key, @Part("token") RequestBody token,
                              @Part MultipartBody.Part file);

    @Multipart
    @POST("/")
    Call<ResponseBody> post(@PartMap Map<String, RequestBody> params);

    @GET("/")
    Call<ResponseBody> get(@QueryMap Map<String, String> usermaps);

    @GET("{filename}")
    Call<ResponseBody> loadContent(@Path("filename") String filename, @Query("t") String randomStr);
}
