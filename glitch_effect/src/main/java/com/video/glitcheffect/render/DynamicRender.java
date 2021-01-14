package com.video.glitcheffect.render;

import android.opengl.GLES20;
import cn.ezandroid.ezfilter.core.FilterRender;

public class DynamicRender extends FilterRender {

    private String uniformmyoffset = "offset";
    private float mMix = 0.05f;
    private int mMixHandle;
    private float mOffset;
    private int mOffsetHandler;
    private long mStartTime;

    public DynamicRender() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform float offset;\nuniform lowp float sliderValue;\nvoid main()\n{\nlowp vec2 iResolution = vec2( 1.0,1.0);\n vec2 uv = textureCoordinate.xy / iResolution.xy;\n float magnitude = sliderValue;\n vec3 color = texture2D(inputImageTexture, uv).rgb;\n color += texture2D(inputImageTexture, uv+vec2(sin(offset*2.0)*magnitude - cos(offset)*magnitude, 0.)).rgb;\n color /= 2.0;\n gl_FragColor = vec4(color, 1.0);\n}");
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
        this.mMix = f / 10.0f;
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
