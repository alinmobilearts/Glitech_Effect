package com.video.glitcheffect.adapters;

import android.content.Context;
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

public class RenderAdapter extends RecyclerView.Adapter<RenderAdapter.vh_myfilter> {
    public static int selectpos;

     ArrayList<FilterRender> arrfilren;
     int[] images;

     ItemfilterclickInterface itemfilterclickInterface;
     Context mContext;

    public class vh_myfilter extends RecyclerView.ViewHolder {
        LinearLayout containor;
        ImageView filterimage;

        public final RelativeLayout ivfilter;
        ImageView selectedfilter;

        public vh_myfilter(View view) {
            super(view);
            this.ivfilter = (RelativeLayout) view.findViewById(R.id.iv_filter);
            this.selectedfilter = (ImageView) view.findViewById(R.id.selected_filter);
            this.filterimage = (ImageView) view.findViewById(R.id.filter_image);
            this.containor = (LinearLayout) view.findViewById(R.id.container);
        }
    }

    public RenderAdapter(Context context, ArrayList<FilterRender> arrayList, ItemfilterclickInterface itemfilterclickInterface2, int[] iArr) {
        this.mContext = context;
        this.arrfilren = arrayList;
        this.itemfilterclickInterface = itemfilterclickInterface2;
        this.images = iArr;
    }

    public vh_myfilter onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new vh_myfilter(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_filteritem, viewGroup, false));
    }

    public void onBindViewHolder(final vh_myfilter vhmyfilter, final int i) {
        vhmyfilter.filterimage.setImageResource(this.images[i]);
        if (i == selectpos) {
            vhmyfilter.selectedfilter.setVisibility(View.VISIBLE);
            vhmyfilter.containor.setClickable(false);
        } else {
            vhmyfilter.selectedfilter.setVisibility(View.GONE);
            vhmyfilter.containor.setClickable(true);
        }
        vhmyfilter.ivfilter.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (i == RenderAdapter.selectpos) {
                    vhmyfilter.selectedfilter.setVisibility(View.VISIBLE);
                    vhmyfilter.containor.setClickable(false);
                    return;
                }
                Camera2FilterActivityGAP.star1 = 1;
                vhmyfilter.selectedfilter.setVisibility(View.GONE);
                vhmyfilter.containor.setClickable(true);
                RenderAdapter.selectpos = i;
                ((FilterRender) RenderAdapter.this.arrfilren.get(i)).getClass().getCanonicalName();
                RenderAdapter.this.itemfilterclickInterface.onfilterClick(i, (FilterRender) RenderAdapter.this.arrfilren.get(i));
                RenderAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public int getItemCount() {
        return this.arrfilren.size();
    }
}
