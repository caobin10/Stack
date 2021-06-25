package com.xiangsheng.jzfp_sc.http;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2018/2/26.
 */

public class HttpException extends Throwable
{
    private int statusCode;
    private String detailMessage;

    public HttpException() {
        super();
    }

    public HttpException(String message) {
        super(message);
        detailMessage = message;
    }

    public HttpException(Exception e) {
        super(e);
        if (e instanceof ConnectException) {
            detailMessage = "";
        } else if (e instanceof UnknownHostException) {
            detailMessage = "";
        } else if (e instanceof SocketException) {
            detailMessage = "";
        } else if (e instanceof SocketTimeoutException) {
            detailMessage = "";
        } else if (e instanceof NullPointerException) {
            detailMessage = "";
        } else if (e instanceof IllegalArgumentException) {
            detailMessage = "";
        } else if (e instanceof IOException) {
            detailMessage = "";
        }
    }

    public HttpException(int statusCode) {
        super(String.valueOf(statusCode));
        this.statusCode = statusCode;
        switch (statusCode) {
            case 301:
                detailMessage = "301";
                break;
            case 302:
                detailMessage = "302";
                break;
            default:
                break;
        }
    }

    @Override
    public String getMessage() {
        return detailMessage == null || "".equals(detailMessage) ? super.getMessage() : detailMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
