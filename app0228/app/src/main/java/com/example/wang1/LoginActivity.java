package com.example.wang1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LoginActivity extends AppCompatActivity {
    Button btn_login , btn_img;
    RadioGroup rg;
    RadioButton fc , fp ;
    ImageView img ;
    EditText name;

    private final int CAMERA_REQUEST = 8888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ExitManager.getInstance().addActivity(this);

        name = findViewById(R.id.user_input);
        btn_img = findViewById(R.id.btn_importImg);
        btn_login = findViewById(R.id.btn_login);
        img = findViewById(R.id.logImg);
        rg = findViewById(R.id.RadioGroup);
        fc = findViewById(R.id.fromCamaro);
        fp = findViewById(R.id.fromPhoto);

        btn_login .setVisibility(View.INVISIBLE);   // 开始时， login 按钮禁用
        fc.setChecked(true);                        // 开始时，选中 从相机

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( name.getText().toString().length() ==0){
                    AlertDialog textTips = new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Tips:")
                            .setMessage("Name can't empty ")
                            .create();
                    textTips.show();
                }
                else {
                    Intent intent = new Intent( LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            }
        });


        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fc.isChecked() ){
                    // 从相机
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

                }
                else{
//                    // 从图片库
//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
////                    startActivityForResult(Intent.createChooser(intent, "Select Contact Image"),1);
//                    startActivityForResult(intent, 12345);
                    boolean isKitKatO = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
                    Intent getAlbum;
                    if (isKitKatO) {
                        getAlbum = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    } else {
                        getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                    }
                    getAlbum.setType("image/*");

                    startActivityForResult(getAlbum, 12345);
                }
            }
        });

    }

    // 拍照后的处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            // 得到 相片并显示到 ImageIV 视图上
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(photo);
            btn_login.setVisibility(View.VISIBLE);
        }
        else{
            if(resultCode==RESULT_OK)
            {
                // 得到图片库里的照片
                img.setImageURI(data.getData());
                btn_login.setVisibility(View.VISIBLE);
           }
        }
    }

}