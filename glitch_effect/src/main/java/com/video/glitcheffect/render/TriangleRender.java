package com.video.glitcheffect.render;

import android.opengl.GLES20;
import cn.ezandroid.ezfilter.core.FilterRender;

public class TriangleRender extends FilterRender {

    private float mMix = 0.5f;
    private int mMixHandle;

    public TriangleRender() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float offset;\nvarying vec2 textureCoordinate;\nuniform float sliderValue;\nvoid main()\n{\n lowp float scale = (1.0- sliderValue)*50.0 ; \n                lowp  vec2 iResolution =vec2(1.0,1.0);\n                lowp vec2 tile_num = vec2(scale,scale);\n                lowp vec2 uv = textureCoordinate.xy / iResolution;\n                lowp vec2 uv2 = floor(uv*tile_num)/tile_num;\n                uv -= uv2;\n                uv *= tile_num;\n                gl_FragColor = texture2D(inputImageTexture,uv2 + vec2(step(1.0-uv.y,uv.x)/(2.0*tile_num.x),step(uv.x,uv.y)/(2.0*tile_num.y)));\n}");
    }

    @Override
    public void initShaderHandles() {
        super.initShaderHandles();
        this.mMixHandle = GLES20.glGetUniformLocation(this.mProgramHandle, "sliderValue");
    }

    @Override
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.mMixHandle, this.mMix);
    }

    public void adjust(float f) {
        this.mMix = f;
    }
}
