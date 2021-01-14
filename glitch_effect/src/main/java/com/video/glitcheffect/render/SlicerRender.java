package com.video.glitcheffect.render;

import android.opengl.GLES20;
import cn.ezandroid.ezfilter.core.FilterRender;

public class SlicerRender extends FilterRender {

    private String uniformmyoffset = "offset";
    private float mMix = 0.5f;
    private int mMixHandle;
    private float mOffset;
    private int mOffsetHandler;
    private long mStartTime;

    public SlicerRender() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float offset;\nvarying vec2 textureCoordinate;\nconst lowp float ring1 = 100.0;  //60.0\n            const lowp float ring2 = 10.0;  //10.0\n            const lowp float push1 = 0.4;  //0.4\n            const lowp float push2 = 0.0;  //0.2\n            const lowp float diminish = 0.05;\n            uniform lowp float sliderValue;\n\nfloat rand(vec2 co){\n    return fract(sin(dot(co.xy ,vec2(12.9898,78.233)*3.141)) * 43758.5453);\n}\n\nvoid main()\n{\nlowp float tempTime = mod(offset,30.0);\n    lowp float time = mod((tempTime+1.0)*20.0,floor((tempTime+1.0)*20.0))   ;\n    lowp vec2 iResolution = vec2(1.0,1.0);\n    lowp vec2 uv = textureCoordinate.xy / iResolution.xy;\n    lowp float  customRing = ring1  * (sliderValue+0.001);\n    lowp float r1 = rand(floor(uv.yy * customRing  )/customRing);\n    lowp float r2 = rand(floor(uv.yy * ring2 )/ring2);\n    r1 = -1.0 + 2.0 * r1;\n    r2 = -1.0 + 2.0 * r2;\n    r1 *= push1;\n    r2 *= push2;\n    r1 += r2;\n    r1 *= diminish ;\n    highp vec4 tex = texture2D(inputImageTexture, uv + vec2(r1 * time*2.0 ,0.0));\n    if(uv.x+r1 > 1.0 || uv.x+r1 <= 0.0)\n    {\n        gl_FragColor = tex;\n    } else {\n        gl_FragColor =tex;\n    }\n\n}");
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
