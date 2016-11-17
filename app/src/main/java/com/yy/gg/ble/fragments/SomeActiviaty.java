package com.yy.gg.ble.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yy.gg.ble.R;
import com.yy.gg.ble.diy.DiyBackBar;
import com.yy.gg.ble.model.SysSettings;
import com.yy.gg.ble.utils.BLEUtils;

/**
 * Created by ZHLian on 16/11/9.
 */

public class SomeActiviaty extends Activity {
    private int kind;
    private Button confirmBtn;
    private Button cancleBtn;
    private TextView mTextView;

    private ImageButton backButton;
    private TextView title;
    private DiyBackBar diyBackBar;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //following will save to localsetting
            if (kind == 1){//pass
                if ((int)v.getTag()==2) {//cancle button
                    Toast.makeText(getApplicationContext(),"pass changed",Toast.LENGTH_SHORT).show();
                    BLEUtils.saveSPString(getApplicationContext(), SysSettings.SYS_PASS,mTextView.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(),"pass not changed",Toast.LENGTH_SHORT).show();
                }
            }else {
                if ((int)v.getTag()==2) {//cancle button
                    BLEUtils.saveSPString(getApplicationContext(), SysSettings.SYS_NICK_NAME,mTextView.getText().toString());

                    Toast.makeText(getApplicationContext(),"nickname changed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"nickname not changed",Toast.LENGTH_SHORT).show();
                }            }
        }
    };
    public SomeActiviaty() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nick_pass);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("kind");

        cancleBtn = (Button)findViewById(R.id.btn_cancle);
        cancleBtn.setTag(1);
        confirmBtn = (Button)findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(onClickListener);
        confirmBtn.setTag(2);
        cancleBtn.setOnClickListener(onClickListener);
        mTextView = (TextView)findViewById(R.id.pass_nikename);
        backButton = (ImageButton)findViewById(R.id.diy_titlebar_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        diyBackBar = (DiyBackBar)findViewById(R.id.some_back);

        title = (TextView)findViewById(R.id.diy_titlebar_text);

        String tag = bundle.getString("kind");
        if (tag!=null){
            kind = Integer.valueOf(tag);
            if (kind ==1){
                diyBackBar.getTitleTextView().setText("蓝牙昵称设置");
            }else{
                diyBackBar.getTitleTextView().setText("操作密码设置");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (kind == 1){
            mTextView.setText(BLEUtils
                    .loadSettingsString(getApplicationContext(),SysSettings.SYS_NICK_NAME+""));
        }else {
            mTextView.setText(BLEUtils
                    .loadSettingsString(getApplicationContext(),SysSettings.SYS_PASS+""));

        }
    }
}
