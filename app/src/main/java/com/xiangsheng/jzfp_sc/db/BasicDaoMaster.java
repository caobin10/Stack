package com.xiangsheng.jzfp_sc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xiangsheng.jzfp_sc.dao.UnitDao;

import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

/**
 * Created by Administrator on 2018/4/2.
 */

public class BasicDaoMaster extends AbstractDaoMaster
{
    public static final int SCHEMA_VERSION = 1000;
    public BasicDaoMaster(SQLiteDatabase db)
    {
        super(db,SCHEMA_VERSION);
        registerDaoClass(UnitDao.class);
    }

    @Override
    public BasicDaoSession newSession()
    {
        return new BasicDaoSession(db, IdentityScopeType.Session,daoConfigMap);
    }

    @Override
    public BasicDaoSession newSession(IdentityScopeType type)
    {
        return new BasicDaoSession(db,type,daoConfigMap);
    }

    public static void createTables(SQLiteDatabase db, boolean ifNotExists){}
    public static void dropTables(SQLiteDatabase db, boolean ifExists){}

    public static abstract class OpenHelper extends SQLiteOpenHelper {
        private static final int VERSION = 1;
        private static final String DBNAME="basic.db";
        public OpenHelper(Context context, SQLiteDatabase.CursorFactory factory) {
            super(context, DBNAME, factory, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("onCreate", "onCreate");
            createTables(db, false);
        }
    }

    public static class DevOpenHelper extends OpenHelper{
        public DevOpenHelper(Context context, SQLiteDatabase.CursorFactory factory) {
            super(context, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("onUpgrade", "onUpgrade");
            dropTables(db, true);
            onCreate(db);
        }
    }

}
