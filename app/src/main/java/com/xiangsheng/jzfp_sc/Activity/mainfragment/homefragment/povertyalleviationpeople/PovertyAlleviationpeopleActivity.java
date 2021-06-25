package com.xiangsheng.jzfp_sc.Activity.mainfragment.homefragment.povertyalleviationpeople;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xiangsheng.jzfp_sc.R;
import com.xiangsheng.jzfp_sc.adapter.MyAdapter;
import com.xiangsheng.jzfp_sc.adapter.MyDividerItemDecoration;
import com.xiangsheng.jzfp_sc.manager.AppConfig;
import com.xiangsheng.jzfp_sc.ui.UnitTextView2;

import org.chuck.view.SweetDialog;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/26.
 */
//扶贫人口
public class PovertyAlleviationpeopleActivity extends Activity implements OnClickListener
{
    private TextView tv_head_title;
    private MyAdapter adapter;
    private RecyclerView mRecyclerView;
    private ImageButton ibtn_back;
    private int start = 0;
    private int limit = AppConfig.LIST_LIMIT;
    private String where = "";
    private Map<String, String> baseMap = null;
    private Button btn_query;
    private boolean dismiss = false;
    private SweetDialog dialog;
    private EditText et_hint_cond;
    private UnitTextView2 unitTv;
    private RecyclerView.LayoutManager layoutmanager;
    private String orderCulum = "";
    private String orderType = "";
    private String tableName = "FP_Houser";
    private String flag = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        init();
    }

    private void init() {
        tv_head_title = (TextView) findViewById(R.id.tv_head_title);
        tv_head_title.setText("扶贫人口");
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btn_query = (Button) findViewById(R.id.btn_query);
        ibtn_back = (ImageButton) findViewById(R.id.ibtn_back);
        et_hint_cond = (EditText) findViewById(R.id.et_hint_cond);
        ibtn_back.setOnClickListener(this);
        baseMap = getValue();
        btn_query.setOnClickListener(this);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<Map<String, Object>> datas = getDatas(dismiss);
        adapter = new MyAdapter(PovertyAlleviationpeopleActivity.this, datas, R.layout.simple_list_item_3)
        {
            @Override
            public void convert(final RecyclerView.ViewHolder holder, Map<String, Object> item, int position)
            {

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn_back:
                finish();
                break;
            case R.id.btn_query:
                break;
        }
    }

    private Map<String,String> getValue() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Name", "姓名");
        map.put("Sex", "性别");
        map.put("IdentNum", "身份证号");
        map.put("PoorAttribute","贫困户属性");
        map.put("ShakePovertyFlag","脱贫标识");
        return map;
    }

    public List<Map<String, Object>> getDatas(final boolean dismiss)
    {
//        String orderCulum="fb.name";//排序列名
//        String orderType="desc";//排序大小 比如 desc：从大到小，asc:从小到大。
        //String where="where fb.IdentNum='510075687463399001'";
//        String where="";
//        NetworkProxy.provideRetrofit(OkHttpUtil.instance().clone(), AppConfig.Url.BASE_URL)
//                .create(DataService.class).Test2(limit,start,tableName,where,orderCulum,orderType,flag)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<HttpResponse<Object>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {//Disposable是一个接口,onSubscribe:订阅
//                        if (!dismiss) {
//                            dialog = DialogUtil.createLoadingDefault(PovertyAlleviationpeopleActivity.this, "正在加载");
//                            dialog.show();
//                        }
//                    }
//
//                    @Override
//                    public void onNext(HttpResponse<Object> resData) {
//                        dialog.dismiss();
//                        try {
//                            JSONObject jsonObject = new JSONObject(resData.getData().toString());
//                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//                            final List<Map<String, Object>> datas = JsonUtil.jsonToMaps(jsonArray.toString(), Object.class);
//                            adapter.setCount(jsonObject.getInt("count"));
//                            adapter.addDatas(datas);
//                            adapter.notifyDataSetChanged();
////                            if (jsonObject.getInt("count") != 0)
////                                setTotalBadge(jsonObject.getInt("count"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    @Override
//                    public void onError(Throwable e) {//Throwable:可抛出
//                        dialog.dismiss();
//                        adapter.setLoadEndView(false);
//                        adapter.notifyDataSetChanged();
//                        Toast.makeText(PovertyAlleviationpeopleActivity.this, "失败", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
        return null;
    }
}
