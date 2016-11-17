package com.yy.gg.ble.diy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yy.gg.ble.R;

/**
 * Created by ZHLian on 16/11/2.
 */

public class WheelInfo extends LinearLayout {
    private TextView mTemper;
    private TextView mPressure;

    public WheelInfo(Context context) {
        super(context);
    }

    public WheelInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.wheel_info, this);
        mTemper = (TextView) findViewById(R.id.wheel_temper_label);
        mPressure = (TextView) findViewById(R.id.wheel_pressure_label);
    }

    public TextView getmTemper() {
        return mTemper;
    }

    public void setmTemper(TextView mTemper) {
        this.mTemper = mTemper;
    }

    public TextView getmPressure() {
        return mPressure;
    }

    public void setmPressure(TextView mPressure) {
        this.mPressure = mPressure;
    }
}
