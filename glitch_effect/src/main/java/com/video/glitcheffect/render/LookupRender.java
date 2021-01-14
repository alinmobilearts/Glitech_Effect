package com.video.glitcheffect.render;

import android.content.Context;
import android.opengl.GLES20;
import cn.ezandroid.ezfilter.extra.MultiBitmapInputRender;

public class LookupRender extends MultiBitmapInputRender {

    private String uniformmyoffset = "offset";

    private float mMix = 0.5f;
    private int mMixHandle;
    private float mOffset;
    private int mOffsetHandler;

    @Override
    public String getFragmentShader() {
        return "precision highp float;\n varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n uniform lowp float u_mix;\nuniform lowp int isActive;\n void main()\n {\n     highp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     highp float blueColor = textureColor.b * 63.0;\n     highp vec2 quad1;\n     quad1.y = floor(floor(blueColor) / 8.0);\n     quad1.x = floor(blueColor) - (quad1.y * 8.0);\n     highp vec2 quad2;\n     quad2.y = floor(ceil(blueColor) / 8.0);\n     quad2.x = ceil(blueColor) - (quad2.y * 8.0);\n     highp vec2 texPos1;\n     texPos1.x = (quad1.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos1.y = (quad1.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     highp vec2 texPos2;\n     texPos2.x = (quad2.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.r);\n     texPos2.y = (quad2.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * textureColor.g);\n     \n     lowp vec4 newColor1 = texture2D(inputImageTexture2, texPos1);\n     lowp vec4 newColor2 = texture2D(inputImageTexture2, texPos2);\n     \n     lowp vec4 newColor = mix(newColor1, newColor2, fract(blueColor));\nif(isActive==0){\n     gl_FragColor = mix(textureColor, vec4(newColor.rgb, textureColor.w), u_mix);\n}else{\ngl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}\n }";
    }

    public LookupRender(Context context, int i) {
        super(context, new int[]{i});
    }

    @Override
    public void initShaderHandles() {
        super.initShaderHandles();
        this.mMixHandle = GLES20.glGetUniformLocation(this.mProgramHandle, "u_mix");
        this.mOffsetHandler = GLES20.glGetUniformLocation(this.mProgramHandle, this.uniformmyoffset);
    }

    @Override
    public void bindShaderValues() {
        super.bindShaderValues();
        GLES20.glUniform1f(this.mMixHandle, this.mMix);
        GLES20.glUniform1f(this.mOffsetHandler, this.mOffset);
    }

    public void adjust(float f) {
        this.mMix = f;
    }

    public void image(int i) {
        super.setImages(this.mContext, new int[]{i});
    }
}
