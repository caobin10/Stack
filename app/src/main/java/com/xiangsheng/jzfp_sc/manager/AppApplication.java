package com.xiangsheng.jzfp_sc.manager;

import android.app.Application;
import android.content.Context;

import com.xiangsheng.jzfp_sc.R;
import com.xiangsheng.jzfp_sc.bean.TicketAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/27.
 */

public class AppApplication extends Application
{
    public TicketAuth auth;
    public static AppApplication instance;

    public static List<Integer> allpeopleId = new ArrayList<>();
    public static List<Integer> allpeopleId2 = new ArrayList<>();




    public static AppApplication getInstance() {
        return (AppApplication) instance;
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public TicketAuth getAuth() {
        return auth;
    }

    public void setAuth(TicketAuth auth)
    {
//        auth.setUnitCode(auth.getUnit().getUnitcode());
        this.auth = auth;
    }

    private void init()
    {
        allpeopleId.add(R.id.tv_value1);
        allpeopleId.add(R.id.tv_value2);
        allpeopleId.add(R.id.tv_value3);

        allpeopleId2.add(R.id.tv_value4);
        allpeopleId2.add(R.id.tv_value5);
        allpeopleId2.add(R.id.tv_value6);

    }

}
