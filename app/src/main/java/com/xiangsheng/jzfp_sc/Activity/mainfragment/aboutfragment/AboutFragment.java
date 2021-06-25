package com.xiangsheng.jzfp_sc.Activity.mainfragment.aboutfragment;

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

public class AboutFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,container,false);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText("关于");
        return view;
    }
}
