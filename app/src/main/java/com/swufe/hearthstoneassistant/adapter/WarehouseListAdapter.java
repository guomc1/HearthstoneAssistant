package com.swufe.hearthstoneassistant.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.swufe.hearthstoneassistant.R;

import java.util.List;

public class WarehouseListAdapter extends ArrayAdapter{

    private AssetManager mgr;
    private Typeface tf;

    public WarehouseListAdapter(Context context, int resource, List<String> list){
        super(context,resource,list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;

        mgr = getContext().getAssets();
        tf = Typeface.createFromAsset(mgr, "fonts/yingbikaishu.ttf");

        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.wh_list_item_layout,
                    parent,
                    false);
        }

        String str = (String)getItem(position);

        TextView textView = itemView.findViewById(R.id.wh_list_item);
        textView.setTypeface(tf);

        textView.setText(str);

        return itemView;
    }
}
