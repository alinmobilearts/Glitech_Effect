package com.video.glitcheffect.render;

import android.opengl.GLES20;
import cn.ezandroid.ezfilter.core.FilterRender;

public class ChromaRender1 extends FilterRender {


    private String uniformmyoffset = "offset";
    float mMix = 0.5f;
    private int mMixHandle;
    private float mOffset;
    private int mOffsetHandler;
    private long mStartTime;

    public ChromaRender1() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float offset;\nvarying vec2 textureCoordinate;\nuniform lowp float sliderValue;\nvoid main()\n{\nvec2 iResolution = vec2(1, 1);\nvec2 uv = textureCoordinate.xy / iResolution.xy;\nlowp float Distance =0.5;\nlowp float amount = 0.0 ;\nlowp float vertical = 0.6;\nlowp float horizontal = sliderValue;\nfloat power=offset;\npower *= (0.6*2.0);\n amount = (1.0 + sin(power*6.0)) * Distance;\namount *= 1.0 + sin(power*16.0) * Distance;\n amount *= 1.0 + sin(power*19.0) * Distance;\namount *= 1.0 + sin(power*27.0) * Distance;\n amount = pow(amount, 3.0);\namount *= 0.05;\nvec3 col;\ncol.r = texture2D(inputImageTexture, vec2(uv.x+(amount*horizontal),uv.y+(amount*vertical)) ).r;\ncol.g = texture2D(inputImageTexture, uv ).g;\ncol.b = texture2D(inputImageTexture, vec2(uv.x-(amount*horizontal),uv.y+(amount*vertical)) ).b;\ncol *= (1.0 - amount * 0.5);\ngl_FragColor = vec4(col,1.0);\n}");
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
        float currentTimeMillis = ((float) (System.currentTimeMillis() - this.mStartTime)) / 1000.0f;
        if (currentTimeMillis >= 20.0f) {
            this.mStartTime = System.currentTimeMillis();
        }
        this.mOffset = currentTimeMillis;
    }
}
