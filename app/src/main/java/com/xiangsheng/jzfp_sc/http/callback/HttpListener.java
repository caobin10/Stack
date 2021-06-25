package com.xiangsheng.jzfp_sc.http.callback;

import com.xiangsheng.jzfp_sc.http.HttpException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/26.
 */

public interface HttpListener<R>
{
    void onStart(Call call);

    /**
     * Response-->R
     * 此回调最好是在非主线程
     *
     * @param response
     */
    R onResponse(Response response);

    void onSuccess(R r);

    void onFailure(HttpException e);

//    void onError(Call call, Exception e);

    void onFinish();
}
