package com.video.glitcheffect.adapters;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecorationAdapter extends RecyclerView.ItemDecoration {
    private int mItemOffset;

    public GridSpacingItemDecorationAdapter(int i) {
        this.mItemOffset = i;
    }

    public GridSpacingItemDecorationAdapter(@NonNull Context context, @DimenRes int i) {
        this(context.getResources().getDimensionPixelSize(i));
    }

    @Override
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        super.getItemOffsets(rect, view, recyclerView, state);
        rect.set(this.mItemOffset, this.mItemOffset, this.mItemOffset, this.mItemOffset);
    }
}
