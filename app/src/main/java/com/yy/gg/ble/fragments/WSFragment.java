package com.yy.gg.ble.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.yy.gg.ble.R;
import com.yy.gg.ble.diy.DIYTitleBar;
import com.yy.gg.ble.diy.DiySeekbar;
import com.yy.gg.ble.model.CarType;
import com.yy.gg.ble.model.SysSettings;
import com.yy.gg.ble.model.Wheel;
import com.yy.gg.ble.utils.BLEUtils;

import java.util.ArrayList;

/**
 * Created by ZHLian on 16/10/27.
 */

public class WSFragment extends Fragment{
    private int[] mArray = new int[]{2,0,4,4,1};
    private final String TAG = "WSFragment";
    private GridLayout mGridLayout;
    private CarType mCurrentCarType;
    private int wheelNum = 0;

    private ArrayList<Wheel>mWheels;

    private Context mContext;
    private TableLayout mTableLayout;
    private DIYTitleBar titleBar;
    private ImageButton menuButton;
    private ImageButton setButton;
    private TextView stateTextView;

    private LinearLayout tableContainer;
    private LinearLayout detailContainer;
    private LinearLayout handleContainer;

    private Button changeBtn;//替换
    private Button exChangeBtn;//互换
    private static int clickTimes;
    private static int menuClick;//菜单切换标记
    private static ClickRecoder recorder;//记录选择轮胎

    private static int mState = 0;//0 表示正常,1 表示 查看详细,2表示更换轮胎

    private TextView airpreTV;//详细中用来显示气压
    private TextView temperTV;//详细中用来显示温度

    private ImageView airPointer;//气压指针
    private ImageView temPointer;//温度指针
    private LinearLayout linearLayout;//获取的进度条

    private DiySeekbar airSeekBar;
    private DiySeekbar tempSeekBar;
    private static int sblength;

    private static SysSettings sysSettings;


    final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Log.d(TAG,"revice message");
                    break;
                case 2:
                    Log.d(TAG,"refreh air and pressure");
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wheel_state,null);
//        mTableLayout = (TableLayout)view.findViewById(R.id.wheel_table);
        titleBar = (DIYTitleBar)view.findViewById(R.id.diy_titlebar);
        menuButton = (ImageButton)view.findViewById(R.id.diy_titlebar_menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"menu button onclick");
                if (menuClick == 0){
                    switchMode(2);
                    menuClick = 1;
                }
                else {
                    switchMode(0);
                    menuClick = 0;
                }
            }
        });
        setButton = (ImageButton)view.findViewById(R.id.diy_titlebar_setting);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                switchFragment(new APPSETFragment());
                Intent intent = new Intent();
                intent.setClass(getActivity(),APPSETActivity.class);
                startActivity(intent);

            }
        });
        stateTextView = (TextView)view.findViewById(R.id.diy_titlebar_text);

        tableContainer = (LinearLayout)view.findViewById(R.id.table_container);
        detailContainer = (LinearLayout)view.findViewById(R.id.detail_container);
        handleContainer = (LinearLayout)view.findViewById(R.id.handle_layout);

        changeBtn = (Button)view.findViewById(R.id.tihuan_wheel);
        exChangeBtn = (Button)view.findViewById(R.id.huhuan_wheel);

        airPointer = (ImageView)view.findViewById(R.id.pointer_image_airpress);
        temPointer = (ImageView)view.findViewById(R.id.pointer_image_temper);
        airpreTV = (TextView)view.findViewById(R.id.text_view_aipressure);
        temperTV = (TextView)view.findViewById(R.id.text_view_temper);
        linearLayout = (LinearLayout)view.findViewById(R.id.progress_airpre);
        tempSeekBar = (DiySeekbar)view.findViewById(R.id.diy_seekbar_temper);
        airSeekBar = (DiySeekbar)view.findViewById(R.id.diy_seekbar_airpress);

        initView(mCurrentCarType);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"oncreate");
        mContext = getActivity();

        sysSettings = BLEUtils.initSySet(mContext);
//        read data to init mCurrentCarType
        mCurrentCarType = new CarType();
        mCurrentCarType.setmType("type1");
        mCurrentCarType.setWheels(mArray);
//        get wheels and setstate
        mWheels = new ArrayList<Wheel>();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"getqidth111"+linearLayout.getMeasuredWidth());

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    /**
     * 根据车型初始化视图
     * @param carType
     */
    private void initView(CarType carType){
        int []array = carType.getWheels();
        int []pos;
        for (int i = 0;i<array.length;i++){//control table row
            switch (array[i]){
                case 0:
                    pos = new int[]{-1,-1,-1,-1};//空行
                    addWheel2Pos(pos);
                    break;
                case 2:
                    pos = new int[]{0,-1,-1,1};
                    addWheel2Pos(pos);
                    break;
                case 4:
                    pos = new int[]{0,1,0,1};
                    addWheel2Pos(pos);
                    break;
                case 1:
                    pos = new int[]{2};
                    addWheel2Pos(pos);
                    break;
            }

        }

    }


    /**
     * 确定轮胎位置
     * @param num
     * @return
     */
    private View getWheelPosition(int num){
        View view = null;
        if (num == -1){//no wheel exit,return emptylayout to hold position
            view = View.inflate(mContext,R.layout.wheel_empty,null);
            Log.d(TAG,"ADD AN EMPTY VIEW");
            return view;
        }

        if (num == 0){
            view = View.inflate(mContext,R.layout.wheel_left,null);
            Log.d(TAG,"ADD AN LEFT VIEW");
        }else if(num == 1){
            view = View.inflate(mContext,R.layout.wheel_right,null);
            Log.d(TAG,"ADD AN RIGHT VIEW");
        }else if (num == 2){
            view = View.inflate(mContext,R.layout.wheel_single,null);
        }
        Wheel wheel = new Wheel();
        wheel.setmPosition(wheelNum);
        wheel.setmState(1);
        wheel.setmAirPress(0);
        wheel.setmTemper(0);
        wheelNum++;
        mWheels.add(wheel);//add to an array
        ((TextView)view.findViewById(R.id.wheel_pressure_label)).setText(wheel.getmAirPress()+"");
        ((TextView)view.findViewById(R.id.wheel_temper_label)).setText(wheel.getmAirPress()+"");
        view.setOnClickListener(mOnClickListener);
//        if (wheel.getmState()==0){//normal state
//            view.findViewById(R.id.wheel_pic)
//                    .setBackgroundResource(R.drawable.wheel);
//        }else{
//            view.findViewById(R.id.wheel_pic)
//                    .setBackgroundResource(R.drawable.wheel_danger);
//        }
        view.setTag(wheel);
        Log.d(TAG,wheel.getmPosition()+"");

        return view;
    }

    /**
     * 添加每一个轴
     * @param pos
     */
    private void addWheel2Pos(int []pos){

        LinearLayout linearLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight=1;
        linearLayout.setGravity(Gravity.CENTER);
//        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(30,30,10,0);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(layoutParams);

//        TableRow tableRow = new TableRow(mContext);
//        Log.d(TAG,"tabllayout width"+mTableLayout.getWidth()+"kkk"+mTableLayout.getHeight());
//
//        TableRow.LayoutParams layoutParams = new TableRow
//                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                0);
//        layoutParams.weight=1;
//
//        tableRow.setPadding(2,20,1,1);
//        tableRow.setLayoutParams(layoutParams);

        if (pos.length == 1){
            tableContainer.addView(getWheelPosition(pos[0]));

        }else{
            tableContainer.addView(linearLayout);
            for (int j = 0;j < 4;j++){
            linearLayout.addView(getWheelPosition(pos[j]));
        }
        }
        tableContainer.invalidate();
//        tableContainer.refreshDrawableState();
    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
//            do something
            Wheel wheel = (Wheel) v.getTag();
            if (mState == 2){//handle state
                initRecoder(wheel,v);
                }
            else{//to detail state
                switchMode(1);
                if (airSeekBar.getWidth()!=0){
                    initDetail(wheel);
                }
            }
        }
    };

    /**
     * 切换当前fragment
     * @param fragment
     */
    public void switchFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager != null){
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
        }
    }

    /**
     * switch mode among normal,detailview and handlewheel
     * @param mode
     */
    public void switchMode(int mode){
        mState = mode;
        switch (mState){
            case 0:
                back2Normal();
                detailContainer.setVisibility(View.GONE);
                handleContainer.setVisibility(View.GONE);
                break;
            case 1:
                scaleTable();
                detailContainer.setVisibility(View.VISIBLE);
                handleContainer.setVisibility(View.GONE);

                break;
            case 2:
                recorder = new ClickRecoder();
                scaleTable();
                handleContainer.setVisibility(View.VISIBLE);
                detailContainer.setVisibility(View.GONE);
                break;

        }

//        detailContainer.setScaleX(0.5f);

        DisplayMetrics dm = new DisplayMetrics();
//        detailContainer.setPivotX(dm.widthPixels/2);
        Log.d(TAG,"getqidth000"+airSeekBar.getWidth());


    }

    public void back2Normal(){
        tableContainer.setScaleX(1);
        tableContainer.setScaleY(1);
//        tableContainer.setPivotX(0);
    }

    public void scaleTable(){
        tableContainer.setScaleX(0.8f);
        tableContainer.setScaleY(1);
        tableContainer.setPivotX(0);
        tableContainer.setPivotY(100);
    }

    public void initDetail(Wheel wheel){
        sysSettings = BLEUtils.initSySet(mContext);
        if (wheel != null){

            switch (sysSettings.getmTemperUnit()){
                case SysSettings.SYS_TEMPER_UNIT_HUA:
                    airpreTV.setText(wheel.getmTemper()+"F");
                    break;
                case SysSettings.SYS_TEMPER_UNIT_SHE:
                    airpreTV.setText(wheel.getmTemper()+"C");
                    break;
            }

            String unit = "";
            switch (sysSettings.getmAirPressUnit()){
                case 1:
                    unit = "Bar";
                    break;
                case 2:
                    unit = "PSI";
                    break;
                case 3:
                    unit = "Kpa";
                    break;
                case 4:
                    unit = "kg";
                    break;

            }
            temperTV.setText(wheel.getmAirPress()+unit);
            float xp = airPointer.getX();
            float yp = airPointer.getY();
            LinearLayout.LayoutParams layoutParams = new LinearLayout
                    .LayoutParams(BLEUtils.dp2px(getResources(),20),
                    BLEUtils.dp2px(getResources(),20));
            sblength = airSeekBar.getWidth();
//            int marginleft = (int)sblength/100*wheel.getmAirPress();

//            layoutParams.setMargins(marginleft,0,0,0);
//            由于自身宽度,pointer距离左侧有10dp
//            if(sysSettings.getMinAirPressure()>wheel.getmAirPress()){//小于最低值
//                int pxValue = BLEUtils.stringToInt(sblength*0.2*wheel.getmAirPress()/sysSettings.getMinAirPressure()+"");
//                if (BLEUtils.px2dp(getResources(),pxValue)<10){
//                    Log.d(TAG,"");
//                }else{
//                    layoutParams.setMargins(pxValue-BLEUtils.dp2px(getResources(),10),0,0,0);
//                }
//
//            }else if (wheel.getmAirPress()>sysSettings.getMinAirPressure()
//                    && wheel.getmAirPress()<sysSettings.getMaxAirPressure()){//正常区间
//                int pxValue = BLEUtils
//                        .stringToInt((sblength*0.6*wheel
//                        .getmAirPress())/(sysSettings.getMaxAirPressure()
//                        -sysSettings.getMinAirPressure())+sblength*0.2-BLEUtils.dp2px(getResources(),10)+"");
//                layoutParams.setMargins(pxValue,0,0,0);
//
//            }else{
//                int pxValue = BLEUtils
//                        .stringToInt(((0.8*sblength-BLEUtils.dp2px(getResources(),10))+0.2*sblength)+"");
//                layoutParams.setMargins(pxValue,0,0,0);
//
//            }
            int px = (int)((0.8*sblength-BLEUtils.dp2px(getResources(),10)));
            Log.d(TAG,px+"ssss");
//            layoutParams.setMargins(slidePointer(1,wheel.getmAirPress()),0,0,0);
//            airPointer.setLayoutParams(layoutParams);

        }

    }

    /**
     * 确定指针的位置
     * @param type 表示温度或者气压
     * @param current 当前温度或气压
     * @return
     */
    public int slidePointer(int type,float current){
        int slide = 0;
        switch (type){
            case 1://air
                if(sysSettings.getMinAirPressure()>current){//小于最低值
                int pxValue = BLEUtils.stringToInt(sblength*0.2*current/sysSettings.getMinAirPressure()+"");
                if (BLEUtils.px2dp(getResources(),pxValue)<10){
                    slide = 0;
                }else{
                    slide = pxValue-BLEUtils.dp2px(getResources(),10);
                }

            }else if (current>sysSettings.getMinAirPressure()
                    && current<sysSettings.getMaxAirPressure()){//正常区间
                 slide = BLEUtils
                        .stringToInt((sblength*0.6*current)/(sysSettings.getMaxAirPressure()
                        -sysSettings.getMinAirPressure())+sblength*0.2-BLEUtils.dp2px(getResources(),10)+"");
            }else{
                 slide = BLEUtils
                        .stringToInt(((0.8*sblength-BLEUtils.dp2px(getResources(),10))+0.2*sblength)+"");
            }
                break;
            case 0://temper
                slide =  0;
                break;
        }

    return slide;
    }

    public void changeViewWithMsg(){


    }

    /**
     * function to init handlebutton exchang and change,refresh recorder class after handle
     */
    public void initHandleButton(){
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"wheell already changed");

            }
        });

        exChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"wheell already exchanged");
            }
        });

    }

    /**
     * init @param recorder
     */
    public void initRecoder(Wheel wheel,View v){
        if (recorder.fisrtPostion == wheel.getmPosition()){
            ((ImageView)v.findViewById(R.id.wheel_pic))
                    .setImageResource(R.drawable.wheel);
            recorder.fisrtPostion = -1;
            clickTimes = 0;
            Log.d(TAG,"first selected cancle");
        }else {
            if (clickTimes == 0){
                ((ImageView)v.findViewById(R.id.wheel_pic))
                        .setImageResource(R.drawable.wheel_danger);
                recorder.fisrtPostion = wheel.getmPosition();
                Log.d(TAG,"first selected");
                clickTimes ++;
            }else{
                if (wheel.getmPosition() == recorder.secondPosition){
                    recorder.secondPosition = -1;
                    ((ImageView)v.findViewById(R.id.wheel_pic))
                            .setImageResource(R.drawable.wheel);
                    Log.d(TAG,"secondposition selected cancle");
                }else {
                    ((ImageView)v.findViewById(R.id.wheel_pic))
                            .setImageResource(R.drawable.wheel_danger);
                    recorder.secondPosition = wheel.getmPosition();
                    Log.d(TAG,"secondposition selected");
                }

            }

        }
    }

    /**
     * class to record wheel postion
     */
    private class ClickRecoder{
        int fisrtPostion = -1;
        int secondPosition = -1;
    }


}
