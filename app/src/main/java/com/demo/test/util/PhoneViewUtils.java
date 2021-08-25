package com.demo.test.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 17-9-12.
 */
public class PhoneViewUtils {

    public static void showOrHindPhoneView(final Context context, ImageView phonetIv, final String tel){

        if(CharSeqUtil.isTeleMobilePhone(tel)){
            phonetIv.setVisibility(View.VISIBLE);

            phonetIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try{
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ((Activity)context).startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(context,"拨号失败！",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }else{
            phonetIv.setVisibility(View.GONE);

        }
    }
}
