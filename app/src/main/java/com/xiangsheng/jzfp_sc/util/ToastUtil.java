package com.xiangsheng.jzfp_sc.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/2/26.
 */

public class ToastUtil
{
    private static Toast mToast;

    public static void showToast(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public static void showToast(Context context, int resId, int duration) {
        showToast(context, context.getResources().getText(resId), duration);
    }
}
