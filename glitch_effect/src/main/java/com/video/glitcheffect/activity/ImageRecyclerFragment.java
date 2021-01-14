package com.video.glitcheffect.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.video.glitcheffect.R;
import com.video.glitcheffect.adapters.GridSpacingItemDecorationAdapter;
import com.video.glitcheffect.adapters.ImageRecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class ImageRecyclerFragment extends Fragment {
    public static ArrayList<String> f = new ArrayList<>();
    public static ImageRecyclerAdapter imageadapter;
    Context ctx;
    TextView imagetext;
    ArrayList<String> imgList;
    ImageLoader imgLoader;
    RecyclerView recyclerView;
    ImageView tvmessage;
    TextView videotext;

    class loadCursordata extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog pd = null;

        loadCursordata() {
        }

        @Override
        public void onPreExecute() {
            if (!ImageRecyclerFragment.this.getActivity().isFinishing()) {
                this.pd = new ProgressDialog(ImageRecyclerFragment.this.ctx);
                this.pd.setMessage("Loading...");
                this.pd.setCancelable(false);
                this.pd.setCanceledOnTouchOutside(true);
                this.pd.show();
            }
        }

        public Boolean doInBackground(Void... voidArr) {
            String[] list;
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getPath());
            sb.append("/");
            sb.append(ImageRecyclerFragment.this.getResources().getString(R.string.parent_folder_name));
            sb.append("/");
            sb.append(ImageRecyclerFragment.this.getResources().getString(R.string.image_folder));
            String sb2 = sb.toString();
            ImageRecyclerFragment.this.imgList = new ArrayList<>();
            File file = new File(sb2);
            if (file.exists()) {
                for (String str : file.list()) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(file.getPath());
                    sb3.append("/");
                    sb3.append(str);
                    File file2 = new File(sb3.toString());
                    if (file2.getName().endsWith("jpg")) {
                        ImageRecyclerFragment.this.imgList.add(file2.getPath());
                    }
                }
            }
            return Boolean.valueOf(true);
        }

        @Override
        public void onPostExecute(Boolean bool) {
            if (this.pd != null && this.pd.isShowing()) {
                this.pd.dismiss();
            }
            Collections.reverse(ImageRecyclerFragment.this.imgList);
            if (ImageRecyclerFragment.this.imgList.size() == 0) {
                ImageRecyclerFragment.this.tvmessage.setVisibility(View.VISIBLE);
                ImageRecyclerFragment.this.imagetext.setVisibility(View.VISIBLE);
                ImageRecyclerFragment.this.recyclerView.setVisibility(View.GONE);
                return;
            }
            ImageRecyclerFragment.this.tvmessage.setVisibility(View.GONE);
            ImageRecyclerFragment.this.imagetext.setVisibility(View.GONE);
            ImageRecyclerFragment.this.recyclerView.setVisibility(View.VISIBLE);
            ImageRecyclerFragment.imageadapter = new ImageRecyclerAdapter(ImageRecyclerFragment.this.getActivity(), ImageRecyclerFragment.this.imgList, ImageRecyclerFragment.this.imgLoader);
            ImageRecyclerFragment.this.recyclerView.setAdapter(ImageRecyclerFragment.imageadapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_recycler, viewGroup, false);
        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.gridView);
        this.tvmessage = (ImageView) inflate.findViewById(R.id.nullimg);
        this.imagetext = (TextView) inflate.findViewById(R.id.imagetext);
        this.videotext = (TextView) inflate.findViewById(R.id.videotext);
        this.ctx = getActivity();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.ctx, 2);
        this.recyclerView.addItemDecoration(new GridSpacingItemDecorationAdapter(this.ctx, R.dimen.item_offset));
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        initImageLoader();
        new loadCursordata().execute(new Void[0]);
        return inflate;
    }

    private void initImageLoader() {
        Log.d("test", "init image loader");
        ImageLoaderConfiguration build = new Builder(getActivity()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        this.imgLoader = ImageLoader.getInstance();
        this.imgLoader.init(build);
    }
}
