package com.yy.gg.ble.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by ZHLian on 16/11/12.
 */

public class WSActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new WSFragment();
    }
}
