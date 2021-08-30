package com.demo.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.test.R;
import com.demo.test.base.BaseActivity;

public class TestActivity1 extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
    }

    public void button2(View view) {
        Intent intent = new Intent(TestActivity1.this, TestActivity2.class);
        startActivity(intent);
    }

}
