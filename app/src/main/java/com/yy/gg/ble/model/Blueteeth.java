package com.yy.gg.ble.model;

/**
 * Created by ZHLian on 16/10/27.
 */

public class Blueteeth {
    private int mNumber;//序号
    private String mName;//地址
    private String mNickName;//名称
    private int mState;//0 未连接 1 连接

    public int getmNumber() {
        return mNumber;
    }

    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmNickName() {
        return mNickName;
    }

    public void setmNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public int getmState() {
        return mState;
    }

    public void setmState(int mState) {
        this.mState = mState;
    }
}
