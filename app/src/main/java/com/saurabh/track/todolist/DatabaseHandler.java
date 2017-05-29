package com.saurabh.track.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabh on 29/5/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todolist";
    private static final int DATABASE_VERSION = 1;
    private static Context context;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(ToDoList.CREATE_TODO_TABLE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addItem(Item item){
        try {
            SQLiteDatabase write = this.getWritableDatabase();
            ToDoList table = new ToDoList();
            table.addItem(write, item);
            if(write.isOpen()){
                write.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Item> getList(){
        try{
            SQLiteDatabase read  = this.getReadableDatabase();
            ToDoList table = new ToDoList();
            List<Item> list = table.getAllList(read);
            if(read.isOpen()){
                read.close();
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void updateStatus(int id, boolean status){
        try{
            SQLiteDatabase write = this.getWritableDatabase();
            ToDoList table = new ToDoList();
            table.updateStatus(write, id, status);
            if(write.isOpen()){
                write.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public class ToDoList {
        public static final String TODO_TABLE = "table";
        public static final String ID = "id";
        public static final String TEXT = "text";
        public static final String STATUS = "status";
        public static final String TIMESTAMP = "timestamp";

        public static final String CREATE_TODO_TABLE = "create table if not exists \'" + TODO_TABLE + "\'"
                + "("
                + ID + " integer primary key AUTOINCREMENT, "
                + TEXT + " text, "
                + STATUS + " integer, "
                + TIMESTAMP + " text"
                + ")";

        public void addItem(SQLiteDatabase write, Item item){
            try{
                String text = item.getText();

                ContentValues values = new ContentValues();
                values.put(TEXT, text);
                values.put(STATUS, 1);
                values.put(TIMESTAMP, System.currentTimeMillis());

                write.insertOrThrow(TODO_TABLE, null, values);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public List<Item> getAllList(SQLiteDatabase read){
            try{
                String query = "select * from " + TODO_TABLE + " order by " + TIMESTAMP + " desc";
                Cursor cursor = read.rawQuery(query, null);
                List<Item> list = null;
                if(cursor != null && cursor.moveToFirst()){
                    list = new ArrayList<>();
                    do{
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
                        String text = cursor.getString(cursor.getColumnIndexOrThrow(TEXT));
                        int statusValue = cursor.getInt(cursor.getColumnIndexOrThrow(STATUS));

                        Item item = new Item();
                        item.setText(text);
                        item.setId(id);
                        item.setStatus(statusValue == 1 ? true : false);

                        list.add(item);
                    }while (cursor.moveToNext());
                }
                if(cursor != null && !cursor.isClosed()){
                    cursor.close();
                }
                return list;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        public void updateStatus(SQLiteDatabase write, int id, boolean status){
            try{
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status ? 1 : 0);
                write.update(TODO_TABLE, contentValues, ID + " = ?", new String[]{String.valueOf(id)});
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
