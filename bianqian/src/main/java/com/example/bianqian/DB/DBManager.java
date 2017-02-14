package com.example.bianqian.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.bianqian.Adapter.MainAdapter;
import com.example.bianqian.pojo.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/9/6.
 */
public class DBManager {
     private Context context;
     private noteDB ndb;
    private SQLiteDatabase db;
    private List<Contact> list;
    private MainAdapter mainAdapter;
    public DBManager(Context context)
    {
        ndb = new noteDB(context,"user.db",null,1);
    }
    public void xinzeng(String title,String content,String time)
    {
        db = ndb.getWritableDatabase();
        String s = "insert into user(title,content,time)values('"+ title +"','"+ content +"','"+ time +"')";
        db.execSQL(s);
        db.close();
    }
    public List<Contact> gettitletime()
    {

        list = new ArrayList<>();
        db = ndb.getReadableDatabase();
        String s = "select * from user;";
        Cursor cursor = db.rawQuery(s,null);
        while (cursor.moveToNext())
        {
            Contact contact = new Contact();
            contact.setTitle(cursor.getString(1));
            contact.setTime(cursor.getString(3));
            contact.setContent(cursor.getString(2));
            contact.setId(cursor.getInt(0));
            list.add(contact);
        }
        db.close();
        return list;
    }
    public void update(String title,String content,int id)
    {
        db = ndb.getWritableDatabase();
        String s = "update user set title = '"+title+"',content = '"+content+"'where id="+ id;
        db.execSQL(s);
        db.close();

    }
    public  void delete(int id)
    {
        db = ndb.getWritableDatabase();
        String s = "delete from user where id ="+id;
        db.execSQL(s);
        db.close();
    }





}
