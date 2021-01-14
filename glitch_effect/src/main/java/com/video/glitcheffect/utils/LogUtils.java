package com.video.glitcheffect.utils;

import android.util.Log;

public class LogUtils {
    private static boolean logststuas = true;

    public static void log(String str) {
        if (logststuas) {
            Log.e("VideoGlitch", str);
        }
    }
}
