package com.video.glitcheffect.render;

import cn.ezandroid.ezfilter.core.FilterRender;

public class NoFilterRender extends FilterRender {
    float mMix;

    public NoFilterRender() {
        setFragmentShader("precision mediump float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nvoid main()\n{\n    gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    public void adjust(float f) {
        this.mMix = f;
    }
}
