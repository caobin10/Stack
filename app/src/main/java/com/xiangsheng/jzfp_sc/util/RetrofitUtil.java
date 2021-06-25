package com.xiangsheng.jzfp_sc.util;

import com.xiangsheng.jzfp_sc.manager.Config;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/2/26.
 */

public class RetrofitUtil
{
    //    private volatile static RetrofitUtil instance;
//    private Retrofit retrofit;

    private RetrofitUtil() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(Config.BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
//                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
//                .build();
    }

//    public RetrofitUtil instance() {
//        if (instance == null) {
//            synchronized (RetrofitUtil.class) {
//                if (instance == null) {
//                    instance = new RetrofitUtil();
//                }
//            }
//        }
//        return instance;
//    }

    public static Retrofit provideRetrofit(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Config.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .build();
        return retrofit;
    }

    public static Retrofit provideRetrofit(OkHttpClient client,String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()//retrofit:改造
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .build();
        return retrofit;
    }

    public static Retrofit provideRetrofit(OkHttpClient.Builder builder, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .build();
        return retrofit;
    }

    public static Retrofit.Builder provideRetrofitBuilder(OkHttpClient client) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(client)
                .baseUrl(Config.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 添加Rx适配器
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create());// 添加Gson转换器
        return builder;
    }
}
