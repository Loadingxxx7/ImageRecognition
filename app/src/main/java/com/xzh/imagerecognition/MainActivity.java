package com.xzh.imagerecognition;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button takePhotoBtn,analysisBtn,pieBtn;
    private TextView textView;
    private File outputImage;
    private Uri imageUri;
    private final int takePhoto = 1;
    String accessToken;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        takePhotoBtn = findViewById(R.id.takePhotoBtn);
        analysisBtn = findViewById(R.id.analysisBtn);
        pieBtn = findViewById(R.id.pieBtn);
        textView = findViewById(R.id.textView);



        //动态获取相机权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1);
        }

        acThread ac = new acThread();
        ac.start();

        pieBtn.setEnabled(false);
        pieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,PieActivity.class);
                View statusView =findViewById(R.id.pieBtn);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(MainActivity.this,statusView,"flag");
                startActivity(intent1,options.toBundle());
            }
        });

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建File对象，用来存储拍照后的照片
                outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                if (outputImage.exists()){
                    outputImage.delete();
                }
                try {
                    outputImage.createNewFile();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        imageUri = FileProvider.getUriForFile(MainActivity.this,"com.xzh.imagerecognition",outputImage);
                    }else {
                        imageUri = Uri.fromFile(outputImage);
                    }
                    //启动相机程序
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,takePhoto);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        analysisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("识别中，请稍等...");
                workThread workThread = new workThread();
                workThread.start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            return;
        }else {
            switch (requestCode){
                case takePhoto:
                    if (resultCode == Activity.RESULT_OK){
                        //将拍摄的照片显示出来
                        try {
                            final Bitmap bitmap = BitmapFactory.decodeStream(getBaseContext().getContentResolver().openInputStream(imageUri));
                            imageView.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
            }
        }
    }

    class workThread extends Thread{
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/classify/ingredient";

            try {
                String filePath = "/sdcard/Android/data/com.xzh.imagerecognition/cache/output_image.jpg";
                byte[] imgData = FileUtil.readFileByBytes(filePath);
                String imgStr = Base64Util.encode(imgData);
                String imgParam = URLEncoder.encode(imgStr,"UTF-8");

                String param = "image="+imgParam;

//                String accessToken = "24.d2241e6acac8de5028e68dd8188f0364.2592000.1595609225.282335-20576638";

                String result = HttpUtil.post(url,accessToken,param);

                Gson gson = new Gson();
                DataBean dataBean = gson.fromJson(result,DataBean.class);


                Log.d("ssssssssssssss",result);

                textView.setText("识别结果为:"+dataBean.getResult().get(0).getName()
                        +"\n识别准确率为:"+(Double.valueOf(dataBean.getResult().get(0).getScore()))*100+"%"
                        +"\n \n其他识别结果为:\n\n"+dataBean.getResult().get(1).getName()
                        +"\n识别准确率为:"+(Double.valueOf(dataBean.getResult().get(1).getScore()))*100+"%\n\n"
                        +dataBean.getResult().get(2).getName()
                        +"\n识别准确率为:"+(Double.valueOf(dataBean.getResult().get(2).getScore()))*100+"%\n\n"
                        +dataBean.getResult().get(3).getName()
                        +"\n识别准确率为:"+(Double.valueOf(dataBean.getResult().get(3).getScore()))*100+"%\n\n"
                        +dataBean.getResult().get(4).getName()
                        +"\n识别准确率为:"+(Double.valueOf(dataBean.getResult().get(4).getScore()))*100+"%\n");

                Intent intent = new Intent();
                intent.setClass(MainActivity.this,PieActivity.class);
                intent.putExtra("name1",dataBean.getResult().get(0).getName());
                intent.putExtra("name2",dataBean.getResult().get(1).getName());
                intent.putExtra("name3",dataBean.getResult().get(2).getName());
                intent.putExtra("name4",dataBean.getResult().get(3).getName());
                intent.putExtra("name5",dataBean.getResult().get(4).getName());

                intent.putExtra("score1",dataBean.getResult().get(0).getScore());
                intent.putExtra("score2",dataBean.getResult().get(1).getScore());
                intent.putExtra("score3",dataBean.getResult().get(2).getScore());
                intent.putExtra("score4",dataBean.getResult().get(3).getScore());
                intent.putExtra("score5",dataBean.getResult().get(4).getScore());

                startActivity(intent);

                pieBtn.setEnabled(true);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取百度ai鉴权密匙Access_token
    class acThread extends Thread{
        @Override
        public void run() {
            String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=yw98P5iXauiABjcin5yumoGh&client_secret=4ejxa9jkn1eMbysu2Fz0ERrdWa4o2RkA";

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();

                Gson gson = new Gson();
                AcBean acBean = gson.fromJson(responseData,AcBean.class);

                accessToken = acBean.getAccess_token();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
