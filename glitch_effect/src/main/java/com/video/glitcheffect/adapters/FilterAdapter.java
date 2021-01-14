package com.video.glitcheffect.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.video.glitcheffect.R;
import com.video.glitcheffect.activity.Camera2FilterActivityGAP;
import com.video.glitcheffect.interfaces.ItemfilterclickInterface;

import java.util.ArrayList;

import cn.ezandroid.ezfilter.core.FilterRender;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.vh_myfilter> {
    public static int filterPosition;

    ArrayList<FilterRender> arrfilren;
    int[] images;

    ItemfilterclickInterface itemfilterclickInterface;
    private Context mContext;

    public class vh_myfilter extends RecyclerView.ViewHolder {
        LinearLayout containor;
        ImageView filterimage;
        ImageView filternew;

        public final RelativeLayout ivfilter;
        ImageView selectedfilter;

        public vh_myfilter(View view) {
            super(view);
            this.ivfilter = (RelativeLayout) view.findViewById(R.id.iv_filter);
            this.selectedfilter = (ImageView) view.findViewById(R.id.selected_filter);
            this.filterimage = (ImageView) view.findViewById(R.id.filter_image);
            this.filternew = (ImageView) view.findViewById(R.id.filter_new);
            this.containor = (LinearLayout) view.findViewById(R.id.container);
        }
    }

    public FilterAdapter(Context context, ArrayList<FilterRender> arrayList, ItemfilterclickInterface itemfilterclickInterface2, int[] iArr) {
        this.mContext = context;
        this.arrfilren = arrayList;
        this.itemfilterclickInterface = itemfilterclickInterface2;
        this.images = iArr;
    }

    public vh_myfilter onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new vh_myfilter(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_filteritem, viewGroup, false));
    }

    public void onBindViewHolder(final vh_myfilter vhmyfilter, final int i) {
        int i2 = this.images[i];
        vhmyfilter.ivfilter.setBackground(this.mContext.getResources().getDrawable(R.drawable.no_filter));
        vhmyfilter.filternew.setBackgroundColor(getDominantColor(BitmapFactory.decodeResource(this.mContext.getResources(), this.images[i])));
        if (i == 0) {
            vhmyfilter.filternew.setAlpha(0.0f);
        } else {
            vhmyfilter.filternew.setAlpha(0.7f);
        }
        if (i == filterPosition) {
            vhmyfilter.selectedfilter.setVisibility(View.VISIBLE);
            vhmyfilter.containor.setClickable(false);
        } else {
            vhmyfilter.selectedfilter.setVisibility(View.GONE);
            vhmyfilter.containor.setClickable(true);
        }
        vhmyfilter.ivfilter.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (i == FilterAdapter.filterPosition) {
                    vhmyfilter.selectedfilter.setVisibility(View.VISIBLE);
                    vhmyfilter.containor.setClickable(false);
                    return;
                }
                Camera2FilterActivityGAP.star2 = 1;
                vhmyfilter.selectedfilter.setVisibility(View.GONE);
                vhmyfilter.containor.setClickable(true);
                FilterAdapter.filterPosition = i;
                ((FilterRender) FilterAdapter.this.arrfilren.get(i)).getClass().getCanonicalName();
                FilterAdapter.this.itemfilterclickInterface.onfilterClick(i, (FilterRender) FilterAdapter.this.arrfilren.get(i));
                FilterAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public int getItemCount() {
        return this.arrfilren.size();
    }

    public int getDominantColor(Bitmap bitmap) {
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        int pixel = createScaledBitmap.getPixel(0, 0);
        createScaledBitmap.recycle();
        return pixel;
    }
}
