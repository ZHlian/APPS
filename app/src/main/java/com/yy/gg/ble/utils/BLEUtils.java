package com.yy.gg.ble.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.yy.gg.ble.fragments.APPSETFragment;
import com.yy.gg.ble.model.SysSettings;

/**
 * Created by ZHLian on 16/10/27.
 */

public class BLEUtils {
    /**
     * judge positon
     * @param wheelNumber number of current line,left to right
     * @return
     */
    public static int judgePostion(int wheelNumber){
        return wheelNumber%2;
    }

    /**
     *
     * @param airPressure
     * @return
     */
    public static int judgeAirPress(float airPressure){
        return 0;
    }

    /**
     *
     * @param temper
     * @return
     */
    public static int judgeTemper(float temper){
        return 0;
    }

    /**
     *
     * @param settings
     * @return
     */
    public static boolean saveSettings(SysSettings settings){
        return true;
    }

    /**
     *
     * @param context
     * @param name
     * @return
     */
    public static String loadSettingsString(Context context,String name){
        SharedPreferences sp = context.getSharedPreferences(APPSETFragment.SETTINGS,Context.MODE_PRIVATE);
        return sp.getString(name,"q");

    }

    /**
     *
     * @param context
     * @param name
     * @return
     */
    public static int loadSettingsIntger(Context context,String name){
        SharedPreferences sp = context.getSharedPreferences(APPSETFragment.SETTINGS,Context.MODE_PRIVATE);
        return sp.getInt(name,1);
    }

    /**
     *
     * @param context
     * @param name
     * @return
     */
    public static boolean loadSettingsBoolean(Context context,String name){
        SharedPreferences sp = context.getSharedPreferences(APPSETFragment.SETTINGS,Context.MODE_PRIVATE);
        return sp.getBoolean(name,false);
    }

    /**
     *
     * @param context
     * @param name
     * @param value
     */
    public static void saveSPString(Context context,String name,String value){
        SharedPreferences sp = context.getSharedPreferences(APPSETFragment.SETTINGS,Context.MODE_PRIVATE);
        sp.edit().putString(name,value).apply();
    }

    /**
     *
     * @param context
     * @param name
     * @param value
     */
    public static void saveSPInt(Context context,String name,int value){
        SharedPreferences sp = context.getSharedPreferences(APPSETFragment.SETTINGS,Context.MODE_PRIVATE);
        sp.edit().putInt(name,value).apply();
    }

    /**
     *
     * @param context
     * @param name
     * @param value
     */
    public static void saveSPFloat(Context context,String name,float value){
        SharedPreferences sp = context.getSharedPreferences(APPSETFragment.SETTINGS,Context.MODE_PRIVATE);
        sp.edit().putFloat(name,value).apply();
    }
    public static void saveSPBoolean(Context context,String name,boolean value){
        SharedPreferences sp = context.getSharedPreferences(APPSETFragment.SETTINGS,Context.MODE_PRIVATE);
        sp.edit().putBoolean(name,value).apply();
    }

    public static float loadSPFloat(Context context,String name){
        SharedPreferences sp = context.getSharedPreferences(APPSETFragment.SETTINGS,Context.MODE_PRIVATE);
        return sp.getFloat(name,0);
    }

    public static boolean loadSPBoolean(Context context,String name){
        SharedPreferences sp = context.getSharedPreferences(APPSETFragment.SETTINGS,Context.MODE_PRIVATE);
        return sp.getBoolean(name,false);
    }

    public static SysSettings initSySet(Context context){
        SysSettings sysSettings = SysSettings.getInstance();
        sysSettings.setMaxAirPressure(loadSPFloat(context,SysSettings.SYS_MAX_PRESSURE));
        sysSettings.setMinAirPressure(loadSPFloat(context,SysSettings.SYS_MIN_PRESSURE));
        sysSettings.setMaxTemper(loadSPFloat(context,SysSettings.SYS_MAX_TEMPER));
        sysSettings.setmCarTypeName(loadSettingsString(context,SysSettings.SYS_CARTYPE));
        sysSettings.setmLanguage(loadSettingsIntger(context,SysSettings.SYS_LANGUAGE));
        sysSettings.setmPass(loadSettingsString(context,SysSettings.SYS_PASS));
        sysSettings.setmRing(loadSPBoolean(context,SysSettings.SYS_RING));
        sysSettings.setmShack(loadSPBoolean(context,SysSettings.SYS_SHAKE));
        sysSettings.setmTemperUnit(loadSettingsIntger(context,SysSettings.SYS_TMEPUNIT));
        sysSettings.setmAirPressUnit(loadSettingsIntger(context,SysSettings.SYS_PRESSURE_UNIT));
        return sysSettings;
    }

    public static int dp2px(Resources resources,int dp){
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int ppi = displayMetrics.densityDpi;
        return ppi*dp/160;
    }
    public static int px2dp(Resources resources,int px){
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int ppi = displayMetrics.densityDpi;
        return px*160/ppi;

    }


    public static int stringToInt(String string){
        int j = 0;
        String str = string.substring(0, string.indexOf(".")) + string.substring(string.indexOf(".") + 1);
        int intgeo = Integer.parseInt(str);
        return intgeo;
    }



}
