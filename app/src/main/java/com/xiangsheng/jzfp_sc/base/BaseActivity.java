package com.xiangsheng.jzfp_sc.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xiangsheng.jzfp_sc.R;

/**
 * Created by Administrator on 2018/2/11.
 */

public class BaseActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏 颜色

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(this.getResources().getColor(R.color.black));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void preset_Onclick(View v)
    {
        switch (v.getId()) {
            case R.id.ibtn_back:
                finish();
                break;
            default:
                break;
        }
    }
}
