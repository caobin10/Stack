package com.xiangsheng.jzfp_sc.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.xiangsheng.jzfp_sc.R;
import com.xiangsheng.jzfp_sc.adapter.UnitTreeAdapter;
import com.xiangsheng.jzfp_sc.bean.TicketAuth;
import com.xiangsheng.jzfp_sc.manager.AppApplication;
import com.xiangsheng.jzfp_sc.pojo.Unit;

import org.chuck.adapter.ITreeAllAdapter;
import org.chuck.common.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */

public class UnitTextView extends TextView
{
    private final TicketAuth user;
    private UnitCodeCallBack finishRecorderCallBack;
    private Solve7PopupWindow popupWindow;

    public UnitTextView(final Context context, AttributeSet attrs)
    {
        super(context, attrs);
        AppApplication application = (AppApplication) ((Activity)context).getApplication();
        user = application.getAuth();
        user.getUnit().setRemark(null);

        createUnitTree(context,user.getUnit());

        setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(popupWindow.isShowing())
                {
                    popupWindow.dismiss();
                }else{
                    popupWindow.showAsDropDown(v);
                }
            }
        });
    }

    private void createUnitTree(Context context,Unit unit)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.view_tree, null);
        popupWindow=new Solve7PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ListView contentLv= (ListView) view.findViewById(R.id.lv_content);
        List<Unit> rootUnits=new ArrayList<>();
        rootUnits.add(unit);
        final ITreeAllAdapter<Unit> adapter=new UnitTreeAdapter(context,rootUnits)
        {
            @Override
            public void actionOnNodeBodyClick(ViewHolder holder, Unit item, View convertView, int position)
            {
                finishRecorderCallBack.setCallBack(item);

                popupWindow.dismiss();
            }
        };
        contentLv.setAdapter(adapter);
    }

    public UnitTextView(final Context context)
    {
        this(context, null);
    }

    public  interface UnitCodeCallBack<T>
    {
        void setCallBack(T t);
    }

    public void setFinishCallBack(UnitCodeCallBack callBack) {
        finishRecorderCallBack = callBack;
    }

    public void dismissPop(){
        if(popupWindow!=null){
            popupWindow.dismiss();
        }
    }
}
