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

public class DIYTitleBar extends RelativeLayout{

    private ImageButton diyMenuButton;
    private TextView diyTextView;
    private ImageButton diySetButton;

    public DIYTitleBar(Context context) {
        super(context);
    }


    public DIYTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.diy_titile_bar, this);
        diyMenuButton = (ImageButton)findViewById(R.id.diy_titlebar_menu);

        diyTextView = (TextView)findViewById(R.id.diy_titlebar_text);
        diySetButton = (ImageButton)findViewById(R.id.diy_titlebar_setting);
    }

    public ImageButton getDiyMenuButton() {
        return diyMenuButton;
    }

    public void setDiyMenuButton(ImageButton diyMenuButton) {
        this.diyMenuButton = diyMenuButton;
    }

    public TextView getDiyTextView() {
        return diyTextView;
    }

    public void setDiyTextView(TextView diyTextView) {
        this.diyTextView = diyTextView;
    }

    public ImageButton getDiySetButton() {
        return diySetButton;
    }

    public void setDiySetButton(ImageButton diySetButton) {
        this.diySetButton = diySetButton;
    }
}
