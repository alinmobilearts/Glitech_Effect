package com.video.glitcheffect.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.video.glitcheffect.Ad_class;
import com.video.glitcheffect.R;
import com.video.glitcheffect.adapters.ColorAdapter;
import com.video.glitcheffect.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ColorAdapter gapcoloradapter;
    ImageView brightness;
    RelativeLayout brightnesscolot;
    LinearLayout brightnesslayout;
    Button btn3frames;
    Button btn4frames;
    Button btn5frames;
    ImageView btnback;
    Button btndefaultframes;
    ImageView btnsaveimg;
    ImageView filter;
    RelativeLayout filtercolor;
    LinearLayout filterlayout;
    int framenumber;
    int framecount;
    ImageView[] iv = new ImageView[5];
    RelativeLayout layout;
    TextView loadingtext;

    FrameLayout main;
    Button movediagonal;
    Button moveleftright;
    Button moveupdown;

    Bitmap newcroped;
    RelativeLayout parentbright;
    RelativeLayout parentfilter;
    RelativeLayout parentrgb;

    RecyclerView rccolor;
    ImageView rgb;
    RelativeLayout rgbcolor;
    LinearLayout rgblayout;
    TextView saveprocess;
    SeekBar seekbar;
    SharedPreferences sharedPrefsxx;

    private class LoadFrames extends AsyncTask {

        class update implements Runnable {
            update() {
            }

            public void run() {
                MainActivity.this.updateframe(MainActivity.this.framenumber);
                MainActivity.this.updateInitialFrames(MainActivity.this.framenumber);
                MainActivity.this.seekbar.setProgress(0);
            }
        }

        private LoadFrames() {
        }


        @Override
        public void onPreExecute() {
            super.onPreExecute();
            MainActivity.this.getWindow().setFlags(16, 16);
            MainActivity.this.loadingtext.setVisibility(View.VISIBLE);
        }


        public Object doInBackground(Object[] objArr) {
            MainActivity.this.runOnUiThread(new update());
            return null;
        }


        @Override
        public void onPostExecute(Object obj) {
            super.onPostExecute(obj);
            MainActivity.this.loadingtext.setVisibility(View.GONE);
            MainActivity.this.getWindow().clearFlags(16);
        }
    }

    private class SaveImageToExt extends AsyncTask {
        private SaveImageToExt() {
        }


        @Override
        public void onPreExecute() {
            super.onPreExecute();
            MainActivity.this.saveprocess.setVisibility(View.VISIBLE);
        }


        public Object doInBackground(Object[] objArr) {
            MainActivity.this.saveimage(MainActivity.this.newcroped);
            return null;
        }


        @Override
        public void onPostExecute(Object obj) {
            super.onPostExecute(obj);
            MainActivity.this.saveprocess.setVisibility(View.GONE);
        }
    }


    @Override
    @SuppressLint("WrongConstant")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);


        this.btnsaveimg = (ImageView) findViewById(R.id.btn_save_img);
        this.saveprocess = (TextView) findViewById(R.id.progress_txt_save);
        this.loadingtext = (TextView) findViewById(R.id.loading_text);
        this.layout = (RelativeLayout) findViewById(R.id.layout);
        this.rgb = (ImageView) findViewById(R.id.rgb);
        this.filter = (ImageView) findViewById(R.id.filter);
        this.brightness = (ImageView) findViewById(R.id.brightness);
        this.rgblayout = (LinearLayout) findViewById(R.id.rgblayout);
        this.filterlayout = (LinearLayout) findViewById(R.id.filterlayout);
        this.brightnesslayout = (LinearLayout) findViewById(R.id.brightnesslayout);
        this.rgbcolor = (RelativeLayout) findViewById(R.id.rgbcolor);
        this.filtercolor = (RelativeLayout) findViewById(R.id.filtercolor);
        this.brightnesscolot = (RelativeLayout) findViewById(R.id.brightnesscolor);
        this.parentrgb = (RelativeLayout) findViewById(R.id.parentrgb);
        this.parentfilter = (RelativeLayout) findViewById(R.id.parentfilter);
        this.parentbright = (RelativeLayout) findViewById(R.id.parentbright);
        this.rgb.setBackgroundResource(R.drawable.ic_rgb_pressed);
        this.filter.setBackgroundResource(R.drawable.ic_filter_unpressed);
        this.brightness.setBackgroundResource(R.drawable.ic_brightness_unpressed);
        this.rgblayout.setVisibility(View.VISIBLE);
        this.parentrgb.setBackgroundColor(Color.parseColor("#00D3DA"));
        this.rgbcolor.setBackgroundColor(Color.parseColor("#3B424A"));
        this.filtercolor.setBackgroundColor(Color.parseColor("#3B424A"));
        this.brightnesscolot.setBackgroundColor(Color.parseColor("#3B424A"));
        this.parentrgb.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                MainActivity.this.rgblayout.setVisibility(0);
                MainActivity.this.rgb.setBackgroundResource(R.drawable.ic_rgb_pressed);
                MainActivity.this.filter.setBackgroundResource(R.drawable.ic_filter_unpressed);
                MainActivity.this.brightness.setBackgroundResource(R.drawable.ic_brightness_unpressed);
                MainActivity.this.parentrgb.setBackgroundColor(Color.parseColor("#00D3DA"));
                MainActivity.this.parentfilter.setBackgroundColor(Color.parseColor("#3B424A"));
                MainActivity.this.parentbright.setBackgroundColor(Color.parseColor("#3B424A"));
                MainActivity.this.filterlayout.setVisibility(8);
                MainActivity.this.brightnesslayout.setVisibility(8);
            }
        });
        this.parentfilter.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                MainActivity.this.rgblayout.setVisibility(8);
                MainActivity.this.rgb.setBackgroundResource(R.drawable.ic_rgb_unpressed);
                MainActivity.this.filter.setBackgroundResource(R.drawable.ic_filter_pressed);
                MainActivity.this.brightness.setBackgroundResource(R.drawable.ic_brightness_unpressed);
                MainActivity.this.parentrgb.setBackgroundColor(Color.parseColor("#3B424A"));
                MainActivity.this.parentfilter.setBackgroundColor(Color.parseColor("#00D3DA"));
                MainActivity.this.parentbright.setBackgroundColor(Color.parseColor("#3B424A"));
                MainActivity.this.filterlayout.setVisibility(0);
                MainActivity.this.brightnesslayout.setVisibility(8);
            }
        });
        this.parentbright.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                MainActivity.this.rgblayout.setVisibility(8);
                MainActivity.this.rgb.setBackgroundResource(R.drawable.ic_rgb_unpressed);
                MainActivity.this.filter.setBackgroundResource(R.drawable.ic_filter_unpressed);
                MainActivity.this.brightness.setBackgroundResource(R.drawable.ic_brightness_pressed);
                MainActivity.this.parentrgb.setBackgroundColor(Color.parseColor("#3B424A"));
                MainActivity.this.parentfilter.setBackgroundColor(Color.parseColor("#3B424A"));
                MainActivity.this.parentbright.setBackgroundColor(Color.parseColor("#00D3DA"));
                MainActivity.this.filterlayout.setVisibility(8);
                MainActivity.this.brightnesslayout.setVisibility(0);
            }
        });
        this.sharedPrefsxx = PreferenceManager.getDefaultSharedPreferences(this);
        this.seekbar = (SeekBar) findViewById(R.id.seekBar);
        this.seekbar.getProgressDrawable().setColorFilter(-1, Mode.MULTIPLY);
        this.btnback = (ImageView) findViewById(R.id.btn_back_main_screen);
        this.btnback.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ImportImageActivity.class));
                MainActivity.this.finish();
            }
        });
        this.btndefaultframes = (Button) findViewById(R.id.btn_default_frames);
        this.btn3frames = (Button) findViewById(R.id.btn_3_frames);
        this.btn4frames = (Button) findViewById(R.id.btn_4_frames);
        this.btn5frames = (Button) findViewById(R.id.btn_5_frames);
        this.moveleftright = (Button) findViewById(R.id.btn_move_lefright);
        this.moveupdown = (Button) findViewById(R.id.btn_move_updown);
        this.movediagonal = (Button) findViewById(R.id.btn_move_diagonal);
        this.btnsaveimg.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Bitmap createBitmap = Bitmap.createBitmap(MainActivity.this.main.getWidth(), MainActivity.this.main.getHeight(), Config.ARGB_8888);
                MainActivity.this.main.draw(new Canvas(createBitmap));
                MainActivity.this.newcroped = Bitmap.createBitmap(createBitmap, 50, 50, createBitmap.getWidth() - 50, createBitmap.getHeight() - 100);
                MainActivity.enableDisableView(MainActivity.this.layout, false);
                new SaveImageToExt().execute(new Object[0]);
                /*Ad_class.showFullAd(MainActivity.this, new Ad_class.onLisoner() {
                    @Override
                    public void click() {
                    }
                });*/
            }
        });
        Log.e("Color: ", String.format("#%06X", new Object[]{Integer.valueOf(16842755)}));
        final int[][] iArr = {new int[]{ResourcesCompat.getColor(getResources(), R.color.white, null), ResourcesCompat.getColor(getResources(), R.color.white, null)}, new int[]{ResourcesCompat.getColor(getResources(), R.color.VividSkyBlue, null), ResourcesCompat.getColor(getResources(), R.color.Red, null)}, new int[]{ResourcesCompat.getColor(getResources(), R.color.Red, null), ResourcesCompat.getColor(getResources(), R.color.VividSkyBlue, null)}, new int[]{ResourcesCompat.getColor(getResources(), R.color.Lime, null), ResourcesCompat.getColor(getResources(), R.color.Magenta, null)}, new int[]{ResourcesCompat.getColor(getResources(), R.color.Magenta, null), ResourcesCompat.getColor(getResources(), R.color.Lime, null)}, new int[]{ResourcesCompat.getColor(getResources(), R.color.Lime, null), ResourcesCompat.getColor(getResources(), R.color.MediumBlue, null)}, new int[]{ResourcesCompat.getColor(getResources(), R.color.MediumBlue, null), ResourcesCompat.getColor(getResources(), R.color.Lime, null)}, new int[]{ResourcesCompat.getColor(getResources(), R.color.Magenta, null), ResourcesCompat.getColor(getResources(), R.color.CyberGreen, null)}, new int[]{ResourcesCompat.getColor(getResources(), R.color.CyberGreen, null), ResourcesCompat.getColor(getResources(), R.color.Magenta, null)}, new int[]{ResourcesCompat.getColor(getResources(), R.color.ElectricPurple, null), ResourcesCompat.getColor(getResources(), R.color.TurquoiseBlue, null)}, new int[]{ResourcesCompat.getColor(getResources(), R.color.TurquoiseBlue, null), ResourcesCompat.getColor(getResources(), R.color.ElectricPurple, null)}};
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("FrameCount", 2).apply();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("MoveOption", 3).apply();
        updateframe(2);
        this.seekbar.setProgress(0);
        updateInitialFrames(2);
        setButtonBG(1, 2);
        setButtonBG(2, 2);
        this.rccolor = (RecyclerView) findViewById(R.id.rc_color);
        this.gapcoloradapter = new ColorAdapter(iArr, this.iv, this);
        this.rccolor.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.rccolor.setAdapter(this.gapcoloradapter);
        this.moveleftright.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext()).edit().putInt("MoveOption", 1).apply();
                MainActivity.this.setButtonBG(2, 1);
            }
        });
        this.moveupdown.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext()).edit().putInt("MoveOption", 2).apply();
                MainActivity.this.setButtonBG(2, 3);
            }
        });
        this.movediagonal.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext()).edit().putInt("MoveOption", 3).apply();
                MainActivity.this.setButtonBG(2, 2);
            }
        });
        this.btndefaultframes.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext()).edit().putInt("FrameCount", 2).apply();
                MainActivity.this.setButtonBG(1, 2);
                MainActivity.this.framenumber = 2;
                new LoadFrames().execute(new Object[0]);
                MainActivity.this.gapcoloradapter = new ColorAdapter(iArr, MainActivity.this.iv, MainActivity.this.getApplicationContext());
                MainActivity.this.rccolor.setAdapter(MainActivity.this.gapcoloradapter);
            }
        });
        this.btn3frames.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext()).edit().putInt("FrameCount", 3).apply();
                MainActivity.this.setButtonBG(1, 3);
                MainActivity.this.framenumber = 3;
                new LoadFrames().execute(new Object[0]);
                MainActivity.this.gapcoloradapter = new ColorAdapter(iArr, MainActivity.this.iv, MainActivity.this.getApplicationContext());
                MainActivity.this.rccolor.setAdapter(MainActivity.this.gapcoloradapter);
            }
        });
        this.btn4frames.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext()).edit().putInt("FrameCount", 4).apply();
                MainActivity.this.setButtonBG(1, 4);
                MainActivity.this.framenumber = 4;
                new LoadFrames().execute(new Object[0]);
                MainActivity.this.gapcoloradapter = new ColorAdapter(iArr, MainActivity.this.iv, MainActivity.this.getApplicationContext());
                MainActivity.this.rccolor.setAdapter(MainActivity.this.gapcoloradapter);
            }
        });
        this.btn5frames.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext()).edit().putInt("FrameCount", 5).apply();
                MainActivity.this.setButtonBG(1, 5);
                MainActivity.this.framenumber = 5;
                new LoadFrames().execute(new Object[0]);
                MainActivity.this.gapcoloradapter = new ColorAdapter(iArr, MainActivity.this.iv, MainActivity.this.getApplicationContext());
                MainActivity.this.rccolor.setAdapter(MainActivity.this.gapcoloradapter);
            }
        });
        this.seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int option;

            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("dss", "sd");
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(i);
                Log.d("ProgressVlaue", sb.toString());
                int i2 = i - 100;
                switch (this.option) {
                    case 1:
                        switch (MainActivity.this.framecount) {
                            case 2:
                                MainActivity.this.iv[0].setX((float) i2);
                                MainActivity.this.iv[1].setX((float) (-i2));
                                return;
                            case 3:
                                MainActivity.this.iv[0].setX((float) i2);
                                MainActivity.this.iv[1].setX(0.0f);
                                MainActivity.this.iv[2].setX((float) (-i2));
                                return;
                            case 4:
                                MainActivity.this.iv[0].setX((float) i2);
                                MainActivity.this.iv[1].setX((float) (i2 / 3));
                                int i3 = -i2;
                                MainActivity.this.iv[2].setX((float) (i3 / 3));
                                MainActivity.this.iv[3].setX((float) i3);
                                return;
                            case 5:
                                MainActivity.this.iv[0].setX((float) i2);
                                MainActivity.this.iv[1].setX((float) (i2 / 2));
                                MainActivity.this.iv[2].setX(0.0f);
                                int i4 = -i2;
                                MainActivity.this.iv[3].setX((float) (i4 / 2));
                                MainActivity.this.iv[4].setX((float) i4);
                                return;
                            default:
                                return;
                        }
                    case 2:
                        switch (MainActivity.this.framecount) {
                            case 2:
                                MainActivity.this.iv[0].setY((float) i2);
                                MainActivity.this.iv[1].setY((float) (-i2));
                                return;
                            case 3:
                                MainActivity.this.iv[0].setY((float) i2);
                                MainActivity.this.iv[1].setY(0.0f);
                                MainActivity.this.iv[2].setY((float) (-i2));
                                return;
                            case 4:
                                MainActivity.this.iv[0].setY((float) i2);
                                MainActivity.this.iv[1].setY((float) (i2 / 3));
                                int i5 = -i2;
                                MainActivity.this.iv[2].setY((float) (i5 / 3));
                                MainActivity.this.iv[3].setY((float) i5);
                                return;
                            case 5:
                                MainActivity.this.iv[0].setY((float) i2);
                                MainActivity.this.iv[1].setY((float) (i2 / 2));
                                MainActivity.this.iv[2].setY(0.0f);
                                int i6 = -i2;
                                MainActivity.this.iv[3].setY((float) (i6 / 2));
                                MainActivity.this.iv[4].setY((float) i6);
                                return;
                            default:
                                return;
                        }
                    case 3:
                        switch (MainActivity.this.framecount) {
                            case 2:
                                float f = (float) i2;
                                MainActivity.this.iv[0].setX(f);
                                MainActivity.this.iv[0].setY(f);
                                float f2 = (float) (-i2);
                                MainActivity.this.iv[1].setX(f2);
                                MainActivity.this.iv[1].setY(f2);
                                return;
                            case 3:
                                float f3 = (float) i2;
                                MainActivity.this.iv[0].setX(f3);
                                MainActivity.this.iv[0].setY(f3);
                                MainActivity.this.iv[1].setX(0.0f);
                                MainActivity.this.iv[1].setY(0.0f);
                                float f4 = (float) (-i2);
                                MainActivity.this.iv[2].setX(f4);
                                MainActivity.this.iv[2].setY(f4);
                                return;
                            case 4:
                                float f5 = (float) i2;
                                MainActivity.this.iv[0].setX(f5);
                                MainActivity.this.iv[0].setY(f5);
                                float f6 = (float) (i2 / 3);
                                MainActivity.this.iv[1].setX(f6);
                                MainActivity.this.iv[1].setY(f6);
                                int i7 = -i2;
                                float f7 = (float) (i7 / 3);
                                MainActivity.this.iv[2].setX(f7);
                                MainActivity.this.iv[2].setY(f7);
                                float f8 = (float) i7;
                                MainActivity.this.iv[3].setX(f8);
                                MainActivity.this.iv[3].setY(f8);
                                return;
                            case 5:
                                float f9 = (float) i2;
                                MainActivity.this.iv[0].setX(f9);
                                MainActivity.this.iv[0].setY(f9);
                                float f10 = (float) (i2 / 2);
                                MainActivity.this.iv[1].setX(f10);
                                MainActivity.this.iv[1].setY(f10);
                                MainActivity.this.iv[2].setX(0.0f);
                                MainActivity.this.iv[2].setY(0.0f);
                                int i8 = -i2;
                                float f11 = (float) (i8 / 2);
                                MainActivity.this.iv[3].setX(f11);
                                MainActivity.this.iv[3].setY(f11);
                                float f12 = (float) i8;
                                MainActivity.this.iv[4].setX(f12);
                                MainActivity.this.iv[4].setY(f12);
                                return;
                            default:
                                return;
                        }
                    default:
                        return;
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                this.option = MainActivity.this.sharedPrefsxx.getInt("MoveOption", 1);
                MainActivity.this.framecount = MainActivity.this.sharedPrefsxx.getInt("FrameCount", 2);
            }
        });
    }

    public static void enableDisableView(View view, boolean z) {
        view.setEnabled(z);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                enableDisableView(viewGroup.getChildAt(i), z);
            }
        }
    }


    public void updateframe(int i) {
        this.main = (FrameLayout) findViewById(R.id.frame);
        this.main.removeAllViews();
        int i2 = 0;
        while (i2 < i) {
            ImageView imageView = new ImageView(getApplicationContext());
            if (Utils.transfer == 0) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(Utils.path3));
            }
            if (Utils.transfer == 1) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(Utils.path2));
            }
            imageView.setTag(Integer.valueOf(i2));
            int i3 = i2 + 1;
            imageView.setId(i3);
            imageView.setBackgroundColor(0);
            if (i2 == 1) {
                imageView.setAlpha(0.6f);
            }
            if (i2 == 2) {
                imageView.setAlpha(0.45f);
            }
            if (i2 == 3) {
                imageView.setAlpha(0.4f);
            }
            if (i2 == 4) {
                imageView.setAlpha(0.35f);
            }
            this.iv[i2] = imageView;
            imageView.setLayoutParams(new LayoutParams(-1, -1));
            this.main.addView(imageView);
            i2 = i3;
        }
    }


    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Uri data = intent.getData();
        if (data != null) {
            Intent intent2 = new Intent(this, ImageCroppingActivity.class);
            intent2.putExtra("path", data.toString());
            startActivity(intent2);
            finish();
            return;
        }
        Log.d("GalleryURI", "Gallery URI is NULL");
    }


    public void saveimage(Bitmap bitmap) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/");
        sb.append(getResources().getString(R.string.parent_folder_name));
        sb.append("/");
        sb.append(getResources().getString(R.string.image_folder));
        File file = new File(sb.toString());
        file.mkdirs();
        String format = new SimpleDateFormat("ddMMyyhh:mm:ss").format(new Date(System.currentTimeMillis()));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getResources().getString(R.string.nameimage));
        sb2.append("_");
        sb2.append(format);
        sb2.append(".jpg");
        String sb3 = sb2.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Filename");
        sb4.append(sb3);
        Log.e(sb4.toString(), "name");
        File file2 = new File(file, sb3);
        StringBuilder sb5 = new StringBuilder();
        sb5.append("Dir");
        sb5.append(file);
        Log.e(sb5.toString(), "directory");
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaScannerConnection.scanFile(this, new String[]{file2.toString()}, null, new OnScanCompletedListener() {
            public void onScanCompleted(String str, Uri uri) {
                StringBuilder sb = new StringBuilder();
                sb.append("Scanned ");
                sb.append(str);
                sb.append(":");
                Log.i("ExternalStorage", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("-> uri=");
                sb2.append(uri);
                Log.i("ExternalStorage", sb2.toString());
                Uri parse = Uri.parse(str);
                Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
                intent.putExtra("ImagePathUri", parse);
                intent.putExtra("isfrommain", "0");
                MainActivity.this.startActivity(intent);
            }
        });
    }


    public void updateInitialFrames(int i) {
        switch (i) {
            case 2:
                float f = (float) -100;
                this.iv[0].setX(f);
                this.iv[0].setY(f);
                float f2 = (float) 100;
                this.iv[1].setX(f2);
                this.iv[1].setY(f2);
                return;
            case 3:
                float f3 = (float) -100;
                this.iv[0].setX(f3);
                this.iv[0].setY(f3);
                this.iv[1].setX(0.0f);
                this.iv[1].setY(0.0f);
                float f4 = (float) 100;
                this.iv[2].setX(f4);
                this.iv[2].setY(f4);
                return;
            case 4:
                float f5 = (float) -100;
                this.iv[0].setX(f5);
                this.iv[0].setY(f5);
                float f6 = (float) -33;
                this.iv[1].setX(f6);
                this.iv[1].setY(f6);
                float f7 = (float) 33;
                this.iv[2].setX(f7);
                this.iv[2].setY(f7);
                float f8 = (float) 100;
                this.iv[3].setX(f8);
                this.iv[3].setY(f8);
                return;
            case 5:
                float f9 = (float) -100;
                this.iv[0].setX(f9);
                this.iv[0].setY(f9);
                float f10 = (float) -50;
                this.iv[1].setX(f10);
                this.iv[1].setY(f10);
                this.iv[2].setX(0.0f);
                this.iv[2].setY(0.0f);
                float f11 = (float) 50;
                this.iv[3].setX(f11);
                this.iv[3].setY(f11);
                float f12 = (float) 100;
                this.iv[4].setX(f12);
                this.iv[4].setY(f12);
                return;
            default:
                return;
        }
    }


    public void setButtonBG(int i, int i2) {
        switch (i) {
            case 1:
                switch (i2) {
                    case 2:
                        this.btndefaultframes.setBackgroundResource(R.drawable.frame_2_press);
                        this.btn3frames.setBackgroundResource(R.drawable.frame_3png);
                        this.btn4frames.setBackgroundResource(R.drawable.frame_4png);
                        this.btn5frames.setBackgroundResource(R.drawable.frame_5png);
                        return;
                    case 3:
                        this.btndefaultframes.setBackgroundResource(R.drawable.frame_2png);
                        this.btn3frames.setBackgroundResource(R.drawable.frame_3_press);
                        this.btn4frames.setBackgroundResource(R.drawable.frame_4png);
                        this.btn5frames.setBackgroundResource(R.drawable.frame_5png);
                        return;
                    case 4:
                        this.btndefaultframes.setBackgroundResource(R.drawable.frame_2png);
                        this.btn3frames.setBackgroundResource(R.drawable.frame_3png);
                        this.btn4frames.setBackgroundResource(R.drawable.frame_4_press);
                        this.btn5frames.setBackgroundResource(R.drawable.frame_5png);
                        return;
                    case 5:
                        this.btndefaultframes.setBackgroundResource(R.drawable.frame_2png);
                        this.btn3frames.setBackgroundResource(R.drawable.frame_3png);
                        this.btn4frames.setBackgroundResource(R.drawable.frame_4png);
                        this.btn5frames.setBackgroundResource(R.drawable.frame_5_press);
                        return;
                    default:
                        return;
                }
            case 2:
                switch (i2) {
                    case 1:
                        this.moveleftright.setBackgroundResource(R.drawable.ic_left_and_right_move_grey);
                        this.movediagonal.setBackgroundResource(R.drawable.ic_diagonal_move);
                        this.moveupdown.setBackgroundResource(R.drawable.ic_up_and_down_move);
                        return;
                    case 2:
                        this.moveleftright.setBackgroundResource(R.drawable.ic_left_and_right_move);
                        this.movediagonal.setBackgroundResource(R.drawable.ic_diagonal_move_grey);
                        this.moveupdown.setBackgroundResource(R.drawable.ic_up_and_down_move);
                        return;
                    case 3:
                        this.moveleftright.setBackgroundResource(R.drawable.ic_left_and_right_move);
                        this.movediagonal.setBackgroundResource(R.drawable.ic_diagonal_move);
                        this.moveupdown.setBackgroundResource(R.drawable.ic_up_and_down_move_grey);
                        return;
                    default:
                        return;
                }
            default:
                return;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, ImportImageActivity.class));
        finish();
    }
}
