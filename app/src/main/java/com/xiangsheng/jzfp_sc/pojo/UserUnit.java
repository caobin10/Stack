package com.xiangsheng.jzfp_sc.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2018/4/4.
 */

public class UserUnit
{
    @JsonProperty(value="ID")
    public String id ;

    @JsonProperty(value="Code")
    public String code ;

    @JsonProperty(value="ParentCode")
    public String parentCode ;

    @JsonProperty(value="Name")
    public String name ;

    @JsonProperty(value="UnitLevel")
    public String unitLevel ;

    @JsonProperty(value="Remark")
    public String remark ;

    @JsonProperty(value="DeleteFlag")
    public String deleteFlag ;

    @JsonProperty(value="DateUpdate")
    public String dateUpdate ;

    @JsonProperty(value="DateCreate")
    public String dateCreate ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

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
}
