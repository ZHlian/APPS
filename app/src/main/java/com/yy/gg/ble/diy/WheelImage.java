package com.yy.gg.ble.diy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yy.gg.ble.R;

/**
 * Created by ZHLian on 16/11/2.
 */

public class WheelImage extends LinearLayout {
    private ImageView mImageView;
    public WheelImage(Context context) {
        super(context);
    }

    public WheelImage(Context context, AttributeSet attributeSet){
        super(context);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.wheel_self, this);
        mImageView=(ImageView) findViewById(R.id.wheel_pic);
    }

    public ImageView getmImageView() {
        return mImageView;
    }

    public void setmImageView(ImageView mImageView) {
        this.mImageView = mImageView;
    }
}
