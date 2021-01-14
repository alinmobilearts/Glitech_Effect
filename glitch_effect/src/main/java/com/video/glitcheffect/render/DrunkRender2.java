package com.video.glitcheffect.render;

import android.opengl.GLES20;
import cn.ezandroid.ezfilter.core.FilterRender;

public class DrunkRender2 extends FilterRender {

    private String uniformmyoffset = "offset";
    private float mMix = 1.0f;
    private int mMixHandle;
    private float mOffset;
    private int mOffsetHandler;
    private long mStartTime;

    public DrunkRender2() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform float offset;\nfloat perspective = 0.3;\nuniform lowp float sliderValue;\nlowp int samples = 25;\nlowp float minBlur = .1;\nlowp float maxBlur = .3;\nlowp float speed = 3.;\nvoid main()\n{\nlowp vec2 iResolution = vec2( 1.0,1.0);\n vec2 p = textureCoordinate.xy / iResolution.xy;\n\n    vec4 result = vec4(0);\n    \n    float timeQ = mix(minBlur, maxBlur, (sin(offset*speed*sliderValue)+1.)/2.);\n    \n\tfor (int i=0; i<=samples; i++)\n    {        \n        float q = float(i)/float(samples);\n        result += texture2D(inputImageTexture, p + (vec2(0.5)-p)*q*timeQ)/float(samples);\n    }\n\tgl_FragColor = result;\n}");
        this.mStartTime = System.currentTimeMillis();
    }


    @Override
    public void initShaderHandles() {
        super.initShaderHandles();
        this.mOffsetHandler = GLES20.glGetUniformLocation(this.mProgramHandle, this.uniformmyoffset);
        this.mMixHandle = GLES20.glGetUniformLocation(this.mProgramHandle, "sliderValue");
    }

    @Override
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.mOffsetHandler, this.mOffset);
        GLES20.glUniform1f(this.mMixHandle, this.mMix);
    }

    public void adjust(float f) {
        this.mMix = f;
    }


    @Override
    public void onDraw() {
        super.onDraw();
        long currentTimeMillis = System.currentTimeMillis() - this.mStartTime;
        if (currentTimeMillis > 20000) {
            this.mStartTime = System.currentTimeMillis();
        }
        this.mOffset = (((float) currentTimeMillis) / 1000.0f) * 2.0f * 3.14159f * 0.75f;
    }
}
