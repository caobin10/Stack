package com.xiangsheng.jzfp_sc.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.xiangsheng.jzfp_sc.dao.UnitDao;
import com.xiangsheng.jzfp_sc.factory.DaoFactory;
import com.xiangsheng.jzfp_sc.pojo.Unit;

import org.chuck.adapter.ITreeAllAdapter;
import org.chuck.common.ViewHolder;
import org.chuck.util.ThreadPool;

import java.util.List;

import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by Administrator on 2018/4/2.
 */

public abstract class UnitTreeAdapter extends ITreeAllAdapter<Unit>
{
    protected UnitDao unitDao;
    public UnitTreeAdapter(Context context, List<Unit> rootNodes) {
        super(context, rootNodes);
        addAllNodes();
    }

    public int addChildren(final Unit unit,int position)
    {
        String code=getCode(unit);
        if(getLevel(unit)<4)
        {
            for (Unit node:allNodes)
            {
                if (code.equals(getParentCode(node)))
                {
                    showNodes.add((++position), node);
                    if(isExpand(node))
                    {
                        position=addChildren(node,position);
                    }
                }
            }
        }
        else
        {
            List<Unit> units = unitDao.queryBuilder().where(UnitDao.Properties.UnitCode.like(getCode(unit) + "%"),
                    new WhereCondition.StringCondition("length(code)==12")).build().list();//
            showNodes.addAll((++position),units);
            notifyDataSetChanged();
        }
        return position;
    }

    @Override
    public void actionOnExpandClick(ViewHolder holder, Unit item, View convertView, int position, boolean isExpand) {
        item.setRemark(isExpand ? "false" : "true");
    }

    @Override
    public boolean isHaveChildren(Unit unit) {
        return getLevel(unit)<5?true:false;
    }

    @Override
    public String getCode(Unit unit) {
        return unit.getUnitcode();
    }

    @Override
    public int getLevel(Unit unit) {
        int level = 0;
        switch (unit.getUnitcode().length()){
            case 2:level=1;break;
            case 4:level=2;break;
            case 6:level=3;break;
            case 9:level=4;break;
            case 12:level=5;break;
        }
        return level;
    }

    @Override
    public String getParentCode(Unit unit) {
        return unit.getParentCode();
    }

    @Override
    public String getName(Unit unit) {
        return unit.getName();
    }

    @Override
    public boolean isExpand(Unit unit) {
        return Boolean.valueOf(unit.getRemark());
    }

    public void addAllNodes(){
        unitDao= DaoFactory.getUnitDao(context);

        final StringBuilder builder=new StringBuilder();
        for(int i=0;i<rootNodes.size();i++)
        {
            if(i==0)
            {
                builder.append("code LIKE '"+getCode(rootNodes.get(i))+"%'");
            }
            else
            {
                builder.append(" OR code LIKE '"+getCode(rootNodes.get(i))+"%'");
            }
        }
        ThreadPool.getExecutor().execute(new Runnable()
        {
            @Override
            public void run()
            {
                List<Unit> units = unitDao.queryBuilder().where(new WhereCondition.StringCondition("length(code)<12"),
                        new WhereCondition.StringCondition(builder.toString())).orderAsc(UnitDao.Properties.UnitCode).build().list();
                Log.i("units.size()",units.size()+"");
                addTreeNodes(units);
            }
        });
    }
}
