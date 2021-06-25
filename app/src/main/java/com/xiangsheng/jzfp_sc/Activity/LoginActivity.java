package com.xiangsheng.jzfp_sc.Activity;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.xiangsheng.jzfp_sc.DataService;
import com.xiangsheng.jzfp_sc.R;
import com.xiangsheng.jzfp_sc.base.BaseActivity;
import com.xiangsheng.jzfp_sc.bean.TicketAuth;
import com.xiangsheng.jzfp_sc.dao.UnitDao;
import com.xiangsheng.jzfp_sc.db.BasicDaoMaster;
import com.xiangsheng.jzfp_sc.db.BasicDaoSession;
import com.xiangsheng.jzfp_sc.factory.DaoFactory;
import com.xiangsheng.jzfp_sc.http.HttpResponse;
import com.xiangsheng.jzfp_sc.manager.AppApplication;
import com.xiangsheng.jzfp_sc.manager.AppConfig;
import com.xiangsheng.jzfp_sc.network.NetworkProxy;
import com.xiangsheng.jzfp_sc.pojo.Unit;
import com.xiangsheng.jzfp_sc.util.DialogUtil;
import com.xiangsheng.jzfp_sc.util.EnDecryptUtil;
import com.xiangsheng.jzfp_sc.util.OkHttpUtil;
import com.xiangsheng.jzfp_sc.util.RetrofitUtil;
import com.xiangsheng.jzfp_sc.util.ToastUtil;
import com.xiangsheng.jzfp_sc.util.Utils;

import org.chuck.util.CharSeqUtil;
import org.chuck.view.SweetDialog;

import de.greenrobot.dao.query.Query;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/7.
 */

public class LoginActivity extends BaseActivity implements OnClickListener {
    private Drawable login_user;
    private Drawable login_password;
    private EditText userNameEt;
    private EditText passwordEt;
    private Button loginBtn;
    private SweetDialog dialog;

    private ImageView imageview;

    private ImageView iv_profile;
    private ImageView iv_lock;
    private View view_login_username;
    private View view_login_password;
    private LinearLayout relContent;
    private RelativeLayout llLoginRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_login);
        } catch (Exception e) {
            System.out.println("_________________________________________" + e.toString());
        }
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.s3);
//        if (bmp != null) {
//            Bitmap blurBitmap = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                blurBitmap = BlurBitmapUtil.blurBitmap(LoginActivity.this, bmp, 20);
//            }
//            imageview.setImageBitmap(blurBitmap);
//        }

        init();
//        keepLoginBtnNotOver(llLoginRoot,relContent); //触摸外部，键盘消失

    }

    private void init() {

//        llLoginRoot = (RelativeLayout) findViewById(R.id.rl_login_root);
//        relContent = (LinearLayout) findViewById(R.id.ll_content);

//        imageview = (ImageView) findViewById(R.id.image);

        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        iv_lock = (ImageView) findViewById(R.id.iv_lock);


        userNameEt = (EditText) findViewById(R.id.et_user_name);
        passwordEt = (EditText) findViewById(R.id.et_password);

        view_login_username = (View) findViewById(R.id.view_login_username);
        view_login_password = (View) findViewById(R.id.view_login_password);

        loginBtn = (Button) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(this);
        loginBtn.setBackgroundResource(R.drawable.shape2);

//        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.s3);
//        if (bmp != null) {
//            Bitmap blurBitmap = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                blurBitmap = BlurBitmapUtil.blurBitmap(LoginActivity.this, bmp, 20);
//            }
//            imageview.setImageBitmap(blurBitmap);
//        }
        userNameEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    iv_profile.setImageResource(R.drawable.turquoise_profile);
                    view_login_username.setBackgroundResource(R.drawable.shape2);
                } else {
                    iv_profile.setImageResource(R.drawable.white_profile);
                    view_login_username.setBackgroundResource(R.drawable.shape);
                }
            }
        });

        passwordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {//Focus:焦点
                if (b) {
                    iv_lock.setImageResource(R.drawable.turquoise_lock);
                    view_login_password.setBackgroundResource(R.drawable.shape2);
                } else {
                    iv_lock.setImageResource(R.drawable.white_lock);
                    view_login_password.setBackgroundResource(R.drawable.shape);
                }

            }
        });

//        //触摸外部，键盘消失
//        llLoginRoot.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override public boolean onTouch(View v, MotionEvent event)
//            {
//                Utils.closeKeyboard(LoginActivity.this); return false;
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                final String username = userNameEt.getText().toString().trim();
                final String password = passwordEt.getText().toString().trim();

                if (CharSeqUtil.isEmpty(username)) {
                    ToastUtil.showToast(this, R.string.please_enter_a_username, Toast.LENGTH_LONG);
                    userNameEt.requestFocus();
                    return;
                }
                if (CharSeqUtil.isEmpty(password)) {
                    ToastUtil.showToast(this, R.string.please_enter_a_password, Toast.LENGTH_LONG);
                    passwordEt.requestFocus();
                    return;
                }
                final String passwordMD5Str = EnDecryptUtil.MD5HexEncrypt(password);
                RetrofitUtil.provideRetrofit(OkHttpUtil.instance().getClient(), AppConfig.Url.BASE_URL)
                        .create(DataService.class).login(username, passwordMD5Str)//create:创建
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<HttpResponse<TicketAuth>>() //Observer:观察者
                        {
                            @Override
                            public void onSubscribe(Disposable d) {//Disposable是一个接口,onSubscribe:订阅
                                dialog = DialogUtil.createLoadingDefault(LoginActivity.this, "正在加载");
                                dialog.show();
                            }

                            @Override
                            public void onNext(HttpResponse<TicketAuth> resData)
                            {
                                dialog.dismiss();
                                if (resData.getCode() == AppConfig.RESPONSE_OK) {
                                    final TicketAuth auth = resData.getData();
                                    auth.setUsername(username);
                                    auth.setPassword(passwordMD5Str);
                                    //^
//                                    AppApplication application = (AppApplication) getApplication();
//                                    application.setAuth(auth);
//                                    ACache cache = ACache.get(LoginActivity.this);
//                                    cache.put("auth",auth);
//
//                                    SharedPreferences preferences = getSharedPreferences("auth", Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = preferences.edit();//Editor:编辑，edit:编辑
//                                    editor.putString("username",auth.getUsername());
//                                    editor.putString("password",auth.getPassword());
//                                    editor.commit();//提交数据

                                    getuserunit(auth);

                                    //^


                                    AppApplication app = AppApplication.instance;
                                    AppApplication.instance.setAuth(auth);
                                    startActivity(new Intent(LoginActivity.this, StaffIndexActivity.class));
                                }
//                                ToastUtil.showToast(LoginActivity.this,resData.getMessage(),Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onError(Throwable e) {//Throwable:可抛出
                                dialog.dismiss();
                                e.printStackTrace();
                                ToastUtil.showToast(LoginActivity.this, R.string.please_look_if_connect, Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
        }
    }


    private void keepLoginBtnNotOver(final View root, final View subView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect(); // 获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect); // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom; // 若不可视区域高度大于200，则键盘显示,其实相当于键盘的高度
                if (rootInvisibleHeight > 200) { // 显示键盘时
                    int srollHeight = rootInvisibleHeight - (root.getHeight() - subView.getHeight()) - Utils.getNavigationBarHeight(root.getContext());
                    if (srollHeight > 0) {
                        //当键盘高度覆盖按钮时
                        root.scrollTo(0, srollHeight - 600);//scroll:滚动，to:到，
                    }
                } else { // 隐藏键盘时
                    root.scrollTo(0, 0);
                }
            }
        });
    }

    //点击返回键后到手机主屏幕
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//任务
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void getuserunit(final TicketAuth auth)
    {
        NetworkProxy.provideRetrofit(OkHttpUtil.instance().clone(), AppConfig.Url.BASE_URL)
                .create(DataService.class).GetUserUnit(auth.getUserId().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResponse<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {//Disposable是一个接口,onSubscribe:订阅
                        dialog = DialogUtil.createLoadingDefault(LoginActivity.this, "正在加载");
                        dialog.show();
                    }
                    @Override
                    public void onNext(HttpResponse<Object> resData)
                    {
                        if (resData.getCode() == AppConfig.RESPONSE_OK)
                        {
                            LinkedTreeMap<String,String> s2 = new LinkedTreeMap();
                            s2 = (LinkedTreeMap<String, String>) resData.getData();
                            Unit unit = new Unit();
                            unit.setID(s2.get("ID"));
//                            unit.setUnitcode(s2.get("Code"));
                            BasicDaoMaster daoMaster = DaoFactory.getBasicDaoMaster(LoginActivity.this);
                            BasicDaoSession daoSession=daoMaster.newSession();
                            UnitDao unitDao = daoSession.getUnitDao();
                            Query<Unit> unitQuery = unitDao.queryBuilder().where(UnitDao.Properties.ParentCode.eq(s2.get("Code").toString())).build();
                            String code = unitQuery.unique().getUnitcode();
                            unit.setUnitcode(code);
                            unit.setParentCode(s2.get("ParentCode"));
                            unit.setName(s2.get("Name"));
                            unit.setUnitLevel(String.valueOf(s2.get("UnitLevel")));
                            unit.setRemark(s2.get("Remark"));
                            unit.setDeleteFlag(String.valueOf(s2.get("DeleteFlag")));
                            unit.setDateUpdate(String.valueOf(s2.get("DateUpdate")));
                            unit.setDateCreate(String.valueOf(s2.get("DateCreate")));
                            auth.setUnit(unit);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {//Throwable:可抛出
                        Toast.makeText(LoginActivity.this, "你们", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

}
