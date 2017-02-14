package com.example.bianqian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bianqian.R;
import com.example.bianqian.pojo.Contact;

import java.util.List;

/**
 * Created by lenovo on 2016/9/6.
 */
public class MainAdapter extends BaseAdapter {
    private List<Contact> list;
    private Context context;
    LayoutInflater layoutInflater;
    public MainAdapter(List<Contact> list, Context context)
    {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            VH vh = null;
        if(view == null)
        {
            vh = new VH();
            view =  layoutInflater.inflate(R.layout.activity_main_tu,null);
            vh.tit1 = (TextView) view.findViewById(R.id.tit1);
            vh.time1 = (TextView) view.findViewById(R.id.time1);
            view.setTag(vh);
        }else {
                vh = (VH) view.getTag();
        }
        vh.tit1.setText(list.get(i).getTitle());
        vh.time1.setText(list.get(i).getTime());
        return view;
    }
    public class VH{
        TextView tit1,time1;
    }
}
