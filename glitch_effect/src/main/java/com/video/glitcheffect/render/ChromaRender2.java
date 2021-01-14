package com.video.glitcheffect.render;

import android.opengl.GLES20;

import cn.ezandroid.ezfilter.core.FilterRender;

public class ChromaRender2 extends FilterRender {

    private String uniformmyoffset = "offset";
    private float mMix = 0.5f;
    private int mMixHandle;
    private float mOffset;
    private int mOffsetHandler;
    private long mStartTime;


    public ChromaRender2() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float offset;\nvarying vec2 textureCoordinate;\nuniform lowp float sliderValue;// Work on Distance .\n lowp float sliderValue2=0.7 ;//  Work on Round .\n lowp float sliderValue3 =0.7;//  Work on Round .\n\n lowp vec2 calDist (lowp float angle , lowp float radius )\n        {\n            lowp vec2 center = vec2(0.0,0.0);\n            return vec2 (radius*cos(angle)+ center.x , radius*sin(angle) + center.y  );\n        }\nvoid main()\n{\nlowp vec2 iResolution = vec2(1, 1);  // temp need to change\n                 lowp vec2 uv = textureCoordinate.xy / iResolution.xy;\n                 lowp vec3 col;\n                 lowp float Distance = 0.5;\n                 Distance = (sliderValue2 * 0.5)+0.2 ;\n                 Distance = pow(Distance, 6.0);\n                 lowp float angleValue ;\n                 angleValue = sliderValue ;\n                 lowp float Angle1  = (angleValue * 6.28319) ;\n                 lowp float Angle2  = (angleValue * (6.28319 * 2.06 )) ;\n                 lowp float Angle3  = (angleValue * (6.28319 * (2.06*2.0 ) ));\n                 col.r = texture2D(inputImageTexture,uv + calDist(Angle1,Distance)).r;\n                 col.g = texture2D(inputImageTexture,uv + calDist(Angle2,Distance)).g;\n                 col.b = texture2D(inputImageTexture,uv + calDist(Angle3,Distance)).b;\n                 gl_FragColor = vec4(col,1.0);\n\n}");
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
