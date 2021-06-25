package com.xiangsheng.jzfp_sc.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiangsheng.jzfp_sc.R;
import com.xiangsheng.jzfp_sc.base.BaseActivity;
import com.xiangsheng.jzfp_sc.bean.TicketAuth;
import com.xiangsheng.jzfp_sc.manager.ACache;
import com.xiangsheng.jzfp_sc.manager.AppApplication;
import com.xiangsheng.jzfp_sc.manager.AppConfig;
import com.xiangsheng.jzfp_sc.manager.AppManager;

import java.util.HashMap;
import java.util.Map;

public class WelcomActivity extends BaseActivity {
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        ImageView imageView = (ImageView) findViewById(R.id.iv_certifi);
        init(imageView);
        setListener();
    }

    private void init(ImageView imageView) {
        animation = AnimationUtils.loadAnimation(WelcomActivity.this, R.anim.welcome_fade_in);
        animation.setDuration(1500);
        imageView.startAnimation(animation);
    }
    private void setListener()
    {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation)
            {
                //^^

                Map<String,Boolean> dbMap = new HashMap<>();
                dbMap.put(AppConfig.DB_BASIC_NAME,true);
                try {
                    AppManager.getAppManager().copyDbFileFromAsset(WelcomActivity.this,dbMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                ACache cache = ACache.get(WelcomActivity.this);
                TicketAuth auth = null;
                try{
                    auth = (TicketAuth) cache.getAsObject("auth");
                }catch (Exception e){
                    Toast.makeText(WelcomActivity.this,"出错了："+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                //^^

                Intent intent = new Intent();
                Class<?> cls = null;
                //^^
                if (auth != null)
                {
                    AppApplication application = (AppApplication) getApplication();
                    application.setAuth(auth);
                    cls =StaffIndexActivity.class;
                    intent.setClass(WelcomActivity.this,cls);
                    startActivity(intent);
                    WelcomActivity.this.finish();
                }
                else
                {
                    //^
                    SharedPreferences preferences=getSharedPreferences("auth", Context.MODE_PRIVATE);//preferences:偏好，Shared:共享
                    //^
                    cls = LoginActivity.class;
                    intent.setClass(WelcomActivity.this,cls);
                    startActivity(intent);
                    overridePendingTransition(R.anim.base_slide_top_in,R.anim.base_slide_top_out);
                }
                //^^


            }
        });
    }

}
