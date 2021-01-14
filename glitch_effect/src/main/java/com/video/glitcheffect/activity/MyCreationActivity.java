package com.video.glitcheffect.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.video.glitcheffect.Ad_class;
import com.video.glitcheffect.R;
import com.video.glitcheffect.adapters.Pager;
import com.video.glitcheffect.utils.Utils;
import com.google.android.material.tabs.TabLayout;


public class MyCreationActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    ImageView back;
    int data = -1;
     TabLayout tabLayout;
     ViewPager viewPager;



    public void onTabReselected(TabLayout.Tab tab) {
        Log.d("sa","ds");
    }

    public void onTabUnselected(TabLayout.Tab tab) {
        Log.d("sa","ds");
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_creation);

//        AdView mAdView = findViewById(R.id.adView);
//        Ad_class.showBanner(mAdView);




        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.back = (ImageView) findViewById(R.id.back);
        this.tabLayout.addTab(this.tabLayout.newTab().setText((CharSequence) "Photo"));
        this.tabLayout.addTab(this.tabLayout.newTab().setText((CharSequence) "Video"));
        this.tabLayout.setTabTextColors(Color.parseColor("#502483"), Color.parseColor("#502483"));
        this.tabLayout.setTabGravity(0);
        this.viewPager.setAdapter(new Pager(getSupportFragmentManager(), this.tabLayout.getTabCount()));
        this.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));
        this.tabLayout.addOnTabSelectedListener(this);


        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MyCreationActivity.this.onBackPressed();
            }
        });
        this.data = Utils.tabpass;
        if (this.data == 0) {
            this.viewPager.setCurrentItem(0);
        }
        if (this.data == 1) {
            this.viewPager.setCurrentItem(1);
        }

    }

    public void onTabSelected(TabLayout.Tab tab) {
        this.viewPager.setCurrentItem(tab.getPosition());
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
