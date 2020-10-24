package com.swufe.hearthstoneassistant.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.swufe.hearthstoneassistant.bean.Card;

import java.util.ArrayList;
import java.util.List;

public class CardManager {
    private DBHelper dbHelper;
    private String tbname;

    public CardManager(Context context){
        dbHelper = new DBHelper(context);
        tbname = DBHelper.TB_NAME;
    }

    public void addAll(List<Card> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(Card card:list) {
            ContentValues values = new ContentValues();
            values.put("name", card.getName());
            values.put("cardId", card.getId());
            values.put("flavor", card.getFlavor());
            values.put("artist", card.getArtist());
            values.put("cardClass", card.getCardClass());
            values.put("faction", card.getFaction());
            values.put("rarity", card.getRarity());
            values.put("type", card.getType());
            values.put("cardSet", card.getSet());
            values.put("durability", card.getDurability());
            values.put("dbfId", card.getDbfId());
            values.put("attack", card.getAttack());
            values.put("cost", card.getCost());
            values.put("health", card.getHealth());
            values.put("cardText", card.getText());
            db.insert(tbname, null, values);
        }
        db.close();
    }

    public List<Card> findAll(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(tbname,null,
                null,null,
                null,null,null);

        List<Card> list = new ArrayList<>();

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                String id = cursor.getString(cursor.getColumnIndex("cardId"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String flavor = cursor.getString(cursor.getColumnIndex("flavor"));
                String artist = cursor.getString(cursor.getColumnIndex("artist"));
                String cardClass = cursor.getString(cursor.getColumnIndex("cardClass"));
                String faction = cursor.getString(cursor.getColumnIndex("faction"));
                String rarity = cursor.getString(cursor.getColumnIndex("rarity"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String cardSet = cursor.getString(cursor.getColumnIndex("cardSet"));
                String durability = cursor.getString(cursor.getColumnIndex("durability"));
                String cardText = cursor.getString(cursor.getColumnIndex("cardText"));
                int dbfId = cursor.getInt(cursor.getColumnIndex("dbfId"));
                int attack = cursor.getInt(cursor.getColumnIndex("attack"));
                int cost = cursor.getInt(cursor.getColumnIndex("cost"));
                int health = cursor.getInt(cursor.getColumnIndex("health"));

                Card card = new Card();
                card.setId(id);
                card.setName(name);
                card.setFlavor(flavor);
                card.setArtist(artist);
                card.setCardClass(cardClass);
                card.setFaction(faction);
                card.setRarity(rarity);
                card.setType(type);
                card.setSet(cardSet);
                card.setDurability(durability);
                card.setText(cardText);
                card.setDbfId(dbfId);
                card.setAttack(attack);
                card.setCost(cost);
                card.setHealth(health);

                list.add(card);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return list;
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(tbname,null,null);
    }
}
