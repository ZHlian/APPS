package com.yy.gg.ble.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.yy.gg.ble.R;
import com.yy.gg.ble.model.SysSettings;
import com.yy.gg.ble.utils.BLEUtils;

/**
 * Created by ZHLian on 16/10/27.
 */

public class APPSETFragment extends Fragment {

    public static final String SETTINGS = "syssetting";
    public static final String TAG = "APPSETFragment";
    private static final int REQUEST_CODE_START_CARTYPE = 0;

    private ImageButton bindCarType;//button bind car
    private ImageButton setNickName;
    private ImageButton setPass;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            Intent intent = new Intent();
            intent.setClass(getActivity(),SomeActiviaty.class);
            if ((int)v.getTag() == 1){//pass

                bundle.putString("kind",1+"");

            }else{//nickname
                bundle.putString("kind",2+"");
            }
            intent.putExtra("kind",bundle);
            startActivity(intent);
        }
    };

    private Button btnChineseSimple;
    private Button btnChineseMoti;
    private Button btnEng;
    //airpressure
    private Button air1;
    private Button air2;
    private Button air3;
    private Button air4;


    //temper
    private Button tempHua;
    private Button temShe;
    //
    private Switch noise;
    private Switch shake;
    private Switch obd;

    //
    private SeekBar minAirPre;
    private SeekBar maxAirPre;
    private SeekBar maxTemper;

    //data to store in sharedpreference
    private int language;//1,2,3 present sc,mc,eg
    private int airPressureUnit;//
    private int temperUnit;//1,2 present hua,she

    private boolean isNoise;//true means yeah
    private boolean isShake;//
    private boolean isOBD;//

    private float minPre;//
    private float maxPre;//
    private float maxTemp;//
    //back button

    private ImageButton backButton;
    private View.OnClickListener languageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG,"langguage click at"+v.getTag());
            toggleBGColor((int)v.getTag());

        }
    };
    private View.OnClickListener airPressureClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG,"airpressure click at"+v.getTag());
            toggleBGColorAP((int)v.getTag());
        }
    };
    private View.OnClickListener temperClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG,"temper click at"+v.getTag());
            toggleBGColorTP((int)v.getTag());
        }
    };

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.d(TAG,seekBar.getTag()+"progress");
            String ss = null;
            switch ((int)seekBar.getTag()){
                case 1:
                    ss = getString(R.string.pressure_changed);
                    break;
                case 2:
                    ss = getString(R.string.pressure_changed);
                    break;
                case 3:
                    ss = getString(R.string.temper_max_change);
                    break;
            }
            showToast(ss);

        }
    };




//    private SharedPreferences sharedPreferences;

//    private SharedPreferences.Editor editor;




    public APPSETFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        sharedPreferences =  getActivity()
//                .getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();
        getActivity().setTitle("应用设置");
//        getActivity().getActionBar().setHomeButtonEnabled(true);
//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActivity().getActionBar().setDisplayShowHomeEnabled(true);
//        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.setting_fragment,null);
        backButton = (ImageButton) view.findViewById(R.id.diy_titlebar_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        bindCarType = (ImageButton) view.findViewById(R.id.bind_cartype);
        bindCarType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"switch to cartype select view");
//                getFragmentManager().beginTransaction()
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                CarTypeFragment carTypeFragment = new CarTypeFragment();
                if (fragmentManager != null){
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer,carTypeFragment).commit();
                }

            }
        });


        setNickName = (ImageButton)view.findViewById(R.id.bt_nickname_setting);
        setPass = (ImageButton)view.findViewById(R.id.pass_setting);
        setPass.setTag(1);
        setNickName.setTag(2);
        setNickName.setOnClickListener(listener);
        setPass.setOnClickListener(listener);

        //read from sp to set language button
        btnChineseSimple = (Button)view.findViewById(R.id.setting_language_cn);
        btnChineseSimple.setTag(1);
        btnChineseSimple.setOnClickListener(languageClickListener);

        btnChineseMoti = (Button)view.findViewById(R.id.setting_language_fan);
        btnChineseMoti.setTag(2);
        btnChineseMoti.setOnClickListener(languageClickListener);

        btnEng = (Button)view.findViewById(R.id.setting_language_en);
        btnEng.setTag(3);
        btnEng.setOnClickListener(languageClickListener);


        air1 = (Button)view.findViewById(R.id.air_pre_one);
        air2 = (Button)view.findViewById(R.id.air_pre_two);
        air3 = (Button)view.findViewById(R.id.air_pre_three);
        air4 = (Button)view.findViewById(R.id.air_pre_four);
        air1.setTag(1);
        air2.setTag(2);
        air3.setTag(3);
        air4.setTag(4);
        air1.setOnClickListener(airPressureClickListener);
        air2.setOnClickListener(airPressureClickListener);
        air3.setOnClickListener(airPressureClickListener);
        air4.setOnClickListener(airPressureClickListener);

        tempHua = (Button)view.findViewById(R.id.setting_temper_hua);
        temShe = (Button)view.findViewById(R.id.setting_temper_she);
        tempHua.setTag(1);
        temShe.setTag(2);
        tempHua.setOnClickListener(temperClickListener);
        temShe.setOnClickListener(temperClickListener);

        noise = (Switch)view.findViewById(R.id.setting_noise_switch);
        noise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isNoise = isChecked;
                Log.d(TAG,"noise"+isChecked);
                String ss;
                if (isChecked){
                    ss = getString(R.string.noise_switch_on);
                }else {
                    ss = getString(R.string.noise_switch_off);
                }
                showToast(ss);
                BLEUtils.saveSPBoolean(getActivity(),SysSettings.SYS_RING,isNoise);
            }
        });

        shake = (Switch)view.findViewById(R.id.setting_shake_switch);
        shake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isShake = isChecked;
                Log.d(TAG,"shake"+isChecked);
                String ss;
                if (isChecked){
                    ss = getString(R.string.shake_switch_on);
                }else{
                    ss = getString(R.string.shake_switch_off);
                }
                showToast(ss);
                BLEUtils.saveSPBoolean(getActivity(),SysSettings.SYS_SHAKE,isShake);
            }
        });

        obd = (Switch)view.findViewById(R.id.setting_OBD_switch);
        obd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isOBD = isChecked;
                Log.d(TAG,"obd"+isChecked);

                String ss;
                if (isChecked){
                    ss = getString(R.string.OBD_switch_on);
                }else{
                    ss = getString(R.string.OBD_switch_off);
                }
                showToast(ss);
                BLEUtils.saveSPBoolean(getActivity(),SysSettings.SYS_OBD,isOBD);

            }
        });

        minAirPre = (SeekBar)view.findViewById(R.id.setting_pressure_min);
        maxAirPre = (SeekBar)view.findViewById(R.id.setting_pressure_max);
        maxTemper = (SeekBar)view.findViewById(R.id.setting_temper_max);
        minAirPre.setTag(1);
        maxAirPre.setTag(2);
        maxTemper.setTag(3);

        minAirPre.setOnSeekBarChangeListener(seekBarChangeListener);
        maxAirPre.setOnSeekBarChangeListener(seekBarChangeListener);
        maxTemper.setOnSeekBarChangeListener(seekBarChangeListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //init pre status
        toggleBGColor(BLEUtils.loadSettingsIntger(getActivity(),SysSettings.SYS_LANGUAGE));
        toggleBGColorTP(BLEUtils.loadSettingsIntger(getActivity(),SysSettings.SYS_TMEPUNIT));
        toggleBGColorAP(BLEUtils.loadSettingsIntger(getActivity(),SysSettings.SYS_PRESSURE_UNIT));

        setNoiseSwitch(BLEUtils.loadSettingsBoolean(getActivity(),SysSettings.SYS_RING));
        setShakeSwitch(BLEUtils.loadSettingsBoolean(getActivity(),SysSettings.SYS_SHAKE));
        setOBDSwitch(BLEUtils.loadSettingsBoolean(getActivity(),SysSettings.SYS_OBD));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //update view through current syssetting
    public void updateLanguageView(int tag){

    }

    public void updateTempView(int tag){

    }

    public void updateAirPre(int tag){

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void toggleBGColor(int tag){
        BLEUtils.saveSPInt(getActivity(),SysSettings.SYS_LANGUAGE,tag);
        if (tag == 1){
            btnChineseSimple.setBackground(getResources()
                    .getDrawable(R.drawable.border_drawable));
            btnChineseMoti.setBackgroundColor(getResources().getColor(R.color.unselected));
            btnEng.setBackgroundColor(getResources().getColor(R.color.unselected));

        }else if (tag==2){
            btnChineseMoti.setBackground(getResources()
                    .getDrawable(R.drawable.border_drawable));
            btnChineseSimple.setBackgroundColor(getResources().getColor(R.color.unselected));
            btnEng.setBackgroundColor(getResources().getColor(R.color.unselected));

        }else if (tag==3){
            btnEng.setBackground(getResources()
                    .getDrawable(R.drawable.border_drawable));
            btnChineseMoti.setBackgroundColor(getResources().getColor(R.color.unselected));
            btnChineseSimple.setBackgroundColor(getResources().getColor(R.color.unselected));
        }else {
            Log.e(TAG,"error init language");
        }

    }

    public void setNoiseSwitch(boolean flag){
            noise.setChecked(flag);
    }
    public void setShakeSwitch(boolean flag){
        shake.setChecked(flag);
    }
    public void setOBDSwitch(boolean flag){
        obd.setChecked(flag);
    }

    public void toggleBGColorAP(int tag){
        BLEUtils.saveSPInt(getActivity(),SysSettings.SYS_PRESSURE_UNIT,tag);
        if (tag == 1){
            air1.setBackground(getResources()
                    .getDrawable(R.drawable.border_drawable));
            air2.setBackgroundColor(getResources().getColor(R.color.unselected));
            air3.setBackgroundColor(getResources().getColor(R.color.unselected));
            air4.setBackgroundColor(getResources().getColor(R.color.unselected));

        }else if (tag==2){
            air2.setBackground(getResources()
                    .getDrawable(R.drawable.border_drawable));
            air1.setBackgroundColor(getResources().getColor(R.color.unselected));
            air3.setBackgroundColor(getResources().getColor(R.color.unselected));
            air4.setBackgroundColor(getResources().getColor(R.color.unselected));

        }else if (tag==3){
            air3.setBackground(getResources()
                    .getDrawable(R.drawable.border_drawable));
            air1.setBackgroundColor(getResources().getColor(R.color.unselected));
            air2.setBackgroundColor(getResources().getColor(R.color.unselected));
            air4.setBackgroundColor(getResources().getColor(R.color.unselected));
        }else if (tag==4){
            air4.setBackground(getResources()
                    .getDrawable(R.drawable.border_drawable));
            air2.setBackgroundColor(getResources().getColor(R.color.unselected));
            air1.setBackgroundColor(getResources().getColor(R.color.unselected));
            air3.setBackgroundColor(getResources().getColor(R.color.unselected));
        }

    }
    public void toggleBGColorTP(int tag){
        BLEUtils.saveSPInt(getActivity(),SysSettings.SYS_TMEPUNIT,tag);
        if (tag == 1){
            tempHua.setBackground(getResources()
                    .getDrawable(R.drawable.border_drawable));
            temShe.setBackgroundColor(getResources().getColor(R.color.unselected));

        }else if (tag==2){
            temShe.setBackground(getResources()
                    .getDrawable(R.drawable.border_drawable));
            tempHua.setBackgroundColor(getResources().getColor(R.color.unselected));

        }

    }
    public void showToast(String ss){
        Toast.makeText(getActivity(),ss,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_back_2settting:
                Log.d(TAG,"back to wheel state");
                switchFragment(new WSFragment());
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.cartype_select_menu,menu);
    }

    public void switchFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager != null){
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
        }
    }
}
