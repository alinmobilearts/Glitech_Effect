package com.video.glitcheffect.render;

import android.opengl.GLES20;
import cn.ezandroid.ezfilter.core.FilterRender;

public class DrunkenRender extends FilterRender {

    private String uniformmyoffset = "offset";
    private float mMix = 2.0f;
    private int mMixHandle;
    private float mOffset;
    private int mOffsetHandler;
    private long mStartTime;

    public DrunkenRender() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nuniform float offset;\nvarying vec2 textureCoordinate;\nuniform lowp float sliderValue;\nvoid main()\n{\nvec2 iResolution = vec2(1,1);\nfloat drunk = sin(offset*sliderValue)/10.0;\nfloat unitDrunk1 = (sin(offset*1.2)+1.0)/2.0;\nfloat unitDrunk2 = (sin(offset*1.8)+1.0)/2.0;\nvec2 normalizedCoord = mod((textureCoordinate.xy + vec2(0, drunk)) / iResolution.xy, 1.0);\nnormalizedCoord.x = pow(normalizedCoord.x, mix(1.25, 0.85, unitDrunk1));\nnormalizedCoord.y = pow(normalizedCoord.y, mix(0.85, 1.25, unitDrunk2));\nvec2 normalizedCoord2 = mod((textureCoordinate.xy + vec2(drunk, 0)) / iResolution.xy, 1.0);\nnormalizedCoord2.x = pow(normalizedCoord2.x, mix(0.95, 1.1, unitDrunk2));\nnormalizedCoord2.y = pow(normalizedCoord2.y, mix(1.1, 0.95, unitDrunk1));\nvec2 normalizedCoord3 = textureCoordinate.xy/iResolution.xy;\nvec4 color = texture2D(inputImageTexture, normalizedCoord);\nvec4 color2 = texture2D(inputImageTexture, normalizedCoord2);\nvec4 color3 = texture2D(inputImageTexture, normalizedCoord3);\n// Mess with colors and test swizzling\ncolor.x = sqrt(color2.x);\ncolor2.x = sqrt(color2.x);\nvec4 finalColor = mix( mix(color, color2, mix(0.4, 0.6, unitDrunk1)), color3, 0.4);\n// \nif (length(finalColor) > 1.4)\nfinalColor.xy = mix(finalColor.xy, normalizedCoord3, 0.5);\nelse if (length(finalColor) < 0.4)\nfinalColor.yz = mix(finalColor.yz, normalizedCoord3, 0.5);\n\ngl_FragColor = finalColor;\n}");
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
        this.mMix = f * 4.0f;
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
