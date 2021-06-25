package com.xiangsheng.jzfp_sc.Activity.mainfragment.homefragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiangsheng.jzfp_sc.Activity.mainfragment.homefragment.allpeople.AllPeopleActivity2;
import com.xiangsheng.jzfp_sc.Activity.mainfragment.homefragment.baseservice.BaseServiceActivity;
import com.xiangsheng.jzfp_sc.Activity.mainfragment.homefragment.povertyalleviationpeople.PovertyAlleviationpeopleActivity;
import com.xiangsheng.jzfp_sc.R;
import com.xiangsheng.jzfp_sc.ui.MyGridAdapter;
import com.xiangsheng.jzfp_sc.ui.PictureAdapter;

import org.chuck.view.SweetDialog;

import java.util.ArrayList;
import java.util.List;

//import org.chuck.view.SweetDialog;

//import android.support.annotation.RequiresApi;

/**
 * Created by Administrator on 2018/2/8.
 */

public class HomeFragment extends Fragment implements OnPageChangeListener {
    private List<Integer> imgIds;
    private List<String> textIds;
    private MyGridAdapter adapter;
    private ViewPager mViewPaper;
    private LinearLayout mPointContainer;
    private TextView title;
//    private PictureAdapter pictureAdapter;
    //    存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.ic_jlfp,
            R.drawable.ic_jlfp,
            R.drawable.ic_jlfp,
            R.drawable.ic_jlfp,
            R.drawable.ic_jlfp};
    private String[] imageUrls = new String[]{
            "00.png",
            "01.png",
            "02.png",
            "03.png",
            "04.png"
    };

    private GridView gridView;
    private String[] titles = new String[]{"基础数据", "基础服务", "基础统计", "业务例子"};
    private Integer[] images = {R.drawable.ease_chat_image_selector,
            R.drawable.ease_chat_image_selector, R.drawable.ease_chat_image_selector,
            R.drawable.ease_chat_image_selector,};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imgIds = new ArrayList<>();
        textIds = new ArrayList<>();

        //^^^^
        gridView = (GridView) view.findViewById(R.id.gv_main);

        final PictureAdapter pictureAdapter = new PictureAdapter(titles, images, getActivity());

        gridView.setAdapter(pictureAdapter);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));//setSelector颜色为透明色
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                if (pictureAdapter.getItem(position).equals("基础数据"))
                    showbasedataDialog(pictureAdapter.getItem(position).toString());
                if (pictureAdapter.getItem(position).equals("基础服务")){
                    Intent intent = new Intent(getActivity(), BaseServiceActivity.class);
                    startActivity(intent);
                }
            }
        });
        initPager(view);
        return view;
    }

    private void setHomeMod() {
        imgIds.add(R.drawable.icon_base);
        textIds.add(getResources().getString(R.string.base_data));

        imgIds.add(R.drawable.icon_base);
        textIds.add(getResources().getString(R.string.base_service));

        imgIds.add(R.drawable.icon_base);
        textIds.add(getResources().getString(R.string.base_statistics));

        imgIds.add(R.drawable.icon_base);
        textIds.add(getResources().getString(R.string.business_example));
    }

    private void initPager(View view) {
        mViewPaper = (ViewPager) view.findViewById(R.id.vp);
        mPointContainer = (LinearLayout) view.findViewById(R.id.item_home_picture_container_indicator);
        title = (TextView) view.findViewById(R.id.title);
        mViewPaper.setAdapter(new HomePictureAdapter());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //轮播adapter
    class HomePictureAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (imageIds != null)
                return Integer.MAX_VALUE;
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % imageIds.length;
            ImageView iv = new ImageView(getActivity());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //给iv设置数据
            iv.setImageResource(imageIds[position]);
            //设置网络图片
            container.addView(iv);
            return iv;
        }
    }
    private void showbasedataDialog(String title) {
        String[] titles = new String[]{"总人口", "扶贫人口","残联人口"};
        Integer[] images = {R.drawable.ease_chat_image_selector, R.drawable.ease_chat_image_selector,R.drawable.ease_chat_image_selector,};
            showdialog(title, titles, images);
    }

    private void showdialog(String title, String[] titles, Integer[] images)
    {
        final SweetDialog dialog = new SweetDialog(getActivity());
        dialog.setContentView(R.layout.dialog);
        TextView titleTv = dialog.getView(R.id.text);
        titleTv.setText(title);
        GridView gvbasedata = dialog.getView(R.id.gv_basedata);
        gvbasedata.setSelector(new ColorDrawable(Color.TRANSPARENT));
        final PictureAdapter pictureAdapterdialog = new PictureAdapter(titles, images, getActivity());
        gvbasedata.setAdapter(pictureAdapterdialog);
        gvbasedata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (pictureAdapterdialog.getItem(position).equals("总人口"))
                {
                    dialog.dismiss();
                    Intent intent = new Intent(getActivity(),AllPeopleActivity2.class);
                    startActivity(intent);
                }
                if (pictureAdapterdialog.getItem(position).equals("扶贫人口")){
                    dialog.dismiss();
                    Intent intent = new Intent(getActivity(), PovertyAlleviationpeopleActivity.class);
                    startActivity(intent);
                }
            }
        });
        dialog.show();
    }
}
