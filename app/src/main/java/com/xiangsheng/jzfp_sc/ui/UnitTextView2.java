package com.xiangsheng.jzfp_sc.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xiangsheng.jzfp_sc.R;
import com.xiangsheng.jzfp_sc.adapter.UnitTreeAdapter;
import com.xiangsheng.jzfp_sc.bean.TicketAuth;
import com.xiangsheng.jzfp_sc.manager.AppApplication;
import com.xiangsheng.jzfp_sc.pojo.Unit;

import org.chuck.adapter.ITreeAllAdapter;
import org.chuck.common.ViewHolder;
import org.chuck.view.SweetDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class UnitTextView2 extends TextView
{
    private final TicketAuth user;
    private UnitTextView2.UnitCodeCallBack finishRecorderCallBack;
//    private Solve7PopupWindow popupWindow;
    private SweetDialog dialog;

    public UnitTextView2(final Context context, AttributeSet attrs)
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

                if(dialog.isShowing())
                {
                    dialog.dismiss();
                }else{
//                    popupWindow.showAsDropDown(v);
                    dialog.show();
                }
            }
        });
    }

    //^^
//    public UnitTextView2(Context context, AttributeSet attrs, int defStyle)
//    {
//        super(context, attrs, defStyle);
//    }
    //^^


    private void createUnitTree(Context context,Unit unit)
    {
        dialog = new SweetDialog(context);
        dialog.setContentView(R.layout.view_tree2);
        ListView contentLv= (ListView) dialog.findViewById(R.id.lv_content);
        List<Unit> rootUnits=new ArrayList<>();
        rootUnits.add(unit);

//        View view= LayoutInflater.from(context).inflate(R.layout.view_tree, null);
//        popupWindow=new Solve7PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        ListView contentLv= (ListView) view.findViewById(R.id.lv_content);
//        List<Unit> rootUnits=new ArrayList<>();
//        rootUnits.add(unit);
        final ITreeAllAdapter<Unit> adapter=new UnitTreeAdapter(context,rootUnits)
        {
            @Override
            public void actionOnNodeBodyClick(ViewHolder holder, Unit item, View convertView, int position)
            {
                finishRecorderCallBack.setCallBack(item);

                dialog.dismiss();
            }
        };
        contentLv.setAdapter(adapter);
//        dialog.show();
    }

    public UnitTextView2(final Context context)
    {
        this(context, null);
    }

    public  interface UnitCodeCallBack<T>
    {
        void setCallBack(T t);
    }

    public void setFinishCallBack(UnitTextView2.UnitCodeCallBack callBack)
    {
        finishRecorderCallBack = callBack;
    }

//    public void dismissPop(){
//        if(popupWindow!=null){
//            popupWindow.dismiss();
//        }
//    }
}
