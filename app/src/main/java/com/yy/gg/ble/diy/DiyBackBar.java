package com.yy.gg.ble.diy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yy.gg.ble.R;

/**
 * Created by ZHLian on 16/11/14.
 */

public class DiyBackBar extends RelativeLayout {
    private ImageButton backIMB;
    private TextView titleTextView;
    public DiyBackBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.appset_titlbar, this);
        backIMB = (ImageButton)findViewById(R.id.diy_titlebar_back);
        titleTextView = (TextView)findViewById(R.id.diy_titlebar_title);
    }

    public ImageButton getBackIMB() {
        return backIMB;
    }

    public void setBackIMB(ImageButton backIMB) {
        this.backIMB = backIMB;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(TextView titleTextView) {
        this.titleTextView = titleTextView;
    }
}
