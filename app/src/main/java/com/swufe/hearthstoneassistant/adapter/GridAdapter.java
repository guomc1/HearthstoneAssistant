package com.swufe.hearthstoneassistant.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.swufe.hearthstoneassistant.R;
import com.swufe.hearthstoneassistant.bean.Card;


import java.util.List;


public class GridAdapter extends ArrayAdapter{

    private AssetManager mgr;
    private Typeface tf;

    private ImageLoader imageLoader = ImageLoader.getInstance();

    public GridAdapter(Context context, int resource, List<Card> list){
        super(context,resource,list);
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View itemView = convertView;

        mgr = getContext().getAssets();
        tf = Typeface.createFromAsset(mgr, "fonts/yingbikaishu.ttf");

        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_layout,
                    parent,
                    false);
        }

        Card card = (Card) getItem(position);
        String id = card.getId();
        ImageView imageView = itemView.findViewById(R.id.pic);
        TextView textView = itemView.findViewById(R.id.cardName);
        textView.setTypeface(tf);

        System.out.println(card.getRarity());
        if (card.getRarity().equals("FREE")) {
            textView.setTextColor(Color.BLACK);
        } else if (card.getRarity().equals("COMMON")) {
            textView.setTextColor(Color.parseColor("#2F4F4F"));
        } else if (card.getRarity().equals("RARE")){
            textView.setTextColor(Color.BLUE);
        }
        else if(card.getRarity().equals("EPIC")) {
            textView.setTextColor(Color.parseColor("#800080"));
        }
        else if(card.getRarity().equals("LEGENDARY")) {
            textView.setTextColor(Color.parseColor("#00FF00"));
            textView.setBackgroundResource(R.color.white);
        }


        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.kabei)
                .showImageForEmptyUri(R.mipmap.notfound)
                .showImageOnFail(R.mipmap.notfound)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        imageLoader.displayImage("https://media.services.zam.com/v1/media/byName/hs/cards/enus/"+ id +".png",
                imageView,options);
        textView.setText(card.getName());
        return itemView;
    }
}
