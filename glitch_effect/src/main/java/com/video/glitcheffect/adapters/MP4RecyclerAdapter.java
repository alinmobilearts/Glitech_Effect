package com.video.glitcheffect.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.video.glitcheffect.R;
import com.video.glitcheffect.activity.ImportImageActivity;
import com.video.glitcheffect.activity.PreviewActivity;
import com.video.glitcheffect.utils.Utils;
import java.util.ArrayList;

public class MP4RecyclerAdapter extends RecyclerView.Adapter<MP4RecyclerAdapter.MyViewHolder1> {
    ArrayList<String> data = new ArrayList<>();
    int height;
    String id;
    ImageLoader imageLoader;
    private LayoutInflater infalter;

    public final Context mContext;
    int width;

    public static class MyViewHolder1 extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder1(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.recyler_img_item);
        }
    }

    @SuppressLint("WrongConstant")
    public MP4RecyclerAdapter(Context context, ArrayList<String> arrayList, ImageLoader imageLoader2) {
        this.mContext = context;
        this.infalter = (LayoutInflater) context.getSystemService("layout_inflater");
        this.data.addAll(arrayList);
        this.imageLoader = imageLoader2;
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        this.width = i / 2;
        this.height = i2 / 3;
    }

    @NonNull
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder1(this.infalter.inflate(R.layout.my_creation_img_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull MyViewHolder1 myViewHolder1, final int i) {
        RequestManager with = Glide.with(this.mContext);
        StringBuilder sb = new StringBuilder();
        sb.append("file://");
        sb.append(((String) this.data.get(i)).toString());
        with.load(Uri.parse(sb.toString()).toString()).into(myViewHolder1.imageView);
        ImageLoader imageLoader2 = this.imageLoader;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("file://");
        sb2.append(((String) this.data.get(i)).toString());
        imageLoader2.displayImage(Uri.parse(sb2.toString()).toString(), myViewHolder1.imageView);
        myViewHolder1.imageView.setLayoutParams(new LayoutParams(this.width, this.height));
        myViewHolder1.imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Utils.tabpass = 1;
                Intent intent = new Intent(MP4RecyclerAdapter.this.mContext, PreviewActivity.class);
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(((String) MP4RecyclerAdapter.this.data.get(i)).toString());
                intent.putExtra("VideoPath", sb.toString());
                intent.putExtra("isfrommain1", "1");
                ImportImageActivity.imgvid = 1;
                MP4RecyclerAdapter.this.mContext.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return this.data.size();
    }
}
