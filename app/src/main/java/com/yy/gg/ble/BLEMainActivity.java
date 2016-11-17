package com.yy.gg.ble;

import android.support.v4.app.Fragment;

import com.yy.gg.ble.fragments.BTFragment;
import com.yy.gg.ble.fragments.SingleFragmentActivity;

public class BLEMainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new BTFragment();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_blemain);
////        从本地读取数据,判断是否存在已经连接过的设备
////        Intent intent = new Intent();
////        intent.setClass(getApplicationContext(), WSActivity.class);
////        Bundle bundle = new Bundle();
////        startActivity(intent);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
//
//        if (fragment == null){
//            fragment = new WSFragment();
//            fragmentManager.beginTransaction()
//                    .add(R.id.fragmentContainer,fragment).commit();
//
//        }
////      read data to confirm state
//
//
//
//    }
}
