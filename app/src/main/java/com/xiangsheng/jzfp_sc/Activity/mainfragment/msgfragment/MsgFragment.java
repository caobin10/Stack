package com.xiangsheng.jzfp_sc.Activity.mainfragment.msgfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiangsheng.jzfp_sc.R;

/**
 * Created by Administrator on 2018/2/8.
 */

public class MsgFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_msg, container,false);
        TextView textview = (TextView) view.findViewById(R.id.textview);
        textview.setText("消息");
        return view;
    }
}
