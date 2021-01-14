package com.video.glitcheffect.render;

import android.opengl.GLES20;
import cn.ezandroid.ezfilter.core.FilterRender;

public class Binary extends FilterRender {

    private float mMix = 0.5f;
    private int mMixHandle;

    public Binary() {
        setFragmentShader("precision highp float;\nvarying lowp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform lowp float sliderValue;\nvoid main(){\n lowp vec2 iResolution = vec2( 1.0,1.0);\n             lowp vec2 uv = textureCoordinate.xy / iResolution.xy;\n             lowp float x = uv.s;\n             lowp float y = uv.t;\n             lowp float glitchStrength = (100.0 + 55.55)/ 5.0;\n             glitchStrength = 30.0;\n             glitchStrength = 21.0 - (sliderValue *20.0) ;  //10.0\n             lowp float psize = 0.04 * glitchStrength;\n             lowp float psq = 1.0 / psize;\n             lowp float px = floor( x * psq + 0.5) * psize;\n             lowp float py = floor( y * psq + 0.5) * psize;\n             lowp vec4 colSnap = texture2D(inputImageTexture, vec2( px,py) );\n             lowp float lum = pow( 1.0 - (colSnap.r + colSnap.g + colSnap.b) / 3.0, glitchStrength );\n             lowp float qsize = 1.0 * lum;\n             lowp float qsq = 1.0 / qsize;\n             lowp float qx = floor( x * qsq + 0.5) * qsize;\n             lowp float qy = floor( y * qsq + 0.5) * qsize;\n             lowp float rx = (px - qx) * lum + x;\n             lowp float ry = (py - qy) * lum + y;\n             gl_FragColor = texture2D(inputImageTexture, vec2( rx,ry));\n}");
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
