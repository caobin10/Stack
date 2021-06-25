package com.xiangsheng.jzfp_sc.Activity.mainfragment.homefragment.baseservice;

import android.app.Activity;
import android.os.Bundle;

import com.xiangsheng.jzfp_sc.R;

import org.chuck.view.PinnedSectionListView;

/**
 * Created by Administrator on 2018/5/2.
 */

public class ServiceInformationActivity extends Activity
{
    private PinnedSectionListView contentLv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        init();
    }

    private void init() {
        contentLv = (PinnedSectionListView) findViewById(R.id.lv_content);
    }
}
