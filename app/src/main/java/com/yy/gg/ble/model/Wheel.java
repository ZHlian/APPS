package com.yy.gg.ble.model;

import java.io.Serializable;

/**
 * Created by ZHLian on 16/10/27.
 */

public class Wheel implements Serializable{
    private int mPosition;//左右位置 从左往右,上到下 也表示数组中的位置
    private int mState;//状态 -1过低,0正常,1过高,
    private float mAirPress;//压力
    private float mTemper;//温度


    public Wheel(){
        mPosition = -1;
        mState = 2;
        mAirPress = 0;
        mTemper = 0;

    }

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public int getmState() {
        return mState;
    }

    public void setmState(int mState) {
        this.mState = mState;
    }

    public float getmAirPress() {
        return mAirPress;
    }

    public void setmAirPress(float mAirPress) {
        this.mAirPress = mAirPress;
    }

    public float getmTemper() {
        return mTemper;
    }

    public void setmTemper(float mTemper) {
        this.mTemper = mTemper;
    }
}
