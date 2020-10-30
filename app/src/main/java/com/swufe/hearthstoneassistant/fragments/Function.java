package com.swufe.hearthstoneassistant.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alibaba.fastjson.JSON;
import com.swufe.hearthstoneassistant.DB.CardManager;
import com.swufe.hearthstoneassistant.R;
import com.swufe.hearthstoneassistant.Service.HtmlService;
import com.swufe.hearthstoneassistant.adapter.GridAdapter;
import com.swufe.hearthstoneassistant.adapter.ListAdapter;
import com.swufe.hearthstoneassistant.bean.Card;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Function extends Fragment implements Runnable, AdapterView.OnItemClickListener{

    private GridView gridView;  //卡库
    private ListView listView;  //卡组
    private short cardsNum; //卡组包含的卡牌数
    private EditText cardsName; //卡组名
    private TextView cardsNumText;  //卡组数显示框
    private GridAdapter gridAdapter;
    private ListAdapter listAdapter;
    private Handler handler;
    private AlertDialog.Builder builder;    //弹窗
    private HashMap<String,Integer> map;    //卡组映射
    private SharedPreferences sp;
    private Button resetBut,saveBut;
    private List<String> strList;
    private List<Card> cardsList;
    private FragmentManager fragmentManager;
    private RadioGroup cardClassGroup1,cardClassGroup2,costGroup,rarityGroup,typeGroup;
    private String cardClass,rarity,type;
    private short cost;
    private RadioButton NEUTRALBut,cost0But,FREEBut,MINIONBut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.function_layout,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragmentManager = getFragmentManager();
        map = new HashMap<>();
        strList = new LinkedList<>();
        cardsNum = 0;
        cardsNumText = getView().findViewById(R.id.cardsNum);
        cardsName = getView().findViewById(R.id.cardsName);
        cardClassGroup1 = getView().findViewById(R.id.cardClassGroup1);
        cardClassGroup2 = getView().findViewById(R.id.cardClassGroup2);
        costGroup = getView().findViewById(R.id.costGroup);
        rarityGroup = getView().findViewById(R.id.rarityGroup);
        typeGroup = getView().findViewById(R.id.typeGroup);
        NEUTRALBut = getView().findViewById(R.id.NEUTRAL);
        NEUTRALBut.setChecked(true);
        cost0But = getView().findViewById(R.id.cost0);
        cost0But.setChecked(true);
        FREEBut = getView().findViewById(R.id.FREE);
        FREEBut.setChecked(true);
        MINIONBut = getView().findViewById(R.id.MINION);
        MINIONBut.setChecked(true);
        cardClass = "NEUTRAL";
        cost = 1;
        rarity = "FREE";
        type = "MINION";

        cardClassGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.NEUTRAL:
                        cardClass = "NEUTRAL";
                        cardClassGroup2.clearCheck();
                        break;
                    case R.id.MAGE:
                        cardClass = "MAGE";
                        cardClassGroup2.clearCheck();
                        break;
                    case R.id.HUNTER:
                        cardClass = "HUNTER";
                        cardClassGroup2.clearCheck();
                        break;
                    case R.id.DRUID:
                        cardClass = "DRUID";
                        cardClassGroup2.clearCheck();
                        break;
                    case R.id.PALADIN:
                        cardClass = "PALADIN";
                        cardClassGroup2.clearCheck();
                        break;
                    case R.id.PRIEST:
                        cardClass = "PRIEST";
                        cardClassGroup2.clearCheck();
                        break;
                    default:
                        break;
                }
                setGridView();
            }
        });

        cardClassGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.ROGUE:
                        cardClass = "ROGUE";
                        cardClassGroup1.clearCheck();
                        break;
                    case R.id.SHAMAN:
                        cardClass = "SHAMAN";
                        cardClassGroup1.clearCheck();
                        break;
                    case R.id.WARLOCK:
                        cardClass = "WARLOCK";
                        cardClassGroup1.clearCheck();
                        break;
                    case R.id.WARRIOR:
                        cardClass = "WARRIOR";
                        cardClassGroup1.clearCheck();
                        break;
                    case R.id.DEMONHUNTER:
                        cardClass = "DEMONHUNTER";
                        cardClassGroup1.clearCheck();
                        break;
                    default:
                        break;
                }
                setGridView();
            }
        });

        costGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.cost0:
                        cost = 0;
                        break;
                    case R.id.cost1:
                        cost = 1;
                        break;
                    case R.id.cost2:
                        cost = 2;
                        break;
                    case R.id.cost3:
                        cost = 3;
                        break;
                    case R.id.cost4:
                        cost = 4;
                        break;
                    case R.id.cost5:
                        cost = 5;
                        break;
                    case R.id.cost6:
                        cost = 6;
                        break;
                }
                setGridView();
            }
        });

        rarityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.FREE:
                        rarity = "FREE";
                        break;
                    case R.id.COMMON:
                        rarity = "COMMON";
                        break;
                    case R.id.RARE:
                        rarity = "RARE";
                        break;
                    case R.id.EPIC:
                        rarity = "EPIC";
                        break;
                    case R.id.LEGENDARY:
                        rarity = "LEGENDARY";
                        break;
                }
                setGridView();
            }
        });

        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.MINION:
                        type = "MINION";
                        break;
                    case R.id.SPELL:
                        type = "SPELL";
                        break;
                    case R.id.WEAPON:
                        type = "WEAPON";
                        break;
                    case R.id.HERO:
                        type = "HERO";
                        break;
                }
                setGridView();
            }
        });

        resetBut = getView().findViewById(R.id.reset);
        resetBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.clear();
                setListView(map);
                cardsNum = 0;
                cardsNumText.setText(cardsNum + "/30");
            }
        });
        saveBut = getView().findViewById(R.id.save);
        saveBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardsNum < 30){
                    Toast.makeText(getContext(),"卡牌数量不足30张",Toast.LENGTH_LONG).show();
                    return;
                }
                String name = cardsName.getText().toString();
                sp = getActivity().getSharedPreferences("cards", Activity.MODE_PRIVATE);
                if(sp.getString(name,"").equals("")){
                    String cards = "";
                    for(String key:map.keySet()){
                        cards += key + "," + map.get(key) + ";";
                    }
                    cards = cards.substring(0,cards.length()-1);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(name,cards);
                    editor.apply();
                    builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("提示")
                            .setMessage("保存成功！")
                            .setPositiveButton("查看卡组", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    fragmentManager.beginTransaction()
                                            .hide(fragmentManager.findFragmentById(R.id.fragment1))
                                            .show(fragmentManager.findFragmentById(R.id.fragment2))
                                            .commit();
                                }
                            })
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    cardsName.setText("");
                                }
                            });
                    builder.create().show();
                }
                else{
                    Toast.makeText(getContext(),"卡组名称已存在，保存失败",Toast.LENGTH_LONG).show();
                }

            }
        });

        gridView = getView().findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);

        listView = getView().findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String str = (String) listView.getItemAtPosition(position);
                String id = str.split(",")[0];
                String name = str.split(",")[1];
                String key = id + "," + name;

                if(map.get(key) == 1){
                    map.remove(key);
                }
                else{
                    map.put(key,1);
                }
                setListView(map);
                if(cardsNum > 0){
                    cardsNum -= 1;
                }
                cardsNumText.setText(cardsNum + "/30");
            }
        });

//        sp = getActivity().getSharedPreferences("card", Activity.MODE_PRIVATE);
//        date = sp.getString("date","");
//
//        //当前日期
//        Date now = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String nowDate = sdf.format(now);
//
//        if(date.equals(nowDate)){
        CardManager rateManager = new CardManager(getContext());
        cardsList = rateManager.findAll();
//        MyThread myThread = new MyThread(list,getContext());
//        Thread thread = new Thread(myThread);
//        thread.start();
        gridAdapter = new GridAdapter(getContext(),
                R.layout.list_item_layout,
                cardsList);
        gridView.setAdapter(gridAdapter);
//        }
//        else{
//            getData();
//
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("date",nowDate);
//            editor.apply();
//        }
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

                    //剔除英雄卡牌
                    Iterator<Card> sListIterator = list.iterator();
                    while (sListIterator.hasNext()) {
                        Card card = sListIterator.next();
                        if (card.getType().equals("HERO")) {
                            sListIterator.remove();
                        }
                    }


                    gridAdapter = new GridAdapter(getContext(),R.layout.item_layout,list);
                    gridView.setAdapter(gridAdapter);

                    //存于数据库
//                    CardManager cardManager = new CardManager(getContext());
//                    cardManager.deleteAll();
//                    cardManager.addAll(list);

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

    //gridView事件监听
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long i) {
            Card card = (Card) gridView.getItemAtPosition(position);
            String id = card.getId();
            String name = card.getName();
            String key = id + "," + name;
            if(cardsNum == 30){
                Toast.makeText(getContext(),"卡牌数量已达卡组上限",Toast.LENGTH_LONG).show();
                return;
            }
            if(!map.containsKey(key)){
                map.put(key,1);
            }
            else {
                if(map.get(key) == 2){
                    Toast.makeText(getContext(),"单卡数量不得超过2张",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    map.put(key,2);
                }
            }
            setListView(map);
            cardsNum += 1;
            cardsNumText.setText(cardsNum + "/30");
    }

    private void setListView(HashMap<String,Integer> map){
        strList.clear();
        for(String item:map.keySet()){
            strList.add(item.split(",")[0]+ "," + item.split(",")[1] + "," + map.get(item));
        }

        listAdapter = new ListAdapter(getContext(),R.layout.list_item_layout,strList);
        listView.setAdapter(listAdapter);
    }

    private void setGridView(){
        List<Card> list = new LinkedList<>();
        if(cost == 6){
            for(Card card:cardsList){
                if(card.getCardClass().equals(cardClass) && card.getCost() >= cost
                        && card.getRarity().equals(rarity) && card.getType().equals(type)){
                    list.add(card);
                }
            }
        }
        else {
            for (Card card : cardsList) {
                if (card.getCardClass().equals(cardClass) && card.getCost() == cost
                        && card.getRarity().equals(rarity) && card.getType().equals(type)) {
                    list.add(card);
                }
            }
        }
        gridAdapter = new GridAdapter(getContext(),R.layout.item_layout,list);
        gridView.setAdapter(gridAdapter);
    }
}
