package com.example.bianqian;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bianqian.Adapter.MainAdapter;
import com.example.bianqian.DB.DBManager;
import com.example.bianqian.pojo.Contact;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    final static int yi = 1;
    final static int er = 2;
    Button b1,b2;
    ListView lv1;
    List<Contact> list;
    DBManager dBManager;
    MainAdapter mainAdapter;
    boolean b = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OneActivity.class);
                startActivityForResult(intent,yi);
                Toast.makeText(MainActivity.this, "编辑完按返回键直接保存", Toast.LENGTH_LONG).show();

            }
        });
        b2 = (Button) findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,GuanYu.class);
                startActivity(intent);
            }
        });

        lv1 = (ListView) findViewById(R.id.lv);
        dBManager = new DBManager(this);
        list = dBManager.gettitletime();

        mainAdapter = new MainAdapter(list,this);
        lv1.setAdapter(mainAdapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,TwoActivity.class);
                intent.putExtra("title",list.get(i).getTitle());
                intent.putExtra("content",list.get(i).getContent());
                intent.putExtra("time",list.get(i).getTime());
                intent.putExtra("id",list.get(i).getId());
                Log.e("llllllllll",list.get(i).getId()+"");
                startActivityForResult(intent,er);
                Toast.makeText(MainActivity.this, "长按标题可朗读", Toast.LENGTH_SHORT).show();
            }
        });

        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                new AlertDialog.Builder(MainActivity.this).setTitle(list.get(i).getTitle()).setItems
                        (new String[]{"删除"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int pos) {
                                if (pos==0) {
                                    dBManager = new DBManager(MainActivity.this);
                                    dBManager.delete(list.get(i).getId());
                                    list.clear();
                                    List<Contact> list01 = dBManager.gettitletime();
                                    for (Contact c :
                                            list01) {
                                        list.add(c);
                                    }
                                    mainAdapter.notifyDataSetChanged();
                                }
//                                }else if(pos==1)
//                                {
//                                    Toast.makeText(MainActivity.this, "该功能还未实现，敬请期待", Toast.LENGTH_SHORT).show();
//                                }else if(pos==2)
//                                {
//                                    Toast.makeText(MainActivity.this, "该功能还未实现，敬请期待", Toast.LENGTH_SHORT).show();
//                                }else if(pos==3)
//                                {
//                                    Toast.makeText(MainActivity.this, "该功能还未实现，敬请期待", Toast.LENGTH_SHORT).show();
//                                }
                            }
                        })
                        .setNegativeButton("确定",null).show();
                return true;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK)
        {
            return;
        }
        if(requestCode == yi){

            boolean b = data.getBooleanExtra("refresh",false);
            if(b)
            {
                dBManager = new DBManager(this);
                list.clear();
                List<Contact> list01 = dBManager.gettitletime();
                for (Contact c:
                     list01) {
                    list.add(c);
                }
                mainAdapter.notifyDataSetChanged();
            }

        }if(requestCode == er)
        {
            boolean d = data.getBooleanExtra("updata",false);
            if(d)
            {
                dBManager = new DBManager(this);
                list.clear();
                List<Contact> list01 = dBManager.gettitletime();
                for (Contact c:
                        list01) {
                    list.add(c);
                }
                mainAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(b)
            {
                b = false;
                Toast.makeText(MainActivity.this, "再按一下关闭", Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        b = true;
                    }
                },3000);


            }else {
                MainActivity.this.finish();

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
