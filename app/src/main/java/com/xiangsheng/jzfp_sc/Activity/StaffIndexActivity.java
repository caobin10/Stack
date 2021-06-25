package com.xiangsheng.jzfp_sc.Activity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangsheng.jzfp_sc.Activity.mainfragment.aboutfragment.AboutFragment;
import com.xiangsheng.jzfp_sc.Activity.mainfragment.homefragment.HomeFragment;
import com.xiangsheng.jzfp_sc.Activity.mainfragment.msgfragment.MsgFragment;
import com.xiangsheng.jzfp_sc.Activity.mainfragment.profilefragment.ProfileFragment;
import com.xiangsheng.jzfp_sc.R;
import com.xiangsheng.jzfp_sc.adapter.LfFragmentPagerAdapter;
import com.xiangsheng.jzfp_sc.base.BaseActivity;

import org.chuck.view.SyncHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2018/2/8.
 */

public class StaffIndexActivity extends BaseActivity {
    private ViewPager viewPager;//ViewPager(视图滑动切换工具)
    private FragmentPagerAdapter pagerAdapter;

    private FragmentTransaction transaction;

    private Fragment newsFragment;//消息
    private Fragment homeFragment;//首页
    private Fragment profileFragment;//个人
    private Fragment aboutFragment;//关于
    private TextView headTitleTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        radiobuttonicon();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void init() {
        //------------------------------------------------------------------------------------------------------------
        final SyncHorizontalScrollView navHSV = (SyncHorizontalScrollView) findViewById(R.id.hsv_nav);
        navHSV.setHorizontalScrollBarEnabled(false);
        navHSV.setSmoothScrollingEnabled(true);


//--------------------------------------------------------------------------------------------------------------

//        headTitleTv = (TextView) findViewById(R.id.tv_head_title);

//        transaction = getFragmentManager().beginTransaction();//FragmentManager:碎片栈,transaction:事务,begin:开始
//        homeFragment = new HomeFragment();
//        headTitleTv.setText(R.string.main_home);
//        transaction.add(R.id.container, homeFragment);
//        transaction.commit();//commit:提交
        //原来：final RadioGroup tabBarRg = (RadioGroup) findViewById(R.id.rg_main_tabbar);
        final RadioGroup tabBarRg = (RadioGroup) findViewById(R.id.rg_main_tabbar);
        //tabBarRg.setOnCheckedChangeListener(checkedChangeListener);

//-----------------
        tabBarRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    View child = group.getChildAt(i);
                    if (child.getId() == checkedId) {
                        int scrollX = child.getLeft() - (navHSV.getWidth() - child.getWidth()) / 2;
                        navHSV.animOnScroll(scrollX);
                        if (viewPager.getCurrentItem() != i) {
                            viewPager.setCurrentItem(i);//跳转到某一个页面
                        }
                        break;
                    }
                }
            }
        });
        viewPager = (ViewPager) findViewById(R.id.vp_pager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MsgFragment());

        homeFragment = new HomeFragment();
        fragments.add(homeFragment);

        fragments.add(new ProfileFragment());
        fragments.add(new AboutFragment());
        pagerAdapter = new LfFragmentPagerAdapter(getFragmentManager(), fragments);
        try {
            viewPager.setAdapter(pagerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                CompoundButton child = (CompoundButton) tabBarRg.getChildAt(position);
                child.setChecked(true);
                switch (position) {
                    case 0:
//                        headTitleTv.setText(R.string.main_new_msg);
                        break;
                    case 1:
//                        headTitleTv.setText(R.string.main_home);
                        break;
                    case 2:
//                        headTitleTv.setText(R.string.main_profile);
                        break;
                    case 3:
//                        headTitleTv.setText(R.string.main_about);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                int a = state;
            }
        });
//-----------------
    }

    private void radiobuttonicon() {
        RadioButton[] rbs = new RadioButton[4];
        rbs[0] = (RadioButton) findViewById(R.id.rb_new_msg);
        rbs[1] = (RadioButton) findViewById(R.id.rb_home);
        rbs[2] = (RadioButton) findViewById(R.id.rb_profile);
        rbs[3] = (RadioButton) findViewById(R.id.rb_about);

        for (RadioButton rb : rbs) {
            Drawable[] drs = rb.getCompoundDrawables();
            Rect r = new Rect(0, 0, drs[1].getMinimumWidth() * 1 / 8, drs[1].getMinimumHeight() * 1 / 8);//矩形
            drs[1].setBounds(r);
            rb.setCompoundDrawables(null, drs[1], null, null);
        }
    }

    //双击返回键后到手机主屏幕
    private boolean enableExit = false;
    @Override
    public void onBackPressed()
    {
        if (!enableExit)
        {
            enableExit = true;
            Toast.makeText(StaffIndexActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            Timer timer=new Timer();
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    enableExit=false;
                }
            }, 2000);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//任务
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }
}
