package com.yy.gg.ble.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yy.gg.ble.R;
import com.yy.gg.ble.model.Blueteeth;
import com.yy.gg.ble.model.SampleGattAttributes;
import com.yy.gg.ble.service.BluetoothLeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by ZHLian on 16/10/27.
 */

public class BTFragment extends Fragment {

    private static final String TAG = "BTFragment";
    private ArrayList<Blueteeth>blueteeths;
    private ListView listView;
    private View mView;
    private BluetoothAdapter mAdapter;

    private BluetoothLeService mBluetoothLeService;
    private Handler mHandler;
    private Set<BluetoothDevice>mAllBtDevices;

    private boolean mScanning;
    private BTListAdapter adapter;

    private static final int SCAN_PERIOD = 10000;

    private String mDeviceAddress;
    private ImageButton refreshBtn;

    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    private static int mPosition;


    private BluetoothAdapter.LeScanCallback mLeScanCallBack = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mAllBtDevices.contains(device)){

                    }else{
                        mAllBtDevices.add(device);
//                        blueteeths.add(changeToModel(device,1));
                        adapter.add(changeToModel(device,1));
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    };


    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(mPosition);
//                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(mPosition);
//                invalidateOptionsMenu();
//                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
//                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                getActivity().finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }

    };


//    定义一个旋转的动画效果

    private Animation getRotateAnimation(){

        Animation animation;
        animation=new RotateAnimation(0f, 360f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);//设置图片动画属性，各参数说明可参照api
        animation.setRepeatCount(3);//设置旋转重复次数，即转几圈
        animation.setDuration(1000);//设置持续时间，注意这里是每一圈的持续时间，如果上面设置的圈数为3，持续时间设置1000，则图片一共旋转3秒钟
        animation.setInterpolator(new LinearInterpolator());//设置动画匀速改变。相应的还有AccelerateInterpolator、DecelerateInterpolator、CycleInterpolator等
        animation.setAnimationListener(new Animation.AnimationListener() {	//设置动画监听事件
            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }
            //图片旋转结束后触发事件，这里启动新的activity
            @Override
            public void onAnimationEnd(Animation arg0) {
            }
        });

        return animation;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.blueteeth_choose_fragment,null);
        listView = (ListView)view.findViewById(R.id.bt_list);
        refreshBtn = (ImageButton)view.findViewById(R.id.diy_titlebar_refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshBtn.setAnimation(getRotateAnimation());
                scanLeDevice(true);
                Toast.makeText(getActivity(),"开始搜索蓝牙设备",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        setHasOptionsMenu(true);
        mHandler = new Handler();

        mAllBtDevices = new HashSet<BluetoothDevice>();
        /**
         * judge device is suitable for ble
         */
        if (!getActivity().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        {
            Toast.makeText(getActivity(),"not support",Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        final BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        mAdapter = bluetoothManager.getAdapter();

        //默认开始就进行搜索
//        scanLeDevice(true);
//
        Set<BluetoothDevice> pairedDevices = mAdapter.getBondedDevices();
        blueteeths = new ArrayList<Blueteeth>();
//
//        mAllBtDevices.addAll(pairedDevices);
//        for (BluetoothDevice device : pairedDevices){
//            blueteeths.add(changeToModel(device,1));
//            Log.d("device",device.getName());
//        }



//        getActivity().setTitle("蓝牙连接");
////        模拟数据
//        for (int i = 0 ;i < 5;i++){
//            Blueteeth blueteeth = new Blueteeth();
//            blueteeth.setmName("蓝牙"+i);
//            blueteeth.setmNickName("nick"+i);
//            blueteeth.setmNumber(i+1);
//            blueteeth.setmState(i%2);
//            blueteeths.add(blueteeth);
//        }

        adapter = new BTListAdapter(blueteeths);

        Intent gattServiceIntent = new Intent(getActivity(), BluetoothLeService.class);
        getActivity().bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
//        register bdmessage
        getActivity().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        scanLeDevice(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private class BTListAdapter extends ArrayAdapter<Blueteeth>{
        public BTListAdapter(ArrayList<Blueteeth> blueteeths){
            super(getActivity(),0,blueteeths);
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Nullable
        @Override
        public Blueteeth getItem(int position) {
            return super.getItem(position);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_bt,null);


            }

            Blueteeth blueteeth = getItem(position);
            TextView number = (TextView) convertView
                    .findViewById(R.id.bt_list_item_number);

            number.setText((position+1)+"");

            TextView name = (TextView)convertView
                    .findViewById(R.id.bt_list_item_name);
            name.setText(blueteeth.getmName());

            TextView nickName = (TextView)convertView
                    .findViewById(R.id.bt_list_item_nick_name);
            nickName.setText(blueteeth.getmNickName());

            final TextView stateBTN = (TextView) convertView
                    .findViewById(R.id.bt_list_item_btn_state);
            final ImageButton conBTN = (ImageButton)convertView
                    .findViewById(R.id.bt_list_item_btn_con);

            if (blueteeth.getmState() == 0){
                Log.d(TAG,"0000");
                stateBTN.setText(getString(R.string.bt_state_connected));
                conBTN.setBackgroundResource(R.drawable.sign_check);
            }else{
                stateBTN.setText(getString(R.string.bt_state_notconnect));
                conBTN.setBackgroundResource(R.drawable.pause);
            }
            stateBTN.setTag(position);
            conBTN.setTag(position);
            stateBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //连接蓝牙,修改视图状态
                    Log.d(TAG,"connect button clicked state"+stateBTN.getTag());
                    stateBTN.setText("连接中");
                    mPosition = (int)stateBTN.getTag();
                    Blueteeth bt = blueteeths.get((int)stateBTN.getTag());
//                    startConnect(bt.getmName()); 根据链接状况更新视图
                    updateConnectionState((int)stateBTN.getTag());
                    Toast.makeText(getContext(),
                            R.string.bt_connect_succuess,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(getActivity(),WSActivity.class);
                    startActivity(intent);

                }
            });

            conBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"connect button clicked state");
//                    连接蓝牙,修改视图状态
                    conBTN.setBackgroundResource(R.drawable.load);
                    conBTN.setBackgroundResource(R.drawable.sign_check);
                }
            });

            return convertView;
        }

        @Override
        public int getPosition(Blueteeth item) {
            return super.getPosition(item);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setAdapter(adapter);
    }



    private Blueteeth changeToModel(BluetoothDevice device,int connectState){
        Blueteeth blueteeth = new Blueteeth();
        blueteeth.setmState(connectState);
        blueteeth.setmNickName(device.getName());
        blueteeth.setmName(device.getAddress());
        blueteeth.setmNumber(1);
        return blueteeth;

    }

    /*some methods to finish ble*/

    /**
     * to get intentfilter
     * @return
     */
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }


    private void scanLeDevice(final boolean enable) {
        Log.d(TAG,"start scanning");

        if (enable)
        {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mAdapter.stopLeScan(mLeScanCallBack);
//                    invalidateOptionsMenu();
                }
            },SCAN_PERIOD);

            mScanning = true;
            mAdapter.startLeScan(mLeScanCallBack);
        }
        else
        {
            mScanning = false;
            mAdapter.stopLeScan(mLeScanCallBack);
//            Toast.makeText(getActivity(),"搜索完成",Toast.LENGTH_SHORT);
        }
//        invalidateOptionsMenu();
    }



    private void startConnect(String address){
        mDeviceAddress = address;
        mBluetoothLeService.connect(mDeviceAddress);
    }


    private void updateConnectionState(final int position) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mConnectionState.setText(resourceId);
//                getActivity()

                blueteeths.get(position).setmState(1);
                adapter.notifyDataSetChanged();

            }
        });
    }
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices)
        {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics)
            {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }


    }

}
