package com.swufe.hearthstoneassistant.fragments;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.swufe.hearthstoneassistant.DB.CardManager;
import com.swufe.hearthstoneassistant.R;
import com.swufe.hearthstoneassistant.Service.HtmlService;
import com.swufe.hearthstoneassistant.adapter.GridAdapter;
import com.swufe.hearthstoneassistant.adapter.ListAdapter;
import com.swufe.hearthstoneassistant.bean.Card;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Function extends Fragment implements Runnable, AdapterView.OnItemClickListener{

    private GridView gridView;
    private ListView listView;
    private GridAdapter gridAdapter;
    private ListAdapter listAdapter;
    private Handler handler;
    private HashMap<String,Integer> map;
    private SharedPreferences sp;
    private String date;
    List<String> strList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.function_layout,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gridView = getView().findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        listView = getView().findViewById(R.id.listView);
//        listView.setOnItemClickListener(this);
        map = new HashMap<>();
        strList = new LinkedList<>();

        sp = getActivity().getSharedPreferences("card", Activity.MODE_PRIVATE);
        date = sp.getString("date","");

        //当前日期
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(now);

        if(date.equals(nowDate)){
            List<Card> list = new ArrayList<>();
            CardManager rateManager = new CardManager(getContext());
            list = rateManager.findAll();
            gridAdapter = new GridAdapter(getContext(),
                    R.layout.list_item_layout,
                    list);
            gridView.setAdapter(gridAdapter);
        }
        else{
            getData();

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("date",nowDate);
            editor.apply();
        }

    }


    private void getData(){
        Thread t = new Thread(this);
        t.start();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 2){
                    String json =(String)msg.obj;
                    json = json.replace("<b>","");
                    json = json.replace("</b>","");

                    List<Card> list =  JSON.parseArray(json, Card.class);
                    gridAdapter = new GridAdapter(getContext(),R.layout.item_layout,list);
                    gridView.setAdapter(gridAdapter);

                    CardManager cardManager = new CardManager(getContext());
                    cardManager.deleteAll();
                    cardManager.addAll(list);

                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void run() {
        String json = null;
        try {
            String url = "https://api.hearthstonejson.com/v1/63160/zhCN/cards.collectible.json";
            json = HtmlService.getHtml(url);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Message msg = handler.obtainMessage(2);
        msg.obj = json;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long i) {
            Card card = (Card) gridView.getItemAtPosition(position);
            String id = card.getId();
            String name = card.getName();
            strList.clear();

            if(!map.containsKey(id + "," + name)){
                map.put(id + "," + name,1);
            }
            else {
                if(map.get(id + "," + name) == 2){
                    Toast.makeText(getContext(),"卡牌数量已达卡组上限",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    map.put(id + "," + name,2);
                }
            }
            for(String item:map.keySet()){
                strList.add(item.split(",")[1] + "," + map.get(item));
            }

            listAdapter = new ListAdapter(getContext(),R.layout.list_item_layout,strList);
            listView.setAdapter(listAdapter);
    }
}
