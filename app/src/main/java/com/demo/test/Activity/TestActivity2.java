package com.demo.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demo.test.R;
import com.demo.test.base.BaseActivity;

public class TestActivity2 extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
    }

    public void button3(View view) {

        Intent intent = new Intent(TestActivity2.this, TestActivity3.class);
        startActivity(intent);
    }

}
