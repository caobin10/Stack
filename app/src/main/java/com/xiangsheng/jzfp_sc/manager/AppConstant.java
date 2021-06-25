package com.xiangsheng.jzfp_sc.manager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2018/2/28.
 */

public class AppConstant
{
    public static OkHttpClient.Builder mBuilder = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
}
