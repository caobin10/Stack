package com.xiangsheng.jzfp_sc.Activity.mainfragment.homefragment.allpeople;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangsheng.jzfp_sc.DataService;
import com.xiangsheng.jzfp_sc.R;
import com.xiangsheng.jzfp_sc.adapter.MyAdapter;
import com.xiangsheng.jzfp_sc.adapter.MyDividerItemDecoration;
import com.xiangsheng.jzfp_sc.http.HttpResponse;
import com.xiangsheng.jzfp_sc.manager.AppApplication;
import com.xiangsheng.jzfp_sc.manager.AppConfig;
import com.xiangsheng.jzfp_sc.network.NetworkProxy;
import com.xiangsheng.jzfp_sc.pojo.Unit;
import com.xiangsheng.jzfp_sc.ui.UnitTextView2;
import com.xiangsheng.jzfp_sc.util.DialogUtil;
import com.xiangsheng.jzfp_sc.util.OkHttpUtil;
import com.xiangsheng.jzfp_sc.util.Utils;

import org.chuck.util.CharSeqUtil;
import org.chuck.util.JsonUtil;
import org.chuck.view.BadgeView;
import org.chuck.view.SweetDialog;
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

//import com.xiangsheng.jzfp_sc.util.Utils;

//import com.xiangsheng.jzfp_sc.util.Utils;

/**
 * Created by Administrator on 2018/4/11.
 */
//总人口
public class AllPeopleActivity2 extends Activity implements OnClickListener
{

    private TextView tv_head_title;
    private MyAdapter adapter;
    private RecyclerView mRecyclerView;
    private ImageButton ibtn_back;
    private int start = 0;
    private int limit = AppConfig.LIST_LIMIT;
    private String orderCulum = "fb.name";
    private String orderType = "asc";
    private String where = "";
    private boolean dismiss = false;
    private SweetDialog dialog;
    private Map<String, String> baseMap = null;
    private BadgeView totalBv;
    private EditText et_hint_cond;
    private int lastVisibleItem = 0;
    private int mcount;
    private Button btn_query;
    private UnitTextView2 unitTv;
    private boolean once = true;
    private RecyclerView.LayoutManager layoutmanager;
    private Toolbar toolbar;
    private ObjectAnimator animtor;
    private int mY ;


    private int mToolbarOffset = 0;
    private int mToolbarHeight;
    private LinearLayoutManager LayoutManager;
    private LinearLayout mToolbarContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        init();
    }

    private void init()
    {

        mToolbarContainer = (LinearLayout) findViewById(R.id.toolbarContainer);

        mToolbarHeight = Utils.getToolbarHeight(AllPeopleActivity2.this);

        toolbar = (Toolbar) findViewById(R.id.toolBar);



//        tv_head_title = (TextView) findViewById(R.id.tv_head_title);
//        tv_head_title.setText("总人口");
        et_hint_cond = (EditText) findViewById(R.id.et_hint_cond);
        ibtn_back = (ImageButton) findViewById(R.id.ibtn_back);
        btn_query = (Button) findViewById(R.id.btn_query);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        totalBv = (BadgeView) findViewById(R.id.bv_total);

        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        baseMap = getValue();
//        ibtn_back.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<Map<String, Object>> datas = getDatas(dismiss);
        adapter = new MyAdapter(AllPeopleActivity2.this, datas, R.layout.simple_list_item_3)
        {
            @Override
            public void convert(final RecyclerView.ViewHolder holder, Map<String, Object> item, int position) {
                int i = 0;
                Object obj = null;
                for (Map.Entry<String, String> entry : baseMap.entrySet()) {
                    obj = item.get(entry.getKey());
                    if (obj.toString().equals("0")) obj = "女";
                    if (obj.toString().equals("1")) obj = "男";
                    ((MyViewHolder) holder).setText(AppApplication.allpeopleId2.get(i++),
                            Html.fromHtml(entry.getValue() + "：<font color=\"#6d849f\">" + (obj == null ? "" : obj.toString()) + "</font>"));
                }
            }
        };
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                if ( adapter.getItem(position) == null )
//                    return;
                if ( adapter.getItem(position) == null && adapter.getS().equals("还没联网哦？点击设置网络吧"))
                {
                    //WIRELESS:无线，SETTINGS：设置
//                    Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
//                    startActivity(intent);
//                    return;

                    // 跳转到系统的网络设置界面
                    Intent intent = null;
                    // 先判断当前系统版本
                    if(android.os.Build.VERSION.SDK_INT > 10){  // 3.0以上
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                }else{
                    intent = new Intent();
                    intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                }
                    startActivity(intent);

//                    Toast.makeText(AllPeopleActivity2.this, "去网络", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(AllPeopleActivity2.this, position  + "", Toast.LENGTH_SHORT).show();
            }
        });

        LayoutManager = new LinearLayoutManager(this );
        mRecyclerView.setLayoutManager(LayoutManager);
        mRecyclerView.setAdapter(adapter);
        //Animator:动画师
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //Scroll:滚动，
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);

                if (layoutmanager == null)
                    return;

                //^^
                int lastPosition = -1;

                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                //SCROLL_STATE_IDLE是当屏幕停止滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE  && layoutmanager.getChildCount() > 0 && lastVisibleItem + 1 == adapter.getItemCount())
                {
//                    Toast.makeText(AllPeopleActivity2.this, "0", Toast.LENGTH_SHORT).show();
//                    RecyclerView.LayoutManager layoutmanager = recyclerView.getLayoutManager();

                    if (layoutmanager instanceof LinearLayoutManager)
                    {
                        //通过LayoutManager找到当前显示的最后的item的position
                        lastPosition = ((LinearLayoutManager) layoutmanager).findLastVisibleItemPosition();
                        //Completely:完全
//                        lastPosition = ((GridLayoutManager) layoutmanager).findLastCompletelyVisibleItemPosition();
                        //###
                        if (lastPosition < mcount) {
                            adapter.setLoadEndView(true);
                            adapter.notifyDataSetChanged();
                            loadMore();

                        } else if (recyclerView.getLayoutManager().getItemCount() - 1 == mcount) {
//                            adapter.setLoadEndView(false);
//                            adapter.notifyDataSetChanged();
                            Toast.makeText(AllPeopleActivity2.this, "滑动到底了", Toast.LENGTH_SHORT).show();
                        }
                        //###
//                    } else if (layoutmanager instanceof LinearLayoutManager) {
//                        Toast.makeText(AllPeopleActivity2.this, "数据很少1", Toast.LENGTH_SHORT).show();

//                        mRecyclerView.setLayoutManager(linearLayoutManager);
//                        mRecyclerView.setAdapter(adapter);
//                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutmanager;

//                        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(AllPeopleActivity2.this);
//                        linearLayoutManager.setScrollEnabled(false);
//                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        //获取第一个可见view的位置
//                        int firstItemPosition =linearManager.findFirstVisibleItemPosition();

                        //同理检测是否为顶部itemView时,只需要判断其位置是否为0即可
//                        if (newState == RecyclerView.SCROLL_STATE_IDLE && firstItemPosition == 0)
//                            Toast.makeText(AllPeopleActivity2.this, "3", Toast.LENGTH_SHORT).show();

//                        lastPosition = ((LinearLayoutManager) layoutmanager).findLastVisibleItemPosition();
//                    } else if (layoutmanager instanceof StaggeredGridLayoutManager) {
//                        Toast.makeText(AllPeopleActivity2.this, "数据很少2", Toast.LENGTH_SHORT).show();
//                        //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
//                        //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
//                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutmanager).getSpanCount()];
//                        ((StaggeredGridLayoutManager) layoutmanager).findLastVisibleItemPositions(lastPositions);
//                        lastPosition = findMax(lastPositions);
//                        String s = "";
                    }
                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
//                    int x = recyclerView.getLayoutManager().getItemCount()-1;
//                    if(lastPosition == recyclerView.getLayoutManager().getItemCount()-1)
//                    {
//                        Toast.makeText(AllPeopleActivity2.this, "滑动到底了", Toast.LENGTH_SHORT).show();
//                    }
                }
//                else if(layoutmanager.getChildCount() == mcount ){
//                    Toast.makeText(AllPeopleActivity2.this, "不滑动", Toast.LENGTH_SHORT).show();
//                    mRecyclerView.setNestedScrollingEnabled(false);
//                }
//                if (newState == RecyclerView.SCROLL_STATE_DRAGGING)
//                    Toast.makeText(AllPeopleActivity2.this, "1", Toast.LENGTH_SHORT).show();
//                if (newState == RecyclerView.SCROLL_STATE_SETTLING)
//                    Toast.makeText(AllPeopleActivity2.this, "2", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
//                Toast.makeText(AllPeopleActivity2.this, "变", Toast.LENGTH_SHORT).show();
                layoutmanager = recyclerView.getLayoutManager();

                lastVisibleItem = LayoutManager.findLastVisibleItemPosition();//find：找到，Last：最后，Visible：可见，

                clipToolbarOffset();
                onMoved(mToolbarOffset);//move:移动

                if( ( mToolbarOffset < mToolbarHeight && dy > 0 ) || ( mToolbarOffset > 0 && dy < 0 ) ) {
                    mToolbarOffset += dy;
                }




//                if ( mcount == 0 && layoutmanager.getChildCount() == mcount && dy == 0){
//                    mRecyclerView.setNestedScrollingEnabled(false);
//                }
//                else if (layoutmanager.getChildCount() - 1 == mcount && lastVisibleItem == mcount && dy == 0){
//                    mRecyclerView.setNestedScrollingEnabled(false);
//                }else if (layoutmanager.getChildCount() < mcount || dy > 0 || dy < 0){
//                    mRecyclerView.setNestedScrollingEnabled(true);
//                }
            }
        });


        unitTv = (UnitTextView2) findViewById(R.id.tv_unit);
        unitTv.setVisibility(View.VISIBLE);
        unitTv.setFinishCallBack(new UnitTextView2.UnitCodeCallBack<Unit>() {
            @Override
            public void setCallBack(Unit unit) {
                unitTv.setText(unit.getName().toString());
                start = 0;
                limit = AppConfig.LIST_LIMIT;
                if (CharSeqUtil.isNullOrEmpty(et_hint_cond.getText().toString()))
                    where = "where unit.Name='" + unit.getName().toString() + "'";
                adapter.getAdapterDatas().clear();
                adapter.notifyDataSetChanged();
                getDatas(dismiss);
            }
        });

    }


    private Map<String, String> getValue() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Name", "姓名");
        map.put("IdentNum", "身份证号");
        map.put("Sex", "性别");
        return map;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ibtn_back:
//                finish();
//                break;
            case R.id.btn_query:
                String nameOrIdentNum = et_hint_cond.getText().toString().trim();
                if (CharSeqUtil.isNullOrEmpty(nameOrIdentNum)) {
                    where = "";
                } else if (CharSeqUtil.isHaveChinese(nameOrIdentNum)) {
                    where = "where fb.Name='" + nameOrIdentNum + "'";
                } else {
                    where = "where fb.IdentNum='" + nameOrIdentNum + "'";
                }
                start = 0;
                adapter.getAdapterDatas().clear();
                adapter.notifyDataSetChanged();
                dismiss = false;
                getDatas(dismiss);
                break;
        }
    }

    public List<Map<String, Object>> getDatas(final boolean dismiss)
    {
//        String orderCulum="fb.name";//排序列名
//        String orderType="desc";//排序大小 比如 desc：从大到小，asc:从小到大。
        //String where="where fb.IdentNum='510075687463399001'";
//        String where="";
        NetworkProxy.provideRetrofit(OkHttpUtil.instance().clone(), AppConfig.Url.BASE_URL)
                .create(DataService.class).Test2(start, limit, orderCulum, orderType, where)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResponse<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {//Disposable是一个接口,onSubscribe:订阅
                        if (!dismiss) {
                            dialog = DialogUtil.createLoadingDefault(AllPeopleActivity2.this, "正在加载");
                            dialog.show();
                        }
                    }

                    @Override
                    public void onNext(HttpResponse<Object> resData) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(resData.getData().toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            final List<Map<String, Object>> datas = JsonUtil.jsonToMaps(jsonArray.toString(), Object.class);
                            adapter.setCount(jsonObject.getInt("count"));
                            adapter.addDatas(datas);
                            adapter.notifyDataSetChanged();
                            if (jsonObject.getInt("count") != 0)
                                setTotalBadge(jsonObject.getInt("count"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {//Throwable:可抛出
                        dialog.dismiss();
                        adapter.setLoadEndView(false);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(AllPeopleActivity2.this, "失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        return null;
    }

    private void setTotalBadge(int count)//Badge:徽章
    {
        mcount = count;
        totalBv.setBadgeText(count + "人");
        totalBv.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        totalBv.setTargetView(et_hint_cond);
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private void loadMore() {
        start += limit;
        dismiss = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDatas(dismiss);
            }
        }, 1000);
    }

    private void clipToolbarOffset() {
        if(mToolbarOffset > mToolbarHeight) {
            mToolbarOffset = mToolbarHeight;
        } else if(mToolbarOffset < 0) {
            mToolbarOffset = 0;
        }
    }

    private void onMoved(int mToolbarOffset) {
        mToolbarContainer.setTranslationY(-mToolbarOffset);
    }
}
