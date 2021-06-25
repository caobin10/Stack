package com.xiangsheng.jzfp_sc.db;

import android.database.sqlite.SQLiteDatabase;

import com.xiangsheng.jzfp_sc.dao.UnitDao;
import com.xiangsheng.jzfp_sc.pojo.Unit;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by Administrator on 2018/4/2.
 */

public class BasicDaoSession extends AbstractDaoSession
{
    private UnitDao unitDao;
    private DaoConfig unitConfig;
    public BasicDaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);
        try {
            unitConfig=daoConfigMap.get(UnitDao.class).clone();
        }catch (Exception e){
            e.printStackTrace();
        }
        unitDao=new UnitDao(unitConfig,this);
        registerDao(Unit.class, unitDao);
    }

    public UnitDao getUnitDao(){
        return unitDao;
    }

    public void clear() {
        unitConfig.getIdentityScope().clear();
    }
}
