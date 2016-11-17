package com.yy.gg.ble.fragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yy.gg.ble.R;

/**
 * Created by ZHLian on 16/11/15.
 */

public class DiyRefreshbar extends RelativeLayout{
    private ImageButton refreshBtn;
    private TextView textView;

    public DiyRefreshbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.diy_refresh_title, this);
        refreshBtn = (ImageButton) findViewById(R.id.diy_titlebar_refresh);
        textView = (TextView)findViewById(R.id.bt_title_textview);
    }

    public ImageButton getRefreshBtn() {
        return refreshBtn;
    }

    public void setRefreshBtn(ImageButton refreshBtn) {
        this.refreshBtn = refreshBtn;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
