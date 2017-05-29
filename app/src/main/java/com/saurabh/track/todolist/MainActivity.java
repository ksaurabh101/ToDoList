package com.saurabh.track.todolist;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static ListAdapter adapter;
    private Context context;
    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        db = new DatabaseHandler(context);


        try {
            List<Item> list = new ArrayList<>();
            //get data from database...
            list = db.getList();
            if (list != null) {
                ListView listView = (ListView) findViewById(R.id.listview);
                adapter = new ListAdapter(context, list);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            Button add = (Button)findViewById(R.id.button);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
