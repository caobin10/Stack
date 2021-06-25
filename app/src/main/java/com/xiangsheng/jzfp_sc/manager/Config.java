package com.xiangsheng.jzfp_sc.manager;

/**
 * Created by Administrator on 2018/2/26.
 */

public class Config
{
    public static final int EDITION = Constant.RELEASE;

    public static final String BASE_URL = EDITION == Constant.DEBUG ? Constant.BASE_URL_DEBUG : Constant.BASE_URL_RELEASE;
}
