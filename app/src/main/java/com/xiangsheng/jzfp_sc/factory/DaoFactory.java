package com.xiangsheng.jzfp_sc.factory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.xiangsheng.jzfp_sc.dao.UnitDao;
import com.xiangsheng.jzfp_sc.db.BasicDaoMaster;
import com.xiangsheng.jzfp_sc.db.BasicDaoSession;

/**
 * Created by Administrator on 2018/4/2.
 */

public class DaoFactory
{
    public static BasicDaoMaster basicDaoMaster;
    public static BasicDaoSession basicDaoSession;

    public static BasicDaoMaster getBasicDaoMaster(Context context)
    {
        if (basicDaoMaster == null){
            BasicDaoMaster.DevOpenHelper helper = new BasicDaoMaster.DevOpenHelper(context,null);
            SQLiteDatabase db = helper.getWritableDatabase();
            basicDaoMaster = new BasicDaoMaster(db);
        }
        return basicDaoMaster;
    }

    public static BasicDaoSession getBasicDaoSession(Context context){
        if(basicDaoSession == null){
            basicDaoSession = getBasicDaoMaster(context).newSession();
        }
        return basicDaoSession;
    }

    public static UnitDao getUnitDao(Context context){
        return  getBasicDaoSession(context).getUnitDao();
    }

}
