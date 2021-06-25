package com.xiangsheng.jzfp_sc.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiangsheng.jzfp_sc.pojo.Unit;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/27.
 */

public class TicketAuth implements Serializable
{
    @JsonProperty(value="userId")
    public String userId ;
    @JsonProperty(value="accessToken")
    private String accessToken;
    @JsonProperty(value="refreshToken")
    private String refreshToken;
    @JsonProperty(value="createDate")
    private String createDate;
    @JsonProperty(value="expireDate")
    private String expireDate;

    @JsonProperty(value="username")
    private String username;

    @JsonProperty(value="password")
    private String password;

    @JsonProperty(value="Unit")
    private Unit unit;

    @JsonProperty(value="UnitCode")
    private String unitCode;

    public TicketAuth() {
    }

    public TicketAuth(String userId, String accessToken, String refreshToken, String createDate, String expireDate) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.createDate = createDate;
        this.expireDate = expireDate;
    }

    public TicketAuth(String userId, String accessToken, String refreshToken, String createDate, String expireDate, String password) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.password = password;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
}
