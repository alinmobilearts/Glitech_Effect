package com.video.glitcheffect.render;

import android.opengl.GLES20;
import cn.ezandroid.ezfilter.core.FilterRender;

public class WobbleRender extends FilterRender {

    private String uniformmyoffset = "offset";
    private float mMix = 5.0f;
    private int mMixHandle;
    private float mOffset;
    private int mOffsetHandler;
    private long mStartTime;

    public WobbleRender() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float offset;\nvarying vec2 textureCoordinate;\nuniform float speed;\nvoid main()\n{\nvec2 iResolution = vec2(1.0, 1.0);\nvec2 texcoord = textureCoordinate;\ntexcoord.x += sin(texcoord.y * 4.0 * 2.0 * 3.14159 + offset*speed) / 100.0;\ngl_FragColor = texture2D(inputImageTexture, texcoord);\n}");
        this.mStartTime = System.currentTimeMillis();
    }

    @Override
    public void initShaderHandles() {
        super.initShaderHandles();
        this.mOffsetHandler = GLES20.glGetUniformLocation(this.mProgramHandle, this.uniformmyoffset);
        this.mMixHandle = GLES20.glGetUniformLocation(this.mProgramHandle, "speed");
    }

    @Override
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.mOffsetHandler, this.mOffset);
        GLES20.glUniform1f(this.mMixHandle, this.mMix);
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

    public void adjust(float f) {
        this.mMix = f * 10.0f;
    }
}
