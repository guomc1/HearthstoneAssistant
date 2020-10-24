package com.swufe.hearthstoneassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.swufe.hearthstoneassistant.R;
import com.swufe.hearthstoneassistant.bean.Card;

import java.util.List;


public class GridAdapter extends ArrayAdapter{


    public GridAdapter(Context context, int resource, List<Card> list){
        super(context,resource,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View itemView = convertView;

        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_layout,
                    parent,
                    false);
        }

        Card card = (Card) getItem(position);
        TextView title = itemView.findViewById(R.id.name);
        TextView detail = itemView.findViewById(R.id.text);

        title.setText(card.getName());
        detail.setText(card.getText());

        return itemView;
    }


}
