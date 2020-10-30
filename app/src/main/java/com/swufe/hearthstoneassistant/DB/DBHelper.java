package com.swufe.hearthstoneassistant.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_NAME = "hearthstone.db";
    public static final String TB_NAME = "tb_cards";

    public DBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TB_NAME +
                "(id integer primary key autoincrement," +
                "cardId text," +
                "name text," +
                "flavor text," +
                "artist text," +
                "cardClass text," +
                "faction text," +
                "rarity text," +
                "type text," +
                "cardSet text," +
                "durability text," +
                "dbfId int," +
                "attack int," +
                "cost int," +
                "health int," +
                "pic bolb not null," +
                "cardText text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
