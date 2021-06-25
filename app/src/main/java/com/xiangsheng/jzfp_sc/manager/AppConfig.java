package com.xiangsheng.jzfp_sc.manager;

/**
 * Created by Administrator on 2018/2/27.
 */

public class AppConfig
{
    public static final String DATABASE_NAME = "weixing";

    public static final int RESPONSE_OK = 1000;

    public static final int LIST_LIMIT=10;

//    success = 1000,//成功状态
//    error = 2000, //错误状态
//    tokenError = 20001,//令牌错误
//    tokenTime = 20002,    //令牌过期
//    signaError = 2003, //签名错误状态

    public static final String PRIVATE_KEY="flyingup-scdb-xdsfdswwxf";

    public static final String DB_BASIC_NAME="basic.db";

    public static final String ASSET_DB_DIR="db";

    public static class Url {
        public static final String BASE_URL = "http://198.168.1.162:20419/";
    }

    public static enum AuthorizeType{
        Basic,
        Form,
        Mobile,
        Open
    }
}
