package com.xiangsheng.jzfp_sc.util;

import android.os.Handler;
import android.os.Looper;

import com.xiangsheng.jzfp_sc.http.HttpException;
import com.xiangsheng.jzfp_sc.http.HttpRequest;
import com.xiangsheng.jzfp_sc.http.callback.HttpListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2018/2/26.
 */

public class OkHttpUtil
{
    private volatile static OkHttpUtil instance;
    private OkHttpClient client;

    private Handler mHandler;

    private OkHttpUtil() {
        if (client == null) {
//            client = new OkHttpClient();
            client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Response originalResponse = chain.proceed(chain.request());
                            return originalResponse;
                        }
                    })
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        mHandler = new Handler(Looper.getMainLooper());
    }

    public OkHttpUtil(OkHttpClient httpClient) {
        if (httpClient == null) {
            httpClient = new OkHttpClient();
        }
        this.client = httpClient;
    }

    public static OkHttpUtil instance() {
        if (instance == null) {
            synchronized (OkHttpUtil.class) {
                if (instance == null) {
                    instance = new OkHttpUtil();
                }
            }
        }
        return instance;
    }

    public OkHttpClient.Builder clone() {
        return client.newBuilder();
    }

    public OkHttpClient getClient() {
        return client;//client:客户端
    }

    public Call buildCall(HttpRequest request) {
        return buildCall(client, request);
    }

    public Call buildCall(OkHttpClient client, HttpRequest request) {
        return client.newCall(request.getRequest());
    }

    public <Result> void execute(HttpRequest request, HttpListener<Result> callback) {
        final Call call = buildCall(request);
        call.cancel();
        execute(call, callback);
    }

    public <Result> void execute(OkHttpClient client, HttpRequest request, HttpListener<Result> callback) {
        final Call call = buildCall(client, request);
        execute(call, callback);
    }

    public <Result> void execute(Call call, HttpListener<Result> callback) {
        callback.onStart(call);
        try {
            Response response = call.execute();
            if (call.isCanceled()) {
                callback.onFailure(new HttpException("Canceled"));
            } else {
                int statusCode = response.code();
                if (response.isSuccessful()) {
                    Result result = callback.onResponse(response);
                    callback.onSuccess(result);
                } else {
                    callback.onFailure(new HttpException(statusCode));
                }
            }
        } catch (IOException e) {
            callback.onFailure(new HttpException(e));
        } finally {
            callback.onFinish();
        }
    }

    public <Result> void enqueue(HttpRequest request, HttpListener<Result> callback) {
        Call call = buildCall(request);
        enqueue(call, callback);
    }

    public <Result> void enqueue(OkHttpClient client, HttpRequest request, HttpListener<Result> callback) {
        final Call call = buildCall(client, request);
        enqueue(call, callback);
    }

    public <Result> void enqueue(Call call, final HttpListener<Result> callback) {
        callback.onStart(call);
        try {
            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(new HttpException(e));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        Result result = callback.onResponse(response);
                        callback.onSuccess(result);
                    } else {
                        callback.onFailure(new HttpException(statusCode));
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure(new HttpException(e));
        } finally {
            callback.onFinish();
        }
    }


    //设置信任证书
    public void trustCertificates(InputStream... certificates) {
        try {
//            InputStream is = new Buffer().writeUtf8(Constant.CER_12306).inputStream();

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init(
                    null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom()
            );

            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            new OkHttpClient.Builder().sslSocketFactory(socketFactory, Platform.get().trustManager(socketFactory));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //该方法在反序列化时会被调用，该方法不是接口定义的方法，有点儿约定俗成的感觉
    protected Object readResolve() throws ObjectStreamException {
        return instance;
    }
}
