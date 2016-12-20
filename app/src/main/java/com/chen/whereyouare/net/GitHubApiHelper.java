package com.chen.whereyouare.net;

import retrofit2.Retrofit;

/**
 * Created by ChenHui on 2016/11/10.
 */

public class GitHubApiHelper {
    public static GitHubApi getInstance() {
        return getRetrofit().create(GitHubApi.class);
    }


    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        //创建Retrofit实例
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.weather.com.cn/")
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .build();
        return retrofit;
    }
}
