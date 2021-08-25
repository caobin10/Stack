package com.demo.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.test.R;
import com.demo.test.util.CharSeqUtil;
import com.demo.test.util.DialogUtil;
import com.demo.test.util.PhoneViewUtils;
import com.demo.test.view.SweetDialog;

/**
 * Created by Administrator on 2018/2/7.
 */

public class MainActivity extends Activity implements View.OnClickListener{
    private SweetDialog dialog = null;
    private EditText input;
    private Button ensureBtn;
    private TextView errHintTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ((TextView)findViewById(R.id.name)).setOnClickListener(this);
        ((TextView)findViewById(R.id.identNum)).setOnClickListener(this);
        ((TextView)findViewById(R.id.telephone)).setOnClickListener(this);
        ((TextView)findViewById(R.id.password)).setOnClickListener(this);
        ((TextView)findViewById(R.id.postcode)).setOnClickListener(this);
        ((TextView)findViewById(R.id.houseAveNum)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.name:
                ShowView("name");
                break;
            case R.id.identNum:
                ShowView("identNum");
                break;
            case R.id.telephone:
                ShowView("telephone");
                break;
            case R.id.password:
                ShowView("password");
                break;
            case R.id.postcode:
                ShowView("postcode");
                break;
            case R.id.houseAveNum:
                ShowView("houseAveNum");
                break;
        }
    }

    private void ShowView(final String Type){
        dialog = DialogUtil.createInpDefault(MainActivity.this, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (errHintTv != null && ensureBtn != null){
                    if ("name".equals(Type)){
                        if (!CharSeqUtil.isAllChinese(s.toString())){
                            ensureBtn.setEnabled(false);
                            errHintTv.setText("姓名只能是汉字！");
                            errHintTv.setVisibility(View.VISIBLE);
                        }else {
                            errHintTv.setVisibility(View.GONE);
                            ensureBtn.setEnabled(true);
                        }
                    }else if ("identNum".equals(Type)){
                        if (!CharSeqUtil.isIdentNum(s.toString())) {
                            ensureBtn.setEnabled(false);
                            errHintTv.setText("无效的身份证号！");
                            errHintTv.setVisibility(View.VISIBLE);
                        } else {
                            errHintTv.setVisibility(View.GONE);
                            ensureBtn.setEnabled(true);
                        }
                    }else if ("telephone".equals(Type)){
//如果是电话显示 拨打
                        ImageView phonetIv = dialog.getView(R.id.iv_phone);
                        PhoneViewUtils.showOrHindPhoneView(MainActivity.this, phonetIv, s.toString());

                        if (!CharSeqUtil.isMobilePhone(s.toString())) {
                            ensureBtn.setEnabled(false);
                            if (s.toString().equals("")) {
                                ensureBtn.setEnabled(true);
                            }
                            errHintTv.setText("手机号码格式错误!");
                            errHintTv.setVisibility(View.VISIBLE);
                        } else {
                            errHintTv.setVisibility(View.GONE);
                            ensureBtn.setEnabled(true);
                        }
                    }else if ("password".equals(Type)){
                        if (CharSeqUtil.isPassword(s.toString())){
                            errHintTv.setVisibility(View.GONE);
                            ensureBtn.setEnabled(true);
                        }else {
                            ensureBtn.setEnabled(false);
                            errHintTv.setText("无效的密码(只是字母、数字)");
                            errHintTv.setVisibility(View.VISIBLE);
                        }
                    }else if ("postcode".equals(Type)){
                        if (!CharSeqUtil.isPostCode(s.toString())) {
                            ensureBtn.setEnabled(false);
                            errHintTv.setText("无效的邮政编码！");
                            errHintTv.setVisibility(View.VISIBLE);
                        } else {
                            errHintTv.setVisibility(View.GONE);
                            ensureBtn.setEnabled(true);
                        }
                    }else if ("houseAveNum".equals(Type)){
                        int houseAveNum = CharSeqUtil.parseInt(s.toString(), -1);
                        if (houseAveNum == -1) {
                            ensureBtn.setEnabled(false);
                            errHintTv.setText("输入值只能是数字");
                            errHintTv.setVisibility(View.VISIBLE);
                        } else if (houseAveNum < 1 && houseAveNum > 300) {
                            ensureBtn.setEnabled(false);
                            errHintTv.setText("人均住房面积应不小于1且不大于最大允许值300");
                            errHintTv.setVisibility(View.VISIBLE);
                        } else {
                            errHintTv.setVisibility(View.GONE);
                            ensureBtn.setEnabled(true);
                        }
                    }
                }
            }
        },new DialogUtil.OnResultCallback<CharSequence>(){
            @Override
            public void onResult(CharSequence obj){

            }
        });
        if (dialog != null) {
            dialog.show();
        }
        input = dialog.getView(R.id.et_dialog_inp);
        errHintTv = dialog.getView(R.id.tv_err_hint);
        ensureBtn = dialog.getView(R.id.btn_ensure);
    }
}
