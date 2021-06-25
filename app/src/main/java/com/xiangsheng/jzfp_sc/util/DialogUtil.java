package com.xiangsheng.jzfp_sc.util;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.xiangsheng.jzfp_sc.R;

import org.chuck.view.SweetDialog;

/**
 * Created by Administrator on 2018/2/12.
 */

public class DialogUtil {
    public static SweetDialog createLoadingDefault(Context context, CharSequence message) {
        SweetDialog dialog = new SweetDialog(context, R.style.ProgressHUD);
        dialog.setContentView(R.layout.progress_hud);
        TextView txt = (TextView) dialog.findViewById(R.id.message);
        txt.setText(message);
        dialog.setCancelable(false);//Cancelable:可撤销
        dialog.setOnCancelListener(null);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static SweetDialog ShowExitAccountDialog(Context context, String msg,OnClickListener ensureListener,OnClickListener cancleListener) {
        final SweetDialog dialog = new SweetDialog(context);
        dialog.setContentView(R.layout.dialog_msg);
        dialog.setDefault();
        dialog.setText(R.id.tv_msg, Html.fromHtml(msg));
        dialog.setOnClickListener(R.id.tv_confirm, ensureListener == null ? new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } : ensureListener);
        dialog.setOnClickListener(R.id.tv_cancel, cancleListener == null ? new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        } : cancleListener);
        return dialog;
    }
}
