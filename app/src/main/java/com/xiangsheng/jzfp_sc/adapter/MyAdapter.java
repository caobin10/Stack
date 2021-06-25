package com.xiangsheng.jzfp_sc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiangsheng.jzfp_sc.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/11.
 */

public abstract class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context mContext;
    private List<Map<String, Object>> datas;
    private OnItemClickListener mItemClickListener;
    private int mresId;
    private int mfootview;
    private LayoutInflater minflater;
    private int normalType = 0;//第一种viewType,正常的item
    private int footType = 1;//第二种VIewType,底部的提示View
    private boolean mLoadEnd = true ;
    private int mCount;
    private String s = "";

    public MyAdapter(Context context, List<Map<String, Object>> datas, int resId) {
        super();
        this.mContext = context;
        this.minflater = LayoutInflater.from(context);
        this.datas = datas;
        this.mresId = resId;
    }

    //item的回调接口
    public interface OnItemClickListener
    {
        void onItemClick(View view, int Position);
    }

    //定义一个设置点击监听器的方法
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == normalType)
            return new MyViewHolder(minflater.inflate(mresId, parent, false));
        else
            return new FootHolder(minflater.inflate(R.layout.footview,parent,false));
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {

        if (holder instanceof MyViewHolder && getItem(position) != null){
            convert(holder, getItem(position),position);
        }else if (holder instanceof FootHolder){
//            ((FootHolder) holder).tips.setText("点击加载数据");
            if ( mLoadEnd == false ){
                ((FootHolder) holder).footerProgressBar.setVisibility(View.GONE);
                ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
                ((FootHolder) holder).tips.setText("还没联网哦？点击设置网络吧");
                this.s = "还没联网哦？点击设置网络吧";
            }
            else if ( mCount <= position && getItem(position) == null ) {
                ((FootHolder) holder).footerProgressBar.setVisibility(View.GONE);
                ((FootHolder) holder).notnetworking.setVisibility(View.GONE);
                ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
                ((FootHolder) holder).tips.setText("没有更多数据");
            }
            else{
                ((FootHolder) holder).footerProgressBar.setVisibility(View.VISIBLE);
                ((FootHolder) holder).notnetworking.setVisibility(View.GONE);
                ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
                ((FootHolder) holder).tips.setText("正在加载中...");
            }
        }
        //如果设置了回调，则设置点击事件
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }
    public abstract void convert(RecyclerView.ViewHolder holder, Map<String, Object> item ,int position);

    public Map<String, Object> getItem(int position)
    {
        if ( position < getItemCount() - 1 )
            return datas == null ? null : datas.get(position);
        return null;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == getItemCount() - 1)
            return footType;
        else
            return normalType;
    }


    @Override
    public int getItemCount()
    {
        return datas == null ? 0 : datas.size() + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private SparseArray<View> mViews;
        private View mitemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.mitemView = itemView;
            mViews = new SparseArray<>();
        }
        public MyViewHolder setText(int id, CharSequence text) {
            TextView textView = getView(id);
            textView.setText(text);
            return this;
        }

        public <T extends View> T getView(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = mitemView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }
    }

    public void addDatas(List<Map<String, Object>> items) {

        if (datas == null) {
            datas = new ArrayList<>();
        }
        if (items != null) {
            datas.addAll(items);
        }

    }

    public class FootHolder extends RecyclerView.ViewHolder
    {

        public ProgressBar footerProgressBar;
        public ImageView notnetworking;
        public TextView tips;
        public FootHolder(View itemView)
        {
            super(itemView);
            footerProgressBar = (ProgressBar) itemView.findViewById(R.id.footerProgressBar);
            notnetworking = (ImageView) itemView.findViewById(R.id.iv_notnetworking);
            tips = (TextView) itemView.findViewById(R.id.tips);//tips:提示
        }
    }

    public List<Map<String, Object>> getAdapterDatas(){
        return datas;
    }

    public void setLoadEndView( boolean LoadEnd ){
        this.mLoadEnd = LoadEnd;
    }

    public void setCount(int count){
        this.mCount = count;
    }
    public int getCount(){
        return mCount;
    }
    public String getS(){
        return s;
    }
}
