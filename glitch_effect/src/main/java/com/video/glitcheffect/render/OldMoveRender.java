package com.video.glitcheffect.render;

import android.opengl.GLES20;
import cn.ezandroid.ezfilter.core.FilterRender;

public class OldMoveRender extends FilterRender {

    private String uniformmyoffset = "offset";
    private float mMix = 0.5f;
    private int mMixHandle;
    private float mOffset;
    private int mOffsetHandler;
    private long mStartTime;

    public OldMoveRender() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float offset;\nvarying vec2 textureCoordinate;\nuniform lowp float sliderValue;\nfloat lines(vec2 uv)\n{\nfloat noi = 0.5;//GetNoise(uv*vec2(0.4,1.) + vec2(1.,3.));\n    float y = mod(uv.y*6. + offset/2.+sin(offset + sin(offset*0.63)),1.);\n    float start = 0.5;\n    float end = 0.6;\n    float insideLine = step(start,y) - step(end,y);\nfloat fact = (y-start)/(end-start)*insideLine;\nfloat lineColor =  (1.-fact) * insideLine;\nreturn lineColor*noi;\n}\nvoid main()\n{\nlowp vec2 iResolution = vec2( 1.0,1.0);\nvec2 uv = textureCoordinate.xy/iResolution.xy;\nvec2 videoUVs = uv;\nfloat vShift = sliderValue*(sin(offset)*sin(offset*20.));\nvideoUVs.y = mod(videoUVs.y + vShift, 1.);\n    //videoUVs.x = mod(videoUVs.x + vShift, 1);\nvec3 video = vec3(texture2D(inputImageTexture,videoUVs));\nvideo += lines(uv);\nvideo += 0.0;//GetNoise(uv*2.)/2.;\ngl_FragColor = vec4(video,1.0);\n}");
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
