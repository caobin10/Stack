package com.demo.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.test.R;
import com.demo.test.base.BaseActivity;
import com.demo.test.manager.AppManager;


/**
 * Created by Administrator on 2018/2/7.
 */

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void button1(View view){

        Intent intent = new Intent(MainActivity.this,TestActivity1.class);
        startActivity(intent);
    }

}
