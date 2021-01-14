package com.video.glitcheffect.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.video.glitcheffect.Ad_class;
import com.video.glitcheffect.R;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView imageview;

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_splash_screen);


        getWindow().setFlags(1024, 1024);

//        Ad_class.loadfullad(SplashScreenActivity.this);


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, ImportImageActivity.class);
            intent.setFlags(67141632);
            SplashScreenActivity.this.startActivity(intent);
            SplashScreenActivity.this.finish();
        }, 3000);
    }
}
