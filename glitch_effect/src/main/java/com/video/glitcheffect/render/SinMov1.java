package com.video.glitcheffect.render;

import android.opengl.GLES20;
import cn.ezandroid.ezfilter.core.FilterRender;

public class SinMov1 extends FilterRender {

    private String uniformmyoffset = "offset";
    private float mMix = 0.5f;
    private int mMixHandle;
    private float mOffset;
    private int mOffsetHandler;
    private long mStartTime;

    public SinMov1() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform float offset;\nuniform lowp float sliderValue;\n lowp float sliderValue2=0.5 ;\n lowp float frameWidth=720.0;\n lowp float frameHeight=1280.0;\n\nvoid main()\n{\nlowp vec2 iResolution = vec2( 1.0,1.0);\n vec2 uv = textureCoordinate.xy / iResolution.xy;\n lowp vec2 uv1 = uv;\n              lowp vec2 uv2 = uv;\n              lowp vec4 finalColor  = texture2D(inputImageTexture ,textureCoordinate );\n              lowp float temDiv = (30.0  *(sliderValue+0.05))+ 0.2;  //2.0\n              lowp float y  = mod(floor(uv1.y * frameHeight / temDiv), 2.0);\n              lowp float y1 = mod(floor(0.2-uv2.y * frameHeight / temDiv), 2.0);\n              lowp float timeTemp  = mod(offset,20.0)* (sliderValue2*30.0);\n              lowp float x1 =abs(sin((uv1.y-uv1.x + timeTemp))) * 0.04;\n              if (y1 == 0.0 )\n              {\n              finalColor =  texture2D(inputImageTexture, 1.0*(uv2 + vec2(-x1 , 0.)));\n              }\n              else\n              {\n              finalColor =  texture2D(inputImageTexture, 1.0*(uv1 + vec2(x1 , 0.)));\n              }\n              gl_FragColor = finalColor;\n}");
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
        this.mMix = f;
    }
}
