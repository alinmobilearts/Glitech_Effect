package com.video.glitcheffect;

import android.content.Context;
import android.content.Intent;

import com.video.glitcheffect.activity.ImportImageActivity;

public class GlitchEffectPlugin {

    private Context appContext;

    private static GlitchEffectPlugin mInstance = null;

    public GlitchEffectPlugin() {
    }

    public static GlitchEffectPlugin getInstance() {
        if (mInstance == null) {
            mInstance = new GlitchEffectPlugin();
        }
        return mInstance;
    }

    public void launch(Context appContext) {
        this.appContext = appContext;
        Intent intent = new Intent(appContext, ImportImageActivity.class);
        appContext.startActivity(intent);
    }
}
