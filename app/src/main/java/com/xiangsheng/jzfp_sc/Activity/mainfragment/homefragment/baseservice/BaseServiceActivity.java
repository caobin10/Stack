package com.xiangsheng.jzfp_sc.Activity.mainfragment.homefragment.baseservice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.xiangsheng.jzfp_sc.R;

/**
 * Created by Administrator on 2018/5/2.
 */
//基础服务
public class BaseServiceActivity extends Activity implements OnClickListener
{
    private ImageButton ibtn_back;
    private EditText et_hint_cond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        init();
    }

    private void init() {
        ibtn_back = (ImageButton) findViewById(R.id.ibtn_back);
        et_hint_cond = (EditText) findViewById(R.id.et_hint_cond);
        ibtn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ibtn_back:
                finish();
                break;
            case R.id.et_hint_cond:

                break;
        }

    }
}
