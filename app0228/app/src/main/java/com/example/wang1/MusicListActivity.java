package com.example.wang1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.wang1.util.DisplayInfo;
import com.example.wang1.util.UserList;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.Math.abs;


public class MusicListActivity extends AppCompatActivity {
    private Button mBtnRet ;
    private ListView userlist;
    public String url= "https://reqres.in/api/users?page=";
    UserList elist = new UserList();

    private float value_x , value_y , value_z ;


    // 传感器
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TestSensorListener mSensorListener;

    // 显示哪些用户的标志
    int page = 1 ;




    public List<DisplayInfo> displaylist = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        ExitManager.getInstance().addActivity(this);
        userlist = findViewById(R.id.userlist);

        // 初始化传感器
        mSensorListener = new TestSensorListener();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // 设置为加速度传感器
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 注册 监听器
        mSensorManager.registerListener(mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);


        value_x = value_y=value_z = 0 ;

//        userlist.setAdapter(new MyListAdapter(MusicListActivity.this ));




        // 返回键处理
        mBtnRet = findViewById(R.id.btn_return);
        mBtnRet.setOnClickListener( new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                Intent intent = new Intent(MusicListActivity.this, MainActivity.class);


                startActivity( intent );


            }
        });

        try {
            run( page );
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        // 注册传感器监听函数
        mSensorManager.registerListener(mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 注销监听函数
        mSensorManager.unregisterListener(mSensorListener);
    }

    void run( int page ) throws IOException {
        String realurl ;

        OkHttpClient client = new OkHttpClient();
        realurl = url +page;
        Request request = new Request.Builder()
                .url(realurl)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {

                    final String myResponse = response.body().string();
                    MusicListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Gson netdata  = new Gson();
                            elist = netdata.fromJson(myResponse, UserList.class);
                            DisplayInfo tmp;

                            displaylist.clear();
                            for( int i = 0 ; i< elist.data.size();i++){
                                tmp= new DisplayInfo();

                                tmp.img = (elist.data.get(i)).avatar;
                                tmp.first_name  = elist.data.get(i).first_name;
                                tmp.last_name = elist.data.get(i).last_name;
                                tmp.email = elist.data.get(i).email;

                                displaylist.add( tmp);

                            }

                            if( displaylist.size() > 0 )
                                userlist.setAdapter(new MyListAdapter(MusicListActivity.this ));

                        }
                    });
                }


            }
        });

    }




    class TestSensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 读取加速度传感器数值，values数组0,1,2分别对应x,y,z轴的加速度
            // 如果新值 和 保存值 相差 20 ， 说明晃动了手机， 需要重新调用 run() , 函数重新加载网络数据
            if ( ( abs(value_x - event.values[0]) > 20 ) || ( abs(value_y - event.values[1]) > 20 ) || ( abs(value_z - event.values[2]) > 20 ))
            {
                // 首先，关闭 加速传感器的监听器，
                mSensorManager.unregisterListener(mSensorListener);

                value_x = event.values[0];
                value_y = event.values[1];
                value_z = event.values[2];

                try{
                    run( page );
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                // 指定加载哪些用户数据
                page = page == 1 ? 2 : 1 ;

                // 重新打开 加速传感器的监听
                mSensorManager.registerListener(mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }








}


