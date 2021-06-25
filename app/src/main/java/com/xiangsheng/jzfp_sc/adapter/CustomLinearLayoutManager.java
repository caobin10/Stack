package com.xiangsheng.jzfp_sc.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by Administrator on 2018/4/23.
 */

public class CustomLinearLayoutManager extends LinearLayoutManager
{
    private boolean isScrollEnabled = true;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
