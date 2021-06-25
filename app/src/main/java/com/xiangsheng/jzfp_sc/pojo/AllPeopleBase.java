package com.xiangsheng.jzfp_sc.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Administrator on 2018/3/6.
 */

public class AllPeopleBase
{
    @JsonProperty(value="Id")//Property:属性
    private String id;//唯一ID
    @JsonProperty(value="Name")
    private String name;
    @JsonProperty(value="Sex")
    private int sex;
    @JsonProperty(value="Identnum")
    private String identnum;
    @JsonProperty(value="Idstatus")
    private int idstatus;//二代身份证状态
    @JsonProperty(value="Nation")
    private int nation;//民族
    @JsonProperty(value="Untitid")
    private String unitid;
    @JsonProperty(value="DeleteFlag")
    private boolean deleteFlag;//删除标识
    @JsonProperty(value="Beathflag")
    private boolean beathflag;//死亡标识
    @JsonProperty(value="Dateupdate")
    private Date dateupdate;
    @JsonProperty(value = "Datetime")
    private Date datetime;
    @JsonProperty(value = "Msg")
    private String msg;
    @JsonProperty(value = "Success")
    private boolean success;
    @JsonProperty(value = "Count")
    private int count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIdentnum() {
        return identnum;
    }

    public void setIdentnum(String identnum) {
        this.identnum = identnum;
    }

    public int getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(int idstatus) {
        this.idstatus = idstatus;
    }

    public int getNation() {
        return nation;
    }

    public void setNation(int nation) {
        this.nation = nation;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public boolean isBeathflag() {
        return beathflag;
    }

    public void setBeathflag(boolean beathflag) {
        this.beathflag = beathflag;
    }

    public Date getDateupdate() {
        return dateupdate;
    }

    public void setDateupdate(Date dateupdate) {
        this.dateupdate = dateupdate;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
