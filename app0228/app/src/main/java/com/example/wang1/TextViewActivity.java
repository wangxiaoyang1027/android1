package com.example.wang1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TextViewActivity extends AppCompatActivity {
    private TextView textview;
    private TextView textview1 ;
    private Button mBtnRet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text_view);
        ExitManager.getInstance().addActivity(this);
        textview = findViewById( R.id.textView);





        mBtnRet = findViewById(R.id.btn_return);
        mBtnRet.setOnClickListener( new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                Intent intent = new Intent( TextViewActivity.this, MainActivity.class);

                startActivity(intent);
            }



        });







    }
}