package com.yy.gg.ble.diy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.yy.gg.ble.R;

/**
 * Created by ZHLian on 16/11/6.
 */

public class WheelLeft extends LinearLayout {

    public WheelLeft(Context context) {
        super(context);
    }

    public WheelLeft(Context context, AttributeSet attributeSet){
        super(context);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.wheel_left, this);
    }
}
