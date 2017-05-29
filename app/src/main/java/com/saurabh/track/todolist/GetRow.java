package com.saurabh.track.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by saurabh on 29/5/17.
 */

public abstract class GetRow {
    private Context context;
    private DatabaseHandler db;
    private LayoutInflater inflater;
    private int index = 0;
    public GetRow(Context context, int index){
        this.context = context;
        this.index = index;
        db = new DatabaseHandler(context);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public View getView(Item item){
        try{
            final View view = inflater.inflate(R.layout.row, null);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            final CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox);

            final int id = item.getId();
            String text = item.getText();
            final boolean status = item.isStatus();

            textView.setText(text);
            if(status){
                //give white color to background if active
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }else{
                view.setBackgroundColor(Color.parseColor("#cccccc"));
            }

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked()){
                        checkBox.setChecked(false);
                        //make that to do note active
                        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        db.updateStatus(id, true);
                        refereshAdapter(index, id, true);
                    }else{
                        //make in active..
                        checkBox.setChecked(true);
                        view.setBackgroundColor(Color.parseColor("#cccccc"));
                        db.updateStatus(id, false);
                        refereshAdapter(index, id, false);
                    }
                }
            });

            return view;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public abstract void refereshAdapter(int index, int id, boolean status);
}
