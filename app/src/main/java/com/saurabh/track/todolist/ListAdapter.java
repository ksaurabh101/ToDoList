package com.saurabh.track.todolist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by saurabh on 29/5/17.
 */

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<Item> list;
    public ListAdapter(Context context, List<Item> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        if(list != null){
            return list.size();
        }else {
            return 0;
        }
    }

    @Override
    public Item getItem(int position) {
        if(getCount() > position){
            return list.get(position);
        }else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            Item item = getItem(position);
            GetRow row = new GetRow(context, position) {
                @Override
                public void refereshAdapter(int index, int id, boolean status) {
                    try {
                        list.get(index).setStatus(status);
                        notifyDataSetChanged();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            return row.getView(item);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
