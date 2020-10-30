package com.swufe.hearthstoneassistant.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.swufe.hearthstoneassistant.R;
import com.swufe.hearthstoneassistant.adapter.WarehouseListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Warehouse extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private SharedPreferences sp;
    private ListView listView;
    private List<String> list;
    private HashMap<String,String> map;
    private WarehouseListAdapter warehouseListAdapter;
    AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.warehouse_layout,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getView().findViewById(R.id.warehouse_listView);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        map = new HashMap<>();
        list = new ArrayList<>();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String name = (String)listView.getItemAtPosition(position);
        String cards = map.get(name);
        String[] cardsArray = cards.split(";");
        for(int i = 0;i < cardsArray.length;i++){
            cardsArray[i] = cardsArray[i].split(",")[2] + "✖" + cardsArray[i].split(",")[1];
        }
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("卡组详情")
                .setItems(cardsArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("提示")
                .setMessage("确认删除卡组？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        warehouseListAdapter.remove(listView.getItemAtPosition(position));
                    }
                })
                .setNegativeButton("否",null);
        builder.create().show();
        return true;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        list.clear();
        sp = getActivity().getSharedPreferences("cards", Activity.MODE_PRIVATE);
        for(String key:sp.getAll().keySet()){
            map.put(key,(String)sp.getAll().get(key));
            list.add(key);
        }
        warehouseListAdapter = new WarehouseListAdapter(getContext(),
                R.layout.wh_list_item_layout,
                list);
        listView.setAdapter(warehouseListAdapter);
    }
}
