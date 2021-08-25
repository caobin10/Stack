package com.demo.test.util;

import android.content.Context;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.demo.test.R;
import com.demo.test.view.SweetDialog;

public class DialogUtil {

    public static SweetDialog createInpDefault(Context context, TextWatcher textWatcher, final OnResultCallback<CharSequence> callback) {
        final SweetDialog dialog = new SweetDialog(context);
        dialog.setContentView(R.layout.dialog_inp_default);
        dialog.setDefault();
        dialog.setTextWatch(R.id.et_dialog_inp, textWatcher)
                .setOnClickListener(R.id.btn_ensure, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence inpText = ((TextView) dialog.getView(R.id.et_dialog_inp)).getText();
                        dialog.dismiss();
                        callback.onResult(inpText);
                    }
                })
                .setOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        return dialog;
    }

    public interface OnResultCallback<T> {
        public void onResult(T t);
    }

}
