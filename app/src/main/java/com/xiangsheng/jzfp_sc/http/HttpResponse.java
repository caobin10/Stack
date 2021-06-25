package com.xiangsheng.jzfp_sc.http;

/**
 * Created by Administrator on 2018/2/27.
 */

public class HttpResponse<T>
{
    private int code;
    private T data;
    private String extra;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
