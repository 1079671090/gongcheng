package com.example.bianqian;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bianqian.DB.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2016/9/6.
 */
public class OneActivity extends AppCompatActivity {
    EditText et1,et2;
    TextView tv1,time,tit;
    LayoutInflater layoutInflater;
    String content;
    String title;
    String notdate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinjian);
        et1 = (EditText) findViewById(R.id.et1);
        tv1 = (TextView) findViewById(R.id.tv1);
        time = (TextView) findViewById(R.id.time);
        tit = (TextView) findViewById(R.id.xin);
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
        notdate = sdf.format(data);
        time.setText(notdate);
        layoutInflater = getLayoutInflater();
        tv1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
               View view1 =  layoutInflater.inflate(R.layout.activity_shurukuang,null);
                et2 = (EditText) view1.findViewById(R.id.shuru);
                new AlertDialog.Builder(OneActivity.this).setTitle("编辑标题").setView(view1)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        title= et2.getText().toString();
                        if(TextUtils.isEmpty(title))
                        {
                            Toast.makeText(OneActivity.this, "标题为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        tit.setText(title);

                    }
                }).setNegativeButton("取消",null).show();
            }
        });






    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            content = et1.getText().toString();
            if(TextUtils.isEmpty(content)) {
                Toast.makeText(OneActivity.this, "内容为空,保存失败", Toast.LENGTH_SHORT).show();
                return super.onKeyDown(keyCode, event);
            }
            DBManager dBManager =  new DBManager(OneActivity.this);
            if(title==null)
            {
                title = "无标题";

            }
            dBManager.xinzeng(title,content,notdate);
            Intent intent = new Intent();
            intent.putExtra("refresh",true);
            setResult(RESULT_OK,intent);
            return super.onKeyDown(keyCode, event);

        }
        return super.onKeyDown(keyCode, event);
    }
}
