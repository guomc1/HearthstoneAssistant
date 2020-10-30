package com.swufe.hearthstoneassistant.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.swufe.hearthstoneassistant.R;
import com.swufe.hearthstoneassistant.bean.Card;


import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GridAdapter extends ArrayAdapter{


    public GridAdapter(Context context, int resource, List<Card> list){
        super(context,resource,list);
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent){
        View itemView = convertView;

        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_layout,
                    parent,
                    false);
        }

        Card card = (Card) getItem(position);
        ImageView imageView = itemView.findViewById(R.id.pic);
        TextView textView = itemView.findViewById(R.id.cardName);

        textView.setText(card.getName());
        imageView.setImageResource(R.drawable.ic_launcher_background);
        return itemView;
    }

    private Drawable getDrawable(byte[] pic){
        Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length, null);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        Drawable drawable = bitmapDrawable;
        return drawable;
    }
}
