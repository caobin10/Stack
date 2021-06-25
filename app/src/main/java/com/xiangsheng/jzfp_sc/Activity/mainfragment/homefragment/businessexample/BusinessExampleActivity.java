package com.xiangsheng.jzfp_sc.Activity.mainfragment.homefragment.businessexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.view.View.OnClickListener;

import com.xiangsheng.jzfp_sc.R;

/**
 * Created by Administrator on 2018/5/2.
 */
//业务例子
public class BusinessExampleActivity extends Activity implements OnClickListener{
    private ImageButton ibtn_back ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        init();
    }

    private void init() {
        ibtn_back = (ImageButton) findViewById(R.id.ibtn_back);
        ibtn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ibtn_back:
                finish();
                break;
        }
    }
}
