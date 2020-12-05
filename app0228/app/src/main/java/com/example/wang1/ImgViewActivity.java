package com.example.wang1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


// 显示照片的视图
public class ImgViewActivity extends AppCompatActivity {
    private ImageView imageIV ;     // 指向 图像视图的变量
    private ImageView img;          // 指向 显示弹孔的“视图”
    private int touchCount ;        // 点击计数器， 当点击5次后，




    private final int CAMERA_REQUEST = 8888;
    @Override
     // 生成视图
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 选择 显示布局
        setContentView(R.layout.activity_img_view);
        imageIV = findViewById( R.id.imageIV);

        // 调用摄像头
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

        // 点击计数器 赋初值
        touchCount = 0 ;


        // 设置 图片视图 的“触及”事件
        imageIV.setOnTouchListener( new View.OnTouchListener(){
            @Override

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        // 如果是 “向下点击” ， 则记录点击坐标，并处理
                        setpng(event.getX(),event.getY());
                        touchCount++;
                        break;
                }

                // 点击 5 次， 转到 音乐选择视图 中
                if ( touchCount ==5)
                {
                    Intent intert = new Intent( ImgViewActivity.this, SelectActivity.class);
                    startActivity(intert);

                    touchCount = 0 ;            // 计数器清零
                }
                return true;
            }


        });

        ExitManager.getInstance().addActivity(this);
    }




    // 拍照后的处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            // 得到 相片并显示到 ImageIV 视图上
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageIV.setImageBitmap(photo);
        }
    }



    // 点击“相片”的处理， 通过传递过来的点击坐标， 在给定坐标处显示单孔图片
    public void setpng (float X, float Y){

        ImageView img = new ImageView(this);

       img.setImageResource(R.drawable.bullet);

        // group : 图片视图的“父级视图”
        ViewGroup  group =  findViewById(R.id.viewGroup);

        // 把 图片视图的“布局” 设置为与 父级布局一致
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // 得到 单孔视图的偏移参数 ，
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(img.getLayoutParams());
        // 将 单孔视图 的偏移参数中的横坐标移量 设为 点击坐标 的X 轴
        margin.leftMargin=(int)X;
        // 将 单孔视图 的偏移参数中的纵坐偏移量 设为 点击坐标 的Y 轴
        margin.topMargin=(int)Y;

        // 重新生成新的 弹孔坐标 偏移参数
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        // 将新的 偏移参数 赋予 弹孔视图
        img.setLayoutParams(layoutParams);

        // 在 父级视图 中加入 弹孔视图
        group.addView(img);
        // 显示 父级视图
        setContentView(group);


    }
}