package com.example.wang1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;





public class MainActivity extends AppCompatActivity {
    private Button mBtnTextView;
    private Button mBtnImgView;
    private Button mBtnExit;
    private ExitManager exitmanager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ExitManager.getInstance().addActivity(this);


        mBtnTextView = findViewById(R.id.btn_textview);
        mBtnTextView.setOnClickListener( new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                Intent intent = new Intent(MainActivity.this, TextViewActivity.class);
                startActivity(intent);



            }



        });

        mBtnImgView = findViewById(R.id.btn_Start);
        mBtnImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intert = new Intent( MainActivity.this, ImgViewActivity.class);
                startActivity(intert);
            }
        });


        mBtnExit = findViewById(R.id.btn_Exit);
        mBtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                killAppProcess();
                ExitManager.getInstance().exit();
            }
        });
    }


    public void killAppProcess()
    {
//注意：不能先杀掉主进程，否则逻辑代码无法继续执行，需先杀掉相关进程最后杀掉主进程
        ActivityManager mActivityManager = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> mList = mActivityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : mList)
        {
            if (runningAppProcessInfo.pid != android.os.Process.myPid())
            {
                android.os.Process.killProcess(runningAppProcessInfo.pid);
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}