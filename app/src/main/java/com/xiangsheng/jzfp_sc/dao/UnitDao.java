package com.xiangsheng.jzfp_sc.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.xiangsheng.jzfp_sc.pojo.Unit;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by Administrator on 2018/4/2.
 */

public class UnitDao extends AbstractDao<Unit,String>
{
    public static final String TABLENAME="Unit";
    public UnitDao(DaoConfig config){
        super(config);
    }
    public UnitDao(DaoConfig config, AbstractDaoSession daoSession){
        super(config,daoSession);
    }
    public static class Properties{
        public final static Property ID = new Property(0,String.class,"ID",false,"ID");
        public final static Property UnitCode = new Property(1,String.class,"code",false,"code");
        public final static Property ParentCode = new Property(2,String.class,"parentCode",false,"parentCode");
        public final static Property Name = new Property(3,String.class,"name",false,"name");
        public final static Property UnitLevel = new Property(4,int.class,"unitLevel",false,"unitLevel");
        public final static Property Remark = new Property(5,String.class,"remark",false,"remark");
        public final static Property DeleteFlag = new Property(6,boolean.class,"deleteFlag",false,"deleteFlag");
        public final static Property DateUpdate = new Property(7,Date.class,"dateUpdate",false,"dateUpdate");
        public final static Property DateCreate = new Property(8,Date.class,"dateCreate",false,"dateCreate");

    }
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\""+TABLENAME+"\"";
        db.execSQL(sql);
    }
    @Override
    protected Unit readEntity(Cursor cursor, int offset)
    {
        return new Unit(cursor.isNull(offset)?null:cursor.getString(offset),
                cursor.getString(offset+1),
                cursor.getString(offset+2),
                cursor.getString(offset+3),
                cursor.getString(offset+4),
                cursor.getString(offset+5),
                cursor.getString(offset+6),
                cursor.getString(offset+7),
                cursor.getString(offset+8)
        );
    }

    @Override
    protected String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset) ? null : cursor.getString(offset);
    }

    @Override
    protected void readEntity(Cursor cursor, Unit entity, int offset) {
        entity.setUnitcode(cursor.isNull(offset) ? null : cursor.getString(offset));
        entity.setParentCode(cursor.getString(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setUnitLevel(cursor.getString(offset + 3));
        entity.setRemark(cursor.getString(offset + 4));
        entity.setDeleteFlag(cursor.getString(offset + 5));
        entity.setDateUpdate(cursor.getString(offset + 6));
        entity.setDateCreate(cursor.getString(offset + 7));
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, Unit entity) {
        stmt.clearBindings();
        String unitCode = entity.getUnitcode();
        if (unitCode != null) {
            stmt.bindString(1, unitCode);
        }
        stmt.bindString(2, entity.getParentCode());
        stmt.bindString(3, entity.getName());
        stmt.bindString(4, entity.getUnitLevel());
        stmt.bindString(5, entity.getRemark());
        stmt.bindString(6, entity.getDeleteFlag());
        stmt.bindString(7, entity.getDateUpdate());
        stmt.bindString(8, entity.getDateCreate());
    }

    @Override
    protected String updateKeyAfterInsert(Unit entity, long rowId) {
        return entity.getUnitcode();
    }

    @Override
    protected String getKey(Unit entity) {
        return entity==null?null:entity.getUnitcode();
    }

    @Override
    protected boolean isEntityUpdateable() {
        return false;
    }


    public List<Unit> getChildren(String unitCode){
        List<Unit> datas = null;
        if(unitCode.length()<12){
            datas=this.queryBuilder().where(Properties.ParentCode.eq(unitCode)).orderAsc(Properties.UnitCode).build().list();
        }
        return datas;
    }
}
