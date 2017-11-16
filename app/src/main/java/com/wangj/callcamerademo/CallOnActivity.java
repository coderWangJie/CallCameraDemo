package com.wangj.callcamerademo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

/**
 * 在Activity中调起系统相机
 */
public class CallOnActivity extends AppCompatActivity {
    private final int REQUEST_CAMERA = 123;

    private Uri imageUri;

    private TextView tvMethod;
    private TextView tvFileUri; // 相机返回的Uri
    private ImageView imgThumbnail;  // 缩略图显示
    private ImageView imgOriginal;  // 原图显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_on_activity);
        setTitle("通过Activity调用相机");

        initView();
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//    }

    private void initView() {
        tvMethod = (TextView) findViewById(R.id.tv_method);
        tvFileUri = (TextView) findViewById(R.id.tv_fileUri);
        imgThumbnail = (ImageView) findViewById(R.id.img_1);
        imgOriginal = (ImageView) findViewById(R.id.img_2);

        findViewById(R.id.btn_camera1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMethod.setText("执行方法(1)");

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });

        findViewById(R.id.btn_camera2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMethod.setText("执行方法(2)");

                String filePath = Environment.getExternalStorageDirectory() + File.separator
                        + Environment.DIRECTORY_PICTURES + File.separator;
                // 系统相册默认位置
//                String filePath = Environment.getExternalStorageDirectory() + File.separator
//                        + Environment.DIRECTORY_DCIM + File.separator
//                        + "Camera" + File.separator;
                String fileName = "IMG_" + DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                imageUri = Uri.fromFile(new File(filePath + fileName));

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
    }

    private Bitmap getBitmapByUri(Uri picuUi) {
        Bitmap bitmap = null;
        try {
            InputStream in = getContentResolver().openInputStream(picuUi);
            bitmap = BitmapFactory.decodeStream(in);
            Log.e("WangJ", "从流中获取的原始大小： " + bitmap.getWidth() * bitmap.getHeight());
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 800, 1280);
            Log.e("WangJ", "压缩后大小： " + bitmap.getWidth() * bitmap.getHeight());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CAMERA == requestCode
                && RESULT_OK == resultCode) {
            if (data != null) {
                if (data.getExtras() != null) {
                    Log.e("WangJ", "result-Extras: " + data.getExtras());
                    imgThumbnail.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
                } else {
                    Log.e("WangJ", "无intent extra返回");
                }

                Uri uri;
                if ((uri = data.getData()) != null) {
                    Log.e("WangJ", "result-Uri: " + uri.toString());
                    tvFileUri.setText(uri.toString());
                    imgOriginal.setImageBitmap(getBitmapByUri(uri));

                    Log.e("WangJ", "按返回Uri刷新图库");
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(uri);
                    sendBroadcast(intent);
                } else {
                    Log.e("WangJ", "无Uri返回");
                }
            } else {
                Log.e("WangJ", "result为空！");
                imgOriginal.setImageBitmap(getBitmapByUri(imageUri));

                Log.e("WangJ", "按自定义Uri刷新图库");
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(imageUri);
                sendBroadcast(intent);
            }
        }
    }

}
