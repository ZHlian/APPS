package com.yy.gg.ble.model;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by ZHLian on 16/10/27.
 */

public class CarTypeStore {
    private ArrayList<CarType> mCarTypes;
    private static CarTypeStore sCarTypeStore;
    private Context mContext;

     public static CarTypeStore newInstance(Context context) {
         if ( null == sCarTypeStore ){
             sCarTypeStore = new CarTypeStore(context);
         }
         return sCarTypeStore;
    }


     private CarTypeStore(Context context){
        mContext = context;
//        mo ni data
        mCarTypes = new ArrayList<CarType>();


    }



}
