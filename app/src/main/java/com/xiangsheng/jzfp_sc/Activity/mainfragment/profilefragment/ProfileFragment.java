package com.xiangsheng.jzfp_sc.Activity.mainfragment.profilefragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.xiangsheng.jzfp_sc.Activity.LoginActivity;
import com.xiangsheng.jzfp_sc.R;
import com.xiangsheng.jzfp_sc.util.DialogUtil;

import org.chuck.view.SweetDialog;


/**
 * Created by Administrator on 2018/2/8.
 */

public class ProfileFragment extends Fragment {
    private SweetDialog dialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Drawable ic_arrow_right = (getResources().getDrawable(R.drawable.ic_arrow_right));
        Drawable ic_info = getResources().getDrawable(R.drawable.icon_customer_service);
        Drawable ic_update = getResources().getDrawable(R.drawable.ic_update);
        Drawable ic_settings = getResources().getDrawable(R.drawable.icon_settings);
        ic_arrow_right.setBounds(0, 0, 60, 60);
        ic_info.setBounds(0, 0, 70, 70);
        ic_update.setBounds(0, 0, 70, 70);
        ic_settings.setBounds(0, 0, 70, 70);

        ((TextView) view.findViewById(R.id.tv_op3)).setCompoundDrawables(ic_info, null, ic_arrow_right, null);
        ((TextView) view.findViewById(R.id.tv_update_check)).setCompoundDrawables(ic_update, null, ic_arrow_right, null);
        ((TextView) view.findViewById(R.id.tv_setting)).setCompoundDrawables(ic_settings, null, ic_arrow_right, null);

        Button exitBtn = (Button) view.findViewById(R.id.btn_exit);
        exitBtn.setOnClickListener(clickListener);
        return view;
    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_exit:
                    dialog = DialogUtil.ShowExitAccountDialog(getActivity(), getResources().getString(R.string.confirm_exit_account), new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent mainIntemt = new Intent(getActivity(), LoginActivity.class);
                            getActivity().startActivity(mainIntemt);
                            getActivity().overridePendingTransition(R.anim.base_slide_top_out,0);
                        }
                    }, null);
                    dialog.show();
                    break;
            }
        }
    };
}
