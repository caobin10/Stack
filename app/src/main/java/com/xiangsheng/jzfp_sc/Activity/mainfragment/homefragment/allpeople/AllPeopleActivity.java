package com.xiangsheng.jzfp_sc.Activity.mainfragment.homefragment.allpeople;


import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiangsheng.jzfp_sc.DataService;
import com.xiangsheng.jzfp_sc.R;
import com.xiangsheng.jzfp_sc.base.BaseActivity;
import com.xiangsheng.jzfp_sc.bean.TicketAuth;
import com.xiangsheng.jzfp_sc.http.HttpResponse;
import com.xiangsheng.jzfp_sc.manager.AppApplication;
import com.xiangsheng.jzfp_sc.manager.AppConfig;
import com.xiangsheng.jzfp_sc.network.NetworkProxy;
import com.xiangsheng.jzfp_sc.pojo.Unit;
import com.xiangsheng.jzfp_sc.ui.UnitTextView2;
import com.xiangsheng.jzfp_sc.util.DialogUtil;
import com.xiangsheng.jzfp_sc.util.OkHttpUtil;

import org.chuck.adapter.CommonAdapter;
import org.chuck.common.ViewHolder;
import org.chuck.util.CharSeqUtil;
import org.chuck.util.JsonUtil;
import org.chuck.view.BadgeView;
import org.chuck.view.SweetDialog;
import org.chuck.view.pullview.PullView;
import org.chuck.view.pullview.PullViewFooter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/6.
 */

public class AllPeopleActivity extends BaseActivity implements OnClickListener
{
    private TextView tv_head_title;
    private ListView contentlv;
    private CommonAdapter<Map<String, Object>> adapter;
    private Map<String, String> baseMap = null;
    private SweetDialog dialog;
    private PullView pullView;
    private int start = 0;
    private int limit = AppConfig.LIST_LIMIT;
    private boolean dismiss = false;
    private BadgeView totalBv;
    private EditText et_hint_cond;
    private boolean once = true;
    private String orderCulum = "fb.name";
    private String orderType = "asc";
    private String where = "";
    private Button btn_query;
    private LinearLayout ll_query;
    private boolean footerpull = false;
//    private UnitTextView unitTv;
    private String unitCode;
    private TicketAuth auth;
    private UnitTextView2 unitTv;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpeople);
        AppApplication application = (AppApplication) getApplication();
        auth = application.getAuth();
        baseMap = getValue();
        init();
    }

    private Map<String, String> getValue()
    {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Name", "姓名");
        map.put("IdentNum", "身份证号");
        map.put("Sex", "性别");
        return map;
    }

    private void init()
    {
        unitTv = (UnitTextView2) findViewById(R.id.tv_unit);
        tv_head_title = (TextView) findViewById(R.id.tv_head_title);
        ll_query = (LinearLayout) findViewById(R.id.ll_query);
        contentlv = (ListView) findViewById(R.id.lv_content);
        pullView = (PullView) findViewById(R.id.pv_container);
        totalBv = (BadgeView) findViewById(R.id.bv_total);
        et_hint_cond = (EditText) findViewById(R.id.et_hint_cond);
        btn_query = (Button) findViewById(R.id.btn_query);

//        unitTv = (UnitTextView)findViewById(R.id.tv_unit);

        btn_query.setOnClickListener(this);

        unitTv.setText(auth.getUnit().getName());

        //^^^
        unitTv.setFinishCallBack(new UnitTextView2.UnitCodeCallBack<Unit>()
        {
            @Override
            public void setCallBack(Unit unit)
            {

            }
        });
        //^^^
//        unitTv.setFinishCallBack(new UnitTextView.UnitCodeCallBack<Unit>()
//        {
//            @Override
//            public void setCallBack(Unit unit)
//            {
//
//                unitTv.setText(unit.getName());
//                unitCode = unit.getCode();
//
//                //加载数据
//                start= 0;
//                limit = AppConfig.LIST_LIMIT;
////                if(CharSeqUtil.isNullOrEmpty(simpleCondEt.getText().toString()))
////                    whereSQL=null;
////                adapter.getAdapterDatas().clear();
////                getDatas();
//            }
//        });
//        tv_head_title.setText("总人口");
        List<Map<String, Object>> datas = getDatas(dismiss);
        adapter = new CommonAdapter<Map<String, Object>>(AllPeopleActivity.this, datas, R.layout.allpeople_item)
        {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> item, View convertView, ViewGroup parent, int position)
            {
                convertView.setBackgroundColor(getResources().getColor(R.color.white));
                int i = 0;
                Object obj = null;
                for (Map.Entry<String, String> entry : baseMap.entrySet())
                {
                    obj = item.get(entry.getKey());
                    if (obj.toString().equals("0"))
                        obj = "女";
                    if (obj.toString().equals("1"))
                        obj = "男";
                    holder.setText(AppApplication.allpeopleId.get(i++),
                            Html.fromHtml(entry.getValue() + "：<font color=\"#6d849f\">" + (obj == null ? "" : obj.toString()) + "</font>"));
                    holder.setVisibility(AppApplication.allpeopleId.get(i - 1), View.VISIBLE);
                }
            }
        };
        contentlv.setAdapter(adapter);

        pullView.setHeaderPullEnable(false);
        pullView.setOnFooterPullListener(new PullView.OnFooterPullListener()
        {
            @Override
            public void onFooterPull(PullView view)//Footer:页脚
            {
                load();
            }
        });
    }


    private List<Map<String, Object>> getDatas(final boolean dismiss)
    {

//        String orderCulum="fb.name";//排序列名
//        String orderType="desc";//排序大小 比如 desc：从大到小，asc:从小到大。
        //String where="where fb.IdentNum='510075687463399001'";
//        String where="";

        NetworkProxy.provideRetrofit(OkHttpUtil.instance().clone(), AppConfig.Url.BASE_URL)
                .create(DataService.class).Test2(start, limit, orderCulum, orderType, where)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResponse<Object>>()
                {
                    @Override
                    public void onSubscribe(Disposable d) {//Disposable是一个接口,onSubscribe:订阅
                        if (!dismiss) {
                            dialog = DialogUtil.createLoadingDefault(AllPeopleActivity.this, "正在加载");
                            dialog.show();
                        }
                    }

                    @Override
                    public void onNext(HttpResponse<Object> resData)
                    {
                        dialog.dismiss();
                        tv_head_title.setText("总人口");
                        ll_query.setVisibility(View.VISIBLE);
                        if (resData.getCode() == AppConfig.RESPONSE_OK)
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(resData.getData().toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                final List<Map<String, Object>> datas = JsonUtil.jsonToMaps(jsonArray.toString(), Object.class);
                                if (datas.size() == 0)
                                {
                                    PullViewFooter.footerView.setVisibility(View.VISIBLE);
                                    PullViewFooter.footerTextView.setText("没有数据");
                                }
                                if (datas.size() <= 9 && datas.size() >= 1)
                                    pullView.setFooterPullEnable(false);
                                else
                                    pullView.setFooterPullEnable(true);

                                adapter.addDatas(datas);
                                adapter.notifyDataSetChanged();
                                if (once)
                                    setTotalBadge(jsonObject.getInt("count"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {//Throwable:可抛出
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return null;
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.btn_query:
                String nameOrIdentNum = et_hint_cond.getText().toString().trim();

                if (CharSeqUtil.isNullOrEmpty(nameOrIdentNum))
                {
                    PullViewFooter.footerView.setVisibility(View.GONE);
                    PullViewFooter.footerTextView.setText("");
                    where = "";
                }
                else if (CharSeqUtil.isHaveChinese(nameOrIdentNum)){
                    where = "where fb.Name='" + nameOrIdentNum + "'";
                }
                else{
                    where = "where fb.IdentNum='" + nameOrIdentNum + "'";
                }
                adapter.getAdapterDatas().clear();
                adapter.notifyDataSetChanged();
                setTotalBadge(0);
                dismiss = false;
                once = true;
                getDatas(dismiss);
                break;
        }
    }

    private void load()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                start += limit;
                dismiss = true;
                getDatas(dismiss);
                pullView.onFooterLoadFinish();
            }
        });
    }

    private void setTotalBadge(int count)//Badge:徽章
    {
        once = false;
        totalBv.setBadgeText(count + "人");
        totalBv.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        totalBv.setTargetView(et_hint_cond);
    }

}
