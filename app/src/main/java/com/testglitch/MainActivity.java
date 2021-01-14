package com.testglitch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.video.glitcheffect.GlitchEffectPlugin;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> GlitchEffectPlugin.getInstance().launch(MainActivity.this));

    }
}