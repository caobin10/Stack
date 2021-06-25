package com.xiangsheng.jzfp_sc.util;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2018/2/11.
 */

public class BitmapHelper
{
    private static BitmapUtils utils;

//    static
//    {
//        utils = new BitmapUtils(UIUtils.getContext());
//    }

    public static <T extends View> void display(T container, String uri)
    {
        utils.display(container, uri);
    }
}
