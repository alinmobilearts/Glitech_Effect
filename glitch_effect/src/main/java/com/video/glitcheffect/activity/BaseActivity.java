package com.video.glitcheffect.activity;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public <T extends View> T $(@IdRes int i) {
        return findViewById(i);
    }

    public <T extends View> T $(View view, @IdRes int i) {
        return view.findViewById(i);
    }
}
