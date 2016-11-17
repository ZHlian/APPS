package com.yy.gg.ble.model;

/**
 * Created by ZHLian on 16/10/27.
 */

public class CarType {
    private String mType;
    private int[] wheels;
    private int axleType;

    public static final int ONE_FORUTH_TYPE = 0;
    public static final int ONE_THREE_FORUTH_TYPE = 1;
    public static final int ONE_TO_FORUTH_TYPE = 2;


    public int getAxleType() {
        return axleType;
    }

    public void setAxleType(int axleType) {
        this.axleType = axleType;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public int[] getWheels() {
        return wheels;
    }

    public void setWheels(int[] wheels) {
        this.wheels = wheels;
    }



}
