package com.yy.gg.ble.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by ZHLian on 16/11/14.
 */

public class APPSETActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {

        return new APPSETFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (getActionBar() != null){
            getActionBar().setHomeButtonEnabled(true);
        }

    }
}
