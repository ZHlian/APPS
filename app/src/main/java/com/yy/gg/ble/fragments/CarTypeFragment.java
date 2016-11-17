package com.yy.gg.ble.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.yy.gg.ble.R;
import com.yy.gg.ble.diy.DiyBackBar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ZHLian on 16/11/9.
 */

public class CarTypeFragment extends Fragment {
    private GridView mGridView;
    private ImageButton backButton;
    private DiyBackBar diyBackBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartype_select,null);
        diyBackBar = (DiyBackBar)view.findViewById(R.id.cartype_back);
        diyBackBar.getBackIMB().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer,new APPSETFragment()).commit();
            }
        });

        diyBackBar.getTitleTextView().setText("选择车型");
        mGridView = (GridView)view.findViewById(R.id.cartype_holder_gridview);
        ArrayList<HashMap<String,Object>> lst = new ArrayList<HashMap<String,Object>>();
        for(int i = 0 ;i < 10; i++)
        {
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("itemImage", android.R.drawable.star_on);
            map.put("itemText", "车型"+i);

            lst.add(map);
        }

        SimpleAdapter adpter = new SimpleAdapter(getActivity(),
                lst,R.layout.layout_cartype_item,
                new String[]{"itemImage","itemText"},
                new int[]{R.id.imageView_ItemImage,R.id.textView_ItemText});

        mGridView.setAdapter(adpter);

        mGridView.setOnItemClickListener(new gridView1OnClickListener());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public class gridView1OnClickListener implements AdapterView.OnItemClickListener
    {
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            // TODO Auto-generated method stub
            Object obj = mGridView.getAdapter().getItem(arg2);
            HashMap<String,Object> map  = (HashMap<String,Object>)obj;
            String str = (String) map.get("itemText");

            Toast.makeText(getActivity(),str+"",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_back_2settting){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer,new APPSETFragment()).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cartype_select_menu,menu);

    }
}
