package com.xiangsheng.jzfp_sc.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.chuck.model.ITreeNode;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/2.
 */

public class Unit implements Serializable,ITreeNode
{
    @JsonProperty(value="ID")
    private String ID;

    @JsonProperty(value="UnitCode")
    private String unitcode;

    @JsonProperty(value="ParentCode")
    private String parentCode;


    @JsonProperty(value="Name")
    private String name;

    @JsonProperty(value="UnitLevel")
    private String unitLevel;


    @JsonProperty(value="Remark")
    private String remark;

    @JsonProperty(value="DeleteFlag")
    private String deleteFlag;

    @JsonProperty(value="DateUpdate")
    private String dateUpdate;

    @JsonProperty(value="DateCreate")
    private String dateCreate;

    public Unit(){

    }

    public Unit(String ID, String unitcode, String parentCode, String name, String unitLevel, String remark, String deleteFlag, String dateUpdate, String dateCreate) {
        this.ID = ID;
        this.unitcode = unitcode;
        this.parentCode = parentCode;
        this.name = name;
        this.unitLevel = unitLevel;
        this.remark = remark;
        this.deleteFlag = deleteFlag;
        this.dateUpdate = dateUpdate;
        this.dateCreate = dateCreate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    @Override
    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(String unitLevel) {
        this.unitLevel = unitLevel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Override
    public String getCode()
    {
        return unitcode;
    }
    @Override
    public boolean isHaveChildren()
    {
        return false;
    }

    @Override
    public int getLevel()
    {
        return 0;
    }

    @Override
    public boolean isExpand()
    {
        return false;
    }
}
