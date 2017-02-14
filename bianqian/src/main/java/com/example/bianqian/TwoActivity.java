package com.example.bianqian;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.List;

/**
 * Created by lenovo on 2016/9/7.
 */
public class TwoActivity extends AppCompatActivity {
    TextView tit;
    TextView time;
    EditText con,et2,et1;
    TextView tv1;
    LayoutInflater layoutInflater;
    DBManager dbManager;
    String title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinjian);
        tit = (TextView) findViewById(R.id.xin);
        time = (TextView) findViewById(R.id.time);
        con = (EditText) findViewById(R.id.et1);
        final Intent intent = getIntent();
        tit.setText(intent.getStringExtra("title"));
        time.setText(intent.getStringExtra("time"));
        con.setText(intent.getStringExtra("content"));
        et1 = (EditText) findViewById(R.id.et1);
        tv1 = (TextView) findViewById(R.id.tv1);
        layoutInflater = getLayoutInflater();
        tv1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                View view1 =  layoutInflater.inflate(R.layout.activity_shurukuang,null);
                et2 = (EditText) view1.findViewById(R.id.shuru);

                et2.setText(intent.getStringExtra("title"));
                new AlertDialog.Builder(TwoActivity.this).setTitle("编辑标题").setView(view1)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                title= et2.getText().toString();

                                if(TextUtils.isEmpty(title))
                                {
                                    Toast.makeText(TwoActivity.this, "标题为空", Toast.LENGTH_SHORT).show();

                                    return;
                                }
                                tit.setText(title);

                            }
                        }).setNegativeButton("取消",null).show();
            }
        });


        tv1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(TwoActivity.this,YuYinActivity.class);
                intent.putExtra("yuyin",et1.getText().toString());
                startActivity(intent);
                return true;
            }
        });



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String content = et1.getText().toString();
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {

            if(TextUtils.isEmpty(content)){
            Toast.makeText(TwoActivity.this, "内容为空", Toast.LENGTH_SHORT).show();
            return super.onKeyDown(keyCode, event);
            }
            Intent intent = getIntent();
//            View view1 =  layoutInflater.inflate(R.layout.activity_shurukuang,null);
//            et2 = (EditText) view1.findViewById(R.id.shuru);
            String titlel= tit.getText().toString();


            int id = intent.getIntExtra("id",0);
            DBManager dbManager = new DBManager(TwoActivity.this);
            dbManager.update(titlel,content,id);
            intent.putExtra("updata",true);
            setResult(RESULT_OK,intent);
            return super.onKeyDown(keyCode, event);
        }

        return super.onKeyDown(keyCode, event);






    }


}
