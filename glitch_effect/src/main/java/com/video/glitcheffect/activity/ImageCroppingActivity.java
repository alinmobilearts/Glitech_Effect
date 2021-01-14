package com.video.glitcheffect.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.video.glitcheffect.R;
import com.video.glitcheffect.utils.Utils;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;

public class ImageCroppingActivity extends AppCompatActivity {
    int a = 1;
    Bitmap bitmap1;
    CropImageView cropImageView;
    ImageView ivBtnBack;
    ImageView ivBtnNext;
    String photoPath;
    ImageView rotateimage;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_crop_image);


        this.cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        this.ivBtnNext = (ImageView) findViewById(R.id.ivBtnNext);
        this.ivBtnBack = (ImageView) findViewById(R.id.ivBtnBack);
        this.rotateimage = (ImageView) findViewById(R.id.rotateimage);
        this.cropImageView.setAspectRatio(1, 1);
        this.cropImageView.setFixedAspectRatio(true);
        this.photoPath = Utils.path;
        this.cropImageView.setImageBitmap(BitmapFactory.decodeFile(this.photoPath));
        this.bitmap1 = BitmapFactory.decodeFile(this.photoPath);
        this.ivBtnNext.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Utils.transfer = 1;
                if (ImageCroppingActivity.this.a == 0) {
                    Bitmap croppedImage = ImageCroppingActivity.this.cropImageView.getCroppedImage();
                    String file = Environment.getExternalStorageDirectory().toString();
                    StringBuilder sb = new StringBuilder();
                    sb.append(file);
                    sb.append("/temp/");
                    File file2 = new File(sb.toString());
                    file2.mkdir();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(Long.toString(System.currentTimeMillis()));
                    sb2.append(".jpg");
                    File file3 = new File(file2, sb2.toString());
                    Log.e("Path1::", file3.getAbsolutePath());
                    if (file3.exists()) {
                        file3.delete();
                    }
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file3);
                        croppedImage.compress(CompressFormat.JPEG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(ImageCroppingActivity.this, MainActivity.class);
                    Utils.path2 = file3.getAbsolutePath();
                    ImageCroppingActivity.this.startActivity(intent);
                }
                if (ImageCroppingActivity.this.a == 1) {
                    Bitmap croppedImage2 = ImageCroppingActivity.this.cropImageView.getCroppedImage();
                    String file4 = Environment.getExternalStorageDirectory().toString();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(file4);
                    sb3.append("/temp/");
                    File file5 = new File(sb3.toString());
                    file5.mkdir();
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(Long.toString(System.currentTimeMillis()));
                    sb4.append(".jpg");
                    File file6 = new File(file5, sb4.toString());
                    Log.e("Path2::", file6.getAbsolutePath());
                    if (file6.exists()) {
                        file6.delete();
                    }
                    try {
                        FileOutputStream fileOutputStream2 = new FileOutputStream(file6);
                        croppedImage2.compress(CompressFormat.JPEG, 100, fileOutputStream2);
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    Intent intent2 = new Intent(ImageCroppingActivity.this, MainActivity.class);
                    Utils.path2 = file6.getAbsolutePath();
                    ImageCroppingActivity.this.startActivity(intent2);
                }
            }
        });
        this.ivBtnBack.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ImageCroppingActivity.this.onBackPressed();
            }
        });
        this.rotateimage.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ImageCroppingActivity.this.a = 0;
                ImageCroppingActivity.this.cropImageView.rotateImage(90);
            }
        });
    }

    @Override
    @SuppressLint("WrongConstant")
    public void onBackPressed() {
        Intent intent = new Intent(this, ImportImageActivity.class);
        intent.addFlags(335544320);
        startActivity(intent);
        finish();
    }
}
