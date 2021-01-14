package com.video.glitcheffect.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.video.glitcheffect.Ad_class;
import com.video.glitcheffect.R;
import com.video.glitcheffect.utils.Utils;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class PreviewActivity extends AppCompatActivity implements OnSeekBarChangeListener {
    static AlertDialog alert;

    Bundle b;
    ImageView btnPlayVideo;
    LinearLayout btnback;
    LinearLayout btndeleteimg;
    LinearLayout btnshareimg;
    int duration = 0;
    LinearLayout facebook;
    RelativeLayout flVideoView;
    Handler handler = new Handler();
    LinearLayout imagerelate;
    LinearLayout instagram;
    boolean isPlay = false;
    LinearLayout layli;
    ImageView previewimg;
    SeekBar seekVideo;
    Runnable seekrunnable = () -> {
        if (PreviewActivity.this.videoview.isPlaying()) {
            int currentPosition = PreviewActivity.this.videoview.getCurrentPosition();
            PreviewActivity.this.seekVideo.setProgress(currentPosition);
            try {
                TextView textView = PreviewActivity.this.tvStartVideo;
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(PreviewActivity.formatTimeUnit((long) currentPosition));
                textView.setText(sb.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (currentPosition == PreviewActivity.this.duration) {
                PreviewActivity.this.seekVideo.setProgress(0);
                PreviewActivity.this.tvStartVideo.setText("00:00");
                PreviewActivity.this.handler.removeCallbacks(PreviewActivity.this.seekrunnable);
                return;
            }
            PreviewActivity.this.handler.postDelayed(PreviewActivity.this.seekrunnable, 200);
            return;
        }
        PreviewActivity.this.seekVideo.setProgress(PreviewActivity.this.duration);
        try {
            TextView textView2 = PreviewActivity.this.tvStartVideo;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(PreviewActivity.formatTimeUnit((long) PreviewActivity.this.duration));
            textView2.setText(sb2.toString());
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        PreviewActivity.this.handler.removeCallbacks(PreviewActivity.this.seekrunnable);
    };
    TextView textimage;
    TextView textvideo;
    TextView tvEndVideo;
    TextView tvStartVideo;

     Uri uri;
    String videoPath;
    VideoView videoview;
    LinearLayout whatsapp;

    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d("sa","ds");
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d("sa","ds");
    }

    @Override
    @SuppressLint("WrongConstant")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_preview);

//        AdView mAdView = findViewById(R.id.adView);
//        Ad_class.showBanner(mAdView);


        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());


        this.flVideoView = (RelativeLayout) findViewById(R.id.flVideoView);
        this.facebook = (LinearLayout) findViewById(R.id.facebook);
        this.instagram = (LinearLayout) findViewById(R.id.instagram);
        this.whatsapp = (LinearLayout) findViewById(R.id.whatsapp);
        this.layli = (LinearLayout) findViewById(R.id.layli);
        this.btnshareimg = (LinearLayout) findViewById(R.id.btn_share_img);
        this.btndeleteimg = (LinearLayout) findViewById(R.id.btn_delete_img);
        this.btnback = (LinearLayout) findViewById(R.id.btn_back_preview_screen);
        this.btnPlayVideo = (ImageView) findViewById(R.id.btn_play);
        this.btnPlayVideo.setVisibility(8);
        this.imagerelate = (LinearLayout) findViewById(R.id.imagerelate);
        this.tvStartVideo = (TextView) findViewById(R.id.tvStartVideo);
        this.tvEndVideo = (TextView) findViewById(R.id.tvEndVideo);
        this.seekVideo = (SeekBar) findViewById(R.id.sbVideo);
        this.seekVideo.setOnSeekBarChangeListener(this);
        this.videoview = (VideoView) findViewById(R.id.videoview);
        this.previewimg = (ImageView) findViewById(R.id.preview_img);
        this.textimage = (TextView) findViewById(R.id.textimage);
        this.textvideo = (TextView) findViewById(R.id.textvideo);
        if (ImportImageActivity.imgvid == 0) {
            Utils.tabpass = 0;
            StringBuilder sb = new StringBuilder();
            sb.append("Photoshown ");
            sb.append(ImportImageActivity.imgvid);
            Log.e(sb.toString(), "photo");
            this.previewimg.setVisibility(View.VISIBLE);
            this.videoview.setVisibility(View.GONE);
            this.textimage.setVisibility(View.VISIBLE);
            this.textvideo.setVisibility(View.GONE);
            this.layli.setVisibility(View.INVISIBLE);
            this.btnPlayVideo.setVisibility(View.GONE);
            this.flVideoView.setVisibility(View.GONE);
            this.flVideoView.setClickable(false);
            this.uri = (Uri) getIntent().getParcelableExtra("ImagePathUri");
            this.previewimg.setImageURI(this.uri);
        }
        if (ImportImageActivity.imgvid == 1 || ImportImageActivity.imgvid == 2) {
            Utils.tabpass = 1;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Videoshown ");
            sb2.append(ImportImageActivity.imgvid);
            Log.e(sb2.toString(), "video");
            this.videoview.setVisibility(View.VISIBLE);
            this.previewimg.setVisibility(View.GONE);
            this.textimage.setVisibility(View.GONE);
            this.textvideo.setVisibility(View.VISIBLE);
            this.videoPath = getIntent().getStringExtra("VideoPath");
            StringBuilder sb3 = new StringBuilder();
            sb3.append("previewvideo ");
            sb3.append(this.videoPath);
            Log.e(sb3.toString(), " video");
            File file = new File(this.videoPath);
            Uri.parse(file.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(file.toString());
            sb4.append(" Got");
            Log.e("Video path:", sb4.toString());
            this.videoview.setVideoPath(this.videoPath);
            this.videoview.seekTo(this.seekVideo.getProgress());
            this.videoview.start();
            this.btnPlayVideo.setVisibility(View.GONE);
            this.handler.postDelayed(this.seekrunnable, 200);
        }
        this.flVideoView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!PreviewActivity.this.isPlay) {
                    PreviewActivity.this.videoview.pause();
                    PreviewActivity.this.handler.removeCallbacks(PreviewActivity.this.seekrunnable);
                    PreviewActivity.this.btnPlayVideo.setVisibility(View.VISIBLE);
                    Log.e("touched", "touch");
                } else {
                    PreviewActivity.this.videoview.seekTo(PreviewActivity.this.seekVideo.getProgress());
                    PreviewActivity.this.videoview.start();
                    PreviewActivity.this.handler.postDelayed(PreviewActivity.this.seekrunnable, 200);
                    PreviewActivity.this.videoview.setVisibility(View.VISIBLE);
                    PreviewActivity.this.btnPlayVideo.setVisibility(View.GONE);
                    Log.e("untouched", "touch");
                }
                PreviewActivity.this.isPlay = !PreviewActivity.this.isPlay;
            }
        });
        this.videoview.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                Toast.makeText(PreviewActivity.this, "can't play video", Toast.LENGTH_LONG);
                return true;
            }
        });
        this.videoview.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                PreviewActivity.this.duration = PreviewActivity.this.videoview.getDuration();
                PreviewActivity.this.seekVideo.setMax(PreviewActivity.this.duration);
                PreviewActivity.this.tvStartVideo.setText("00:00");
                try {
                    TextView textView = PreviewActivity.this.tvEndVideo;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(PreviewActivity.formatTimeUnit((long) PreviewActivity.this.duration));
                    textView.setText(sb.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        this.videoview.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                PreviewActivity.this.videoview.setVisibility(View.VISIBLE);
                PreviewActivity.this.btnPlayVideo.setVisibility(View.VISIBLE);
                PreviewActivity.this.videoview.seekTo(0);
                PreviewActivity.this.seekVideo.setProgress(0);
                PreviewActivity.this.tvStartVideo.setText("00:00");
                PreviewActivity.this.handler.removeCallbacks(PreviewActivity.this.seekrunnable);
                PreviewActivity.this.isPlay = true;
            }
        });
        this.btndeleteimg.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ImportImageActivity.imgvid == 0) {
                    PreviewActivity.this.createNotificationAccessDisabledError(PreviewActivity.this);
                }
                if (ImportImageActivity.imgvid == 1 || ImportImageActivity.imgvid == 2) {
                    PreviewActivity.this.createNotificationAccessDisabledError1(PreviewActivity.this);
                }
            }
        });
        this.btnback.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PreviewActivity.this.onBackPressed();
            }
        });
        this.btnshareimg.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ImportImageActivity.imgvid == 0) {
                    Intent intent = new Intent("com.glitcheffect.videoeditor.fileProvider");
                    Uri uriForFile = FileProvider.getUriForFile(PreviewActivity.this.getApplicationContext(), "com.glitcheffect.videoeditor.fileProvider", new File(PreviewActivity.this.uri.toString()));
                    intent.setDataAndType(uriForFile, PreviewActivity.this.getContentResolver().getType(uriForFile));
                    intent.setData(uriForFile);
                    Uri data = intent.getData();
                    Intent intent2 = new Intent("android.intent.action.SEND");
                    intent2.setType("image/*");
                    intent2.putExtra("android.intent.extra.STREAM", data);
                    PreviewActivity.this.startActivity(Intent.createChooser(intent2, "Share Image"));
                }
                if (ImportImageActivity.imgvid == 1 || ImportImageActivity.imgvid == 2) {
                    if (PreviewActivity.this.videoview != null && PreviewActivity.this.videoview.isPlaying()) {
                        PreviewActivity.this.videoview.pause();
                        PreviewActivity.this.handler.removeCallbacks(PreviewActivity.this.seekrunnable);
                        PreviewActivity.this.btnPlayVideo.setVisibility(View.VISIBLE);
                    }
                    String string = PreviewActivity.this.getResources().getString(R.string.app_name);
                    Intent intent3 = new Intent("android.intent.action.SEND");
                    intent3.putExtra("android.intent.extra.SUBJECT", string);
                    Uri fromFile = Uri.fromFile(new File(PreviewActivity.this.videoPath));
                    intent3.setType("video/*");
                    intent3.putExtra("android.intent.extra.STREAM", fromFile);
                    PreviewActivity.this.startActivity(Intent.createChooser(intent3, "Share Video"));
                }
            }
        });
        this.facebook.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!PreviewActivity.this.check("com.facebook.katana")) {
                    try {
                        PreviewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.facebook.katana")));
                    } catch (ActivityNotFoundException unused) {
                        PreviewActivity gAPPreviewActivity = PreviewActivity.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("https://play.google.com/store/apps/details?id=");
                        sb.append(PreviewActivity.this.getPackageName());
                        gAPPreviewActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
                    }
                } else if (ImportImageActivity.imgvid == 0) {
                    String string = PreviewActivity.this.getResources().getString(R.string.app_name);
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.putExtra("android.intent.extra.SUBJECT", string);
                    Uri uriForFile = FileProvider.getUriForFile(PreviewActivity.this, "com.glitcheffect.videoeditor.fileProvider", new File(PreviewActivity.this.uri.toString()));
                    intent.setType("image/*");
                    intent.setPackage("com.facebook.katana");
                    intent.putExtra("android.intent.extra.STREAM", uriForFile);
                    try {
                        PreviewActivity.this.startActivity(Intent.createChooser(intent, "Share Image"));
                    } catch (ActivityNotFoundException unused2) {
                        Log.d("sa","ds");
                    }
                } else if (ImportImageActivity.imgvid == 1 || ImportImageActivity.imgvid == 2) {
                    if (PreviewActivity.this.videoview != null && PreviewActivity.this.videoview.isPlaying()) {
                        PreviewActivity.this.videoview.pause();
                        PreviewActivity.this.handler.removeCallbacks(PreviewActivity.this.seekrunnable);
                        PreviewActivity.this.btnPlayVideo.setVisibility(View.VISIBLE);
                    }
                    String string2 = PreviewActivity.this.getResources().getString(R.string.app_name);
                    Intent intent2 = new Intent("android.intent.action.SEND");
                    intent2.putExtra("android.intent.extra.SUBJECT", string2);
                    Uri fromFile = Uri.fromFile(new File(PreviewActivity.this.videoPath));
                    intent2.setType("video/*");
                    intent2.setPackage("com.facebook.katana");
                    intent2.putExtra("android.intent.extra.STREAM", fromFile);
                    try {
                        PreviewActivity.this.startActivity(Intent.createChooser(intent2, "Share Video"));
                    } catch (ActivityNotFoundException unused3) {
                        Log.d("sa","ds");
                    }
                }
            }
        });
        this.instagram.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!PreviewActivity.this.check("com.instagram.android")) {
                    try {
                        PreviewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.instagram.android")));
                    } catch (ActivityNotFoundException unused) {
                        PreviewActivity gAPPreviewActivity = PreviewActivity.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("https://play.google.com/store/apps/details?id=");
                        sb.append(PreviewActivity.this.getPackageName());
                        gAPPreviewActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
                    }
                } else if (ImportImageActivity.imgvid == 0) {
                    String string = PreviewActivity.this.getResources().getString(R.string.app_name);
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.putExtra("android.intent.extra.SUBJECT", string);
                    Uri uriForFile = FileProvider.getUriForFile(PreviewActivity.this, "com.glitcheffect.videoeditor.fileProvider", new File(PreviewActivity.this.uri.toString()));
                    intent.setType("image/*");
                    intent.setPackage("com.instagram.android");
                    intent.putExtra("android.intent.extra.STREAM", uriForFile);
                    try {
                        PreviewActivity.this.startActivity(Intent.createChooser(intent, "Share Image"));
                    } catch (ActivityNotFoundException unused2) {
                        Log.d("sa","ds");
                    }
                } else if (ImportImageActivity.imgvid == 1 || ImportImageActivity.imgvid == 2) {
                    if (PreviewActivity.this.videoview != null && PreviewActivity.this.videoview.isPlaying()) {
                        PreviewActivity.this.videoview.pause();
                        PreviewActivity.this.handler.removeCallbacks(PreviewActivity.this.seekrunnable);
                        PreviewActivity.this.btnPlayVideo.setVisibility(View.VISIBLE);
                    }
                    String string2 = PreviewActivity.this.getResources().getString(R.string.app_name);
                    Intent intent2 = new Intent("android.intent.action.SEND");
                    intent2.putExtra("android.intent.extra.SUBJECT", string2);
                    Uri fromFile = Uri.fromFile(new File(PreviewActivity.this.videoPath));
                    intent2.setType("video/*");
                    intent2.setPackage("com.instagram.android");
                    intent2.putExtra("android.intent.extra.STREAM", fromFile);
                    try {
                        PreviewActivity.this.startActivity(Intent.createChooser(intent2, "Share Video"));
                    } catch (ActivityNotFoundException unused3) {
                        Log.d("sa","ds");
                    }
                }
            }
        });
        this.whatsapp.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!PreviewActivity.this.check("com.whatsapp")) {
                    try {
                        PreviewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.whatsapp")));
                    } catch (ActivityNotFoundException unused) {
                        PreviewActivity gAPPreviewActivity = PreviewActivity.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("https://play.google.com/store/apps/details?id=");
                        sb.append(PreviewActivity.this.getPackageName());
                        gAPPreviewActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
                    }
                } else if (ImportImageActivity.imgvid == 0) {
                    String string = PreviewActivity.this.getResources().getString(R.string.app_name);
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.putExtra("android.intent.extra.SUBJECT", string);
                    Uri uriForFile = FileProvider.getUriForFile(PreviewActivity.this, "com.glitcheffect.videoeditor.fileProvider", new File(PreviewActivity.this.uri.toString()));
                    intent.setType("image/*");
                    intent.setPackage("com.whatsapp");
                    intent.putExtra("android.intent.extra.STREAM", uriForFile);
                    try {
                        PreviewActivity.this.startActivity(Intent.createChooser(intent, "Share Image"));
                    } catch (ActivityNotFoundException unused2) {
                        Log.d("sa","ds");
                    }
                } else if (ImportImageActivity.imgvid == 1 || ImportImageActivity.imgvid == 2) {
                    if (PreviewActivity.this.videoview != null && PreviewActivity.this.videoview.isPlaying()) {
                        PreviewActivity.this.videoview.pause();
                        PreviewActivity.this.handler.removeCallbacks(PreviewActivity.this.seekrunnable);
                        PreviewActivity.this.btnPlayVideo.setVisibility(View.VISIBLE);
                    }
                    String string2 = PreviewActivity.this.getResources().getString(R.string.app_name);
                    Intent intent2 = new Intent("android.intent.action.SEND");
                    intent2.putExtra("android.intent.extra.SUBJECT", string2);
                    Uri fromFile = Uri.fromFile(new File(PreviewActivity.this.videoPath));
                    intent2.setType("video/*");
                    intent2.setPackage("com.whatsapp");
                    intent2.putExtra("android.intent.extra.STREAM", fromFile);
                    try {
                        PreviewActivity.this.startActivity(Intent.createChooser(intent2, "Share Video"));
                    } catch (ActivityNotFoundException unused3) {
                        Log.d("sa","ds");
                    }
                }
            }
        });


    }

    public static String formatTimeUnit(long j) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j)))});
    }


    public boolean check(String str) {
        try {
            getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public void createNotificationAccessDisabledError(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.message1).setCancelable(true).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                File file = new File(PreviewActivity.this.uri.toString());
                if (file.exists() && file.delete()) {
                    PreviewActivity.this.startActivity(new Intent(PreviewActivity.this.getApplicationContext(), MyCreationActivity.class));
                    dialogInterface.dismiss();
                }
            }
        });
        alert = builder.create();
        alert.show();
        alert.getButton(-2).setTextColor(getResources().getColor(R.color.colorPrimary));
        alert.getButton(-1).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    public void createNotificationAccessDisabledError1(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.message2).setCancelable(true).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                File file = new File(PreviewActivity.this.videoPath);
                if (file.exists() && file.delete()) {
                    PreviewActivity.this.startActivity(new Intent(PreviewActivity.this.getApplicationContext(), MyCreationActivity.class));
                    dialogInterface.dismiss();
                }
            }
        });
        alert = builder.create();
        alert.show();
        alert.getButton(-2).setTextColor(getResources().getColor(R.color.colorPrimary));
        alert.getButton(-1).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MyCreationActivity.class));
        finish();


    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            this.videoview.seekTo(i);
            try {
                TextView textView = this.tvStartVideo;
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(formatTimeUnit((long) i));
                textView.setText(sb.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (ImportImageActivity.imgvid == 0) {
            this.btnPlayVideo.setVisibility(View.GONE);
        }
        this.btnPlayVideo.bringToFront();
        this.isPlay = false;
        this.videoview.seekTo(0);
        this.seekVideo.setProgress(0);
        this.tvStartVideo.setText("00:00");
    }


}
