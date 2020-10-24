package com.swufe.hearthstoneassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.swufe.hearthstoneassistant.R;
import java.util.List;

public class ListAdapter extends ArrayAdapter {

    public ListAdapter(Context context, int resource, List<String> list){
        super(context,resource,list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View itemView = convertView;

        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_layout,
                    parent,
                    false);
        }

        String str = (String) getItem(position);
        TextView title = itemView.findViewById(R.id.list_item);
        TextView num = itemView.findViewById(R.id.num);

        title.setText(str.split(",")[0]);
        num.setText(str.split(",")[1] + "✖");
        return itemView;
    }
}
