package com.yy.gg.ble.model;


import java.io.Serializable;

/**
 * Created by ZHLian on 16/10/27.
 */

public class SysSettings implements Serializable{
    //constant name

    public static final String SYS_PASS = "password";
    public static final String SYS_CARTYPE = "cartype";
    public static final String SYS_LANGUAGE = "language";
    public static final String SYS_TMEPUNIT = "tunit";
    public static final String SYS_RING = "isring";
    public static final String SYS_SHAKE = "ishake";
    public static final String SYS_MAX_PRESSURE = "maxpre";
    public static final String SYS_MIN_PRESSURE = "minpre";
    public static final String SYS_MAX_TEMPER = "maxtmp";
    public static final String SYS_PRESSURE_UNIT = "pressureunit";
    public static final String SYS_OBD = "obd";
    public static final String SYS_NICK_NAME = "nickname";
    public static final String SYS_BLE_MAC="macadd";
    public static final int SYS_TEMPER_UNIT_HUA = 1;//华氏
    public static final int SYS_TEMPER_UNIT_SHE = 2;//摄氏

    private String mPass;//密码
    private String mCarTypeName;//车型名称
    private int mLanguage;//语言
    private int mTemperUnit;//温度单位
    private boolean mRing;//是否有铃声
    private boolean mShack;//震动
    private boolean mOBD;
    private float minAirPressure;//最低气压
    private float maxAirPressure;//最高气压
    private float maxTemper;//最高温度

    private int mAirPressUnit;

    public static volatile SysSettings sysSettings;

    public static SysSettings getInstance() {
        if (sysSettings == null){
            synchronized (SysSettings.class){
                if (sysSettings == null){
                    sysSettings = new SysSettings();
                }
            }
        }
        return sysSettings;
    }

    public int getmLanguage() {
        return mLanguage;
    }

    public void setmLanguage(int mLanguage) {
        this.mLanguage = mLanguage;
    }

    public int getmTemperUnit() {
        return mTemperUnit;
    }

    public void setmTemperUnit(int mTemperUnit) {
        this.mTemperUnit = mTemperUnit;
    }

    public String getmPass() {
        return mPass;
    }

    public void setmPass(String mPass) {
        this.mPass = mPass;
    }

    public String getmCarTypeName() {
        return mCarTypeName;
    }

    public void setmCarTypeName(String mCarTypeName) {
        this.mCarTypeName = mCarTypeName;
    }

    public float getMinAirPressure() {
        return minAirPressure;
    }

    public void setMinAirPressure(float minAirPressure) {
        this.minAirPressure = minAirPressure;
    }

    public float getMaxAirPressure() {
        return maxAirPressure;
    }

    public void setMaxAirPressure(float maxAirPressure) {
        this.maxAirPressure = maxAirPressure;
    }

    public float getMaxTemper() {
        return maxTemper;
    }

    public void setMaxTemper(float maxTemper) {
        this.maxTemper = maxTemper;
    }

    public int getmAirPressUnit() {
        return mAirPressUnit;
    }

    public void setmAirPressUnit(int mAirPressUnit) {
        this.mAirPressUnit = mAirPressUnit;
    }

    public boolean ismRing() {
        return mRing;
    }

    public void setmRing(boolean mRing) {
        this.mRing = mRing;
    }

    public boolean ismShack() {
        return mShack;
    }

    public void setmShack(boolean mShack) {
        this.mShack = mShack;
    }

    public boolean ismOBD() {
        return mOBD;
    }

    public void setmOBD(boolean mOBD) {
        this.mOBD = mOBD;
    }
}
