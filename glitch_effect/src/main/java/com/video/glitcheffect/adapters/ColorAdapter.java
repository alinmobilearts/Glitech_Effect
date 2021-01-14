package com.video.glitcheffect.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.video.glitcheffect.R;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.viewHolder> {

    int colorPosition = 0;

    ImageView[] imageView;

    Context mContext;

    int[][] modes;

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView ftimageview;
        ImageView ftselectedbg;
        ImageView ftselectedbgmiddle;

        public viewHolder(View view) {
            super(view);
            this.ftimageview = (ImageView) view.findViewById(R.id.ft_image);
            this.ftselectedbg = (ImageView) view.findViewById(R.id.ft_selected_bg);
            this.ftselectedbgmiddle = (ImageView) view.findViewById(R.id.ft_selected_bg_middle);
        }
    }

    public ColorAdapter(int[][] iArr, ImageView[] imageViewArr, Context context) {
        this.imageView = imageViewArr;
        this.modes = iArr;
        this.mContext = context;
    }

    @NonNull
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_thumb, viewGroup, false));
    }

    @SuppressLint("WrongConstant")
    public void onBindViewHolder(@NonNull viewHolder viewholder, final int i) {
        switch (PreferenceManager.getDefaultSharedPreferences(this.mContext).getInt("FrameCount", 2)) {
            case 2:
                GradientDrawable gradientDrawable = new GradientDrawable(Orientation.LEFT_RIGHT, new int[]{this.modes[i][0], this.modes[i][1]});
                gradientDrawable.setShape(1);
                viewholder.ftimageview.setBackground(gradientDrawable);
                break;
            case 3:
                GradientDrawable gradientDrawable2 = new GradientDrawable(Orientation.LEFT_RIGHT, new int[]{this.modes[i][0], this.modes[i][1], this.modes[i][0]});
                gradientDrawable2.setShape(1);
                viewholder.ftimageview.setBackground(gradientDrawable2);
                break;
            case 4:
                GradientDrawable gradientDrawable3 = new GradientDrawable(Orientation.LEFT_RIGHT, new int[]{this.modes[i][0], this.modes[i][1], this.modes[i][0], this.modes[i][1]});
                gradientDrawable3.setShape(1);
                viewholder.ftimageview.setBackground(gradientDrawable3);
                break;
            case 5:
                GradientDrawable gradientDrawable4 = new GradientDrawable(Orientation.LEFT_RIGHT, new int[]{this.modes[i][0], this.modes[i][1], this.modes[i][0], this.modes[i][1], this.modes[i][0]});
                gradientDrawable4.setShape(1);
                viewholder.ftimageview.setBackground(gradientDrawable4);
                break;
        }
        if (this.colorPosition == i) {
            viewholder.ftselectedbg.setVisibility(0);
            viewholder.ftselectedbgmiddle.setVisibility(0);
        } else {
            viewholder.ftselectedbg.setVisibility(8);
            viewholder.ftselectedbgmiddle.setVisibility(8);
        }
        viewholder.ftimageview.setOnClickListener(new OnClickListener() {
            final int valueeposition;

            {
                this.valueeposition = i;
            }

            public void onClick(View view) {
                switch (PreferenceManager.getDefaultSharedPreferences(ColorAdapter.this.mContext).getInt("FrameCount", 2)) {
                    case 2:
                        ColorAdapter.this.imageView[0].setColorFilter(ColorAdapter.this.modes[this.valueeposition][0], Mode.MULTIPLY);
                        ColorAdapter.this.imageView[1].setColorFilter(ColorAdapter.this.modes[this.valueeposition][1], Mode.MULTIPLY);
                        break;
                    case 3:
                        ColorAdapter.this.imageView[0].setColorFilter(ColorAdapter.this.modes[this.valueeposition][0], Mode.MULTIPLY);
                        ColorAdapter.this.imageView[1].setColorFilter(ColorAdapter.this.modes[this.valueeposition][1], Mode.MULTIPLY);
                        ColorAdapter.this.imageView[2].setColorFilter(ColorAdapter.this.modes[this.valueeposition][0], Mode.MULTIPLY);
                        break;
                    case 4:
                        ColorAdapter.this.imageView[0].setColorFilter(ColorAdapter.this.modes[this.valueeposition][0], Mode.MULTIPLY);
                        ColorAdapter.this.imageView[1].setColorFilter(ColorAdapter.this.modes[this.valueeposition][1], Mode.MULTIPLY);
                        ColorAdapter.this.imageView[2].setColorFilter(ColorAdapter.this.modes[this.valueeposition][0], Mode.MULTIPLY);
                        ColorAdapter.this.imageView[3].setColorFilter(ColorAdapter.this.modes[this.valueeposition][1], Mode.MULTIPLY);
                        break;
                    case 5:
                        ColorAdapter.this.imageView[0].setColorFilter(ColorAdapter.this.modes[this.valueeposition][0], Mode.MULTIPLY);
                        ColorAdapter.this.imageView[1].setColorFilter(ColorAdapter.this.modes[this.valueeposition][1], Mode.MULTIPLY);
                        ColorAdapter.this.imageView[2].setColorFilter(ColorAdapter.this.modes[this.valueeposition][0], Mode.MULTIPLY);
                        ColorAdapter.this.imageView[3].setColorFilter(ColorAdapter.this.modes[this.valueeposition][1], Mode.MULTIPLY);
                        ColorAdapter.this.imageView[4].setColorFilter(ColorAdapter.this.modes[this.valueeposition][0], Mode.MULTIPLY);
                        break;
                }
                ColorAdapter.this.colorPosition = i;
                ColorAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public int getItemCount() {
        return this.modes.length;
    }
}
