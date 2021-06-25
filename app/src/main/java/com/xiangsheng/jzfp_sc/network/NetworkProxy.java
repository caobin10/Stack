package com.xiangsheng.jzfp_sc.network;

import android.util.Log;

import com.xiangsheng.jzfp_sc.bean.TicketAuth;
import com.xiangsheng.jzfp_sc.manager.AppApplication;
import com.xiangsheng.jzfp_sc.manager.AppConfig;
import com.xiangsheng.jzfp_sc.manager.AppConstant;
import com.xiangsheng.jzfp_sc.util.EnDecryptUtil;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/2/28.
 */

public class NetworkProxy
{
    public static Retrofit provideRetrofit() {
        return provideRetrofit(AppConstant.mBuilder, AppConfig.Url.BASE_URL);
    }

    public static Retrofit provideRetrofit(OkHttpClient.Builder builder) {
        return provideRetrofit(builder, AppConfig.Url.BASE_URL);
    }

    public static Retrofit provideRetrofit(String baseUrl) {
        return provideRetrofit(AppConstant.mBuilder, baseUrl);
    }
//Retrofit:改造，provide：提供
    public static Retrofit provideRetrofit(OkHttpClient.Builder builder, String baseUrl) {
        Log.i("aaaaaa", "provideRetrofit");
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        TicketAuth auth = AppApplication.instance.getAuth();
                        long timeSpan = Calendar.getInstance().getTimeInMillis();
                        String signature = "";
                        try {
                            signature = EnDecryptUtil.SHA1Encrypt(
                                    AppConfig.PRIVATE_KEY
                                            + auth.getAccessToken()
                                            + auth.getPassword()
                                            + timeSpan).toUpperCase();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Request request = chain.request().newBuilder()
                                .header("AuthorizeType", AppConfig.AuthorizeType.Mobile.name())//header:头
                                .header("Token", auth.getAccessToken())
                                .header("Timespan", String.valueOf(timeSpan))//time:时间，span:持续
                                .header("Signature", signature)//Signature:签名
                                .build();
                        return chain.proceed(request);
                    }
                }).build())//建造
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
//                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create())) // 添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .build();
        return retrofit;
    }
}
