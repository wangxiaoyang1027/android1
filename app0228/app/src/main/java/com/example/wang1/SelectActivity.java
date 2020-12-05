package com.example.wang1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity {
    Button btn_Sock , btn_NoSock ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        btn_Sock  = findViewById(R.id.Btn_SelectRock);
        btn_NoSock = findViewById(R.id.Btn_SelectNoRock);

        btn_Sock.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // 点击 “确认“ 按钮
                Intent intert = new Intent( SelectActivity.this, MusicListActivity.class);
                startActivity(intert);
            }
        });


        btn_NoSock.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // 点击 ”No“ ， 返回主界面
                Intent intert1 = new Intent( SelectActivity.this, MainActivity.class);
                startActivity(intert1);

            }
        });




        ExitManager.getInstance().addActivity(this);
    }
}