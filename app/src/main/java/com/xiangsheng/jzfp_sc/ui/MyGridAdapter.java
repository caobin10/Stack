package com.xiangsheng.jzfp_sc.ui;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiangsheng.jzfp_sc.R;

import java.util.List;

/**
 * Created by Administrator on 2018/2/9.
 */

public class MyGridAdapter extends BaseAdapter
{
    private Context mContext;
    private List<Integer> img_list ;
    private List<String> text_list ;
    private List<Integer> img_list1;
    private List<String> text_list1;
    public MyGridAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }
    public MyGridAdapter(Context mContext,List<Integer> img_list,List<String> text_list)
    {
        super();
        this.mContext = mContext;
        this.img_list = img_list;
        this.text_list = text_list;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return img_list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return text_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
        }
        RelativeLayout RL_background=BaseViewHolder.get(convertView, R.id.rl_item);
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        RelativeLayout llll= BaseViewHolder.get(convertView,R.id.lllll);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);

        switch (position)
        {
            case 0:llll.setBackgroundResource(R.drawable.radius1);break;
            case 1:llll.setBackgroundResource(R.drawable.radius2);break;
            case 2:llll.setBackgroundResource(R.drawable.radius3);break;
            case 3:llll.setBackgroundResource(R.drawable.radius4);break;
//            case 4:llll.setBackgroundResource(R.drawable.radius5);break;
//            case 5:llll.setBackgroundResource(R.drawable.radius6);break;
//            case 6:llll.setBackgroundResource(R.drawable.radius7);break;
//            case 7:llll.setBackgroundResource(R.drawable.radius8);break;
//            case 8:llll.setBackgroundResource(R.drawable.radius1);break;
//            case 9:llll.setBackgroundResource(R.drawable.radius5);break;
//            case 10:llll.setBackgroundResource(R.drawable.radius2);break;
        }

        iv.setBackgroundResource(img_list.get(position));
        tv.setText(text_list.get(position));

        return convertView;
    }


    public Resources getResources() {
        return null;
    }
}
