package com.video.glitcheffect.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.video.glitcheffect.Ad_class;
import com.video.glitcheffect.R;
import com.video.glitcheffect.adapters.FilterAdapter;
import com.video.glitcheffect.adapters.RenderAdapter;
import com.video.glitcheffect.interfaces.ItemfilterclickInterface;
import com.video.glitcheffect.render.Binary;
import com.video.glitcheffect.render.ChromaRender1;
import com.video.glitcheffect.render.ChromaRender2;
import com.video.glitcheffect.render.DrunkRender2;
import com.video.glitcheffect.render.DrunkenRender;
import com.video.glitcheffect.render.DynamicRender;
import com.video.glitcheffect.render.LookupRender;
import com.video.glitcheffect.render.NoFilterRender;
import com.video.glitcheffect.render.OldMoveRender;
import com.video.glitcheffect.render.SinMov1;
import com.video.glitcheffect.render.SinMov2;
import com.video.glitcheffect.render.SlicerRender;
import com.video.glitcheffect.render.TriangleRender;
import com.video.glitcheffect.render.WobbleRender;
import com.video.glitcheffect.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;

import cn.ezandroid.ezfilter.EZFilter;
import cn.ezandroid.ezfilter.core.FilterRender;
import cn.ezandroid.ezfilter.core.GLRender;
import cn.ezandroid.ezfilter.core.RenderPipeline;
import cn.ezandroid.ezfilter.core.environment.SurfaceFitView;
import cn.ezandroid.ezfilter.media.record.ISupportRecord;
import cn.ezandroid.ezfilter.video.VideoInput;
import cn.ezandroid.ezfilter.video.player.IMediaPlayer;
import cn.ezandroid.ezfilter.video.player.IMediaPlayer.OnCompletionListener;
import cn.ezandroid.ezfilter.video.player.IMediaPlayer.OnPreparedListener;

public class GalleryVideoActivityGAP extends BaseActivity {
    public static int adapteposfilter;
    static AlertDialog alert;
    public static int posetion;
    public static SeekBar sikBar;
    public static int star11;
    public static int star21;
    FilterAdapter gapfilteradapter;
    RenderAdapter gaprenderadapter;
    int abc;
    
    ArrayList<FilterRender> arrfilter;
    ArrayList<FilterRender> arrrender;
    boolean audiorecording = true;
    Binary binary = new Binary();
    int center;
    int check = -1;
    ChromaRender1 chromaRender1 = new ChromaRender1();
    ChromaRender2 chromaRender2 = new ChromaRender2();

    Handler customHandler = new Handler();
    DrunkRender2 drunkRender2 = new DrunkRender2();
    DrunkenRender drunkenRender = new DrunkenRender();
    DynamicRender dynamicRender = new DynamicRender();
    LinearLayout filtereffect;
    int[] filterimeges = {R.drawable.duo_14, R.drawable.duo_1, R.drawable.duo_2, R.drawable.duo_3, R.drawable.duo_4, R.drawable.duo_5, R.drawable.duo_17, R.drawable.duo_7, R.drawable.duo_8, R.drawable.duo_9, R.drawable.duo_10, R.drawable.duo_11, R.drawable.duo_12, R.drawable.duo_13, R.drawable.duo_14, R.drawable.duo_15, R.drawable.duo_16, R.drawable.duo_17, R.drawable.duo_18, R.drawable.duo_19, R.drawable.duo_20, R.drawable.filter2, R.drawable.filter3, R.drawable.filter6, R.drawable.filter7, R.drawable.filter8, R.drawable.filter14, R.drawable.filter15, R.drawable.filter16, R.drawable.filter17, R.drawable.filter18, R.drawable.filter20, R.drawable.filter21, R.drawable.filter22, R.drawable.filter23, R.drawable.filter24, R.drawable.filter25, R.drawable.filter26, R.drawable.filter27, R.drawable.filter28, R.drawable.filter29, R.drawable.filter30, R.drawable.filter31, R.drawable.filter32, R.drawable.filter33, R.drawable.filter34, R.drawable.filter35, R.drawable.filter36, R.drawable.filter37, R.drawable.filter38, R.drawable.filter39, R.drawable.filter40, R.drawable.filter41, R.drawable.filter42, R.drawable.filter43, R.drawable.filter44, R.drawable.filter45, R.drawable.trippy_1, R.drawable.trippy_2, R.drawable.trippy_3, R.drawable.trippy_4, R.drawable.trippy_5, R.drawable.trippy_6, R.drawable.trippy_7, R.drawable.trippy_8, R.drawable.trippy_9, R.drawable.trippy_10, R.drawable.trippy_11, R.drawable.trippy_12, R.drawable.trippy_13, R.drawable.trippy_14, R.drawable.trippy_15, R.drawable.trippy_16, R.drawable.trippy_17, R.drawable.trippy_18};
    ImageView filterpress;
    int filtertype = 1;
    ImageView filterunpress;
    Animation focus;
    Boolean handactive;
    LinearLayout head1;
    public ItemfilterclickInterface itemfilterclick = new ItemfilterclickInterface() {
        public void onfilterClick(int i, FilterRender filterRender) {
            GalleryVideoActivityGAP.adapteposfilter = i;
            GalleryVideoActivityGAP.sikBar.setProgress(50);
            GalleryVideoActivityGAP.this.seekfilter();
            if (i == 0) {
                GalleryVideoActivityGAP.this.mRenderPipeline.removeFilterRender(GalleryVideoActivityGAP.this.mCurrentRenderfilter);
                GalleryVideoActivityGAP.this.mCurrentRenderfilter = null;
            } else if (GalleryVideoActivityGAP.this.mCurrentRenderfilter != null) {
                GalleryVideoActivityGAP.this.lookupRender.image(GalleryVideoActivityGAP.this.filterimeges[i]);
                GalleryVideoActivityGAP.this.lookupRender.adjust(0.5f);
            } else {
                GalleryVideoActivityGAP.this.lookupRender = new LookupRender(GalleryVideoActivityGAP.this, GalleryVideoActivityGAP.this.filterimeges[i]);
                GalleryVideoActivityGAP.this.mCurrentRenderfilter = GalleryVideoActivityGAP.this.lookupRender;
                if (!GalleryVideoActivityGAP.this.handactive.booleanValue()) {
                    GalleryVideoActivityGAP.this.mRenderPipeline.addFilterRender(2, GalleryVideoActivityGAP.this.lookupRender);
                }
            }
        }
    };
    public ItemfilterclickInterface itemrenderclick = new ItemfilterclickInterface() {
        public void onfilterClick(int i, FilterRender filterRender) {
            StringBuilder sb = new StringBuilder();
            sb.append("posetion ");
            sb.append(i);
            LogUtils.log(sb.toString());
            GalleryVideoActivityGAP.sikBar.setProgress(50);
            GalleryVideoActivityGAP.posetion = i;
            GalleryVideoActivityGAP.this.seekrender();
            if (i == 0) {
                GalleryVideoActivityGAP.this.mRenderPipeline.clearFilterRenders();
                GalleryVideoActivityGAP.this.mRenderPipeline.removeFilterRender(GalleryVideoActivityGAP.this.mCurrentRender);
                GalleryVideoActivityGAP.this.mCurrentRender = null;
            } else if (GalleryVideoActivityGAP.this.mCurrentRender != null) {
                GalleryVideoActivityGAP.this.mOldRender = GalleryVideoActivityGAP.this.mCurrentRender;
                GalleryVideoActivityGAP.this.mCurrentRender = filterRender;
                if (!GalleryVideoActivityGAP.this.handactive.booleanValue()) {
                    GalleryVideoActivityGAP.this.mRenderPipeline.removeFilterRender(GalleryVideoActivityGAP.this.mOldRender);
                    GalleryVideoActivityGAP.this.mRenderPipeline.addFilterRender(GalleryVideoActivityGAP.this.mCurrentRender);
                }
            } else {
                GalleryVideoActivityGAP.this.mOldRender = GalleryVideoActivityGAP.this.mCurrentRender;
                GalleryVideoActivityGAP.this.mCurrentRender = filterRender;
                GalleryVideoActivityGAP.this.mRenderPipeline.addFilterRender(GalleryVideoActivityGAP.this.mCurrentRender);
            }
        }
    };
    LookupRender lookupRender = new LookupRender(this, R.drawable.duo_1);

    public FilterRender mCurrentRender;
    FilterRender mCurrentRenderfilter;

    public FilterRender mOldRender;
    
    
    private RelativeLayout mRecordButton;
    RenderPipeline mRenderPipeline;
    SurfaceFitView mRenderView;
    ISupportRecord mSupportRecord;
    ImageView mic;
    Uri myUri;
    NoFilterRender noFilterRender = new NoFilterRender();
    OldMoveRender oldMoveRender = new OldMoveRender();
    String path = null;
    ImageView press;
    RecyclerView rcfilter;
    RecyclerView rcrender;
    boolean recordstatus = false;
    LinearLayout reject;
    LinearLayout rendereffect;
    int[] renderimages = {R.drawable.no_filter, R.drawable.fil_0, R.drawable.fil_1, R.drawable.fil_2, R.drawable.fil_3, R.drawable.fil_4, R.drawable.fil_5, R.drawable.fil_6, R.drawable.fil_7, R.drawable.fil_8, R.drawable.fil_9};
    LinearLayout seekrelate;
    int seekfilter = 50;
    int seekrender = 50;
    SinMov1 sinMov1 = new SinMov1();
    SinMov2 sinMov2 = new SinMov2();
    SlicerRender slicerRender = new SlicerRender();

    public long startTime = 0;
    RelativeLayout surfacerel;
    ImageView tap;
    long timeInMilliseconds = 0;
    long timeSwapBuff = 0;
    TextView timerValue;
    TriangleRender triangleRender = new TriangleRender();
    ImageView unpress;
    private Runnable updateTimerThread = new Runnable() {
        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        public void run() {
            GalleryVideoActivityGAP.this.timeInMilliseconds = SystemClock.uptimeMillis() - GalleryVideoActivityGAP.this.startTime;
            GalleryVideoActivityGAP.this.updatedTime = GalleryVideoActivityGAP.this.timeSwapBuff + GalleryVideoActivityGAP.this.timeInMilliseconds;
            int i = (int) (GalleryVideoActivityGAP.this.updatedTime / 1000);
            int i2 = i / 60;
            int i3 = i % 60;
            TextView textView = GalleryVideoActivityGAP.this.timerValue;
            StringBuilder sb = new StringBuilder();
            sb.append("0");
            sb.append(i2);
            sb.append(":");
            sb.append(String.format("%02d", new Object[]{Integer.valueOf(i3)}));
            textView.setText(sb.toString());
            GalleryVideoActivityGAP.this.customHandler.postDelayed(this, 0);
        }
    };
    long updatedTime = 0;
    LinearLayout vdorotation;
    int warning = 0;
    WobbleRender wobbleRender = new WobbleRender();
    ImageView yesrecording;

    @Override
    @SuppressLint({"ClickableViewAccessibility"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_gallery_video);

//        AdView mAdView = findViewById(R.id.adView);
//        Ad_class.showBanner(mAdView);


        this.handactive = Boolean.valueOf(false);
        this.mRenderView = (SurfaceFitView) findViewById(R.id.render_view);
        this.rcrender = (RecyclerView) findViewById(R.id.rc_render);
        this.rcfilter = (RecyclerView) findViewById(R.id.rc_filter);
        this.yesrecording = (ImageView) findViewById(R.id.yes_recording);
        this.reject = (LinearLayout) findViewById(R.id.reject);
        this.timerValue = (TextView) findViewById(R.id.timer);
        sikBar = (SeekBar) findViewById(R.id.entensity);
        sikBar.setProgress(50);
        this.filterpress = (ImageView) findViewById(R.id.filter_press);
        this.filterunpress = (ImageView) findViewById(R.id.filter_unpress);
        this.unpress = (ImageView) findViewById(R.id.unpress);
        this.press = (ImageView) findViewById(R.id.press);
        this.head1 = (LinearLayout) findViewById(R.id.head1);
        this.head1.setVisibility(View.VISIBLE);
        this.rendereffect = (LinearLayout) findViewById(R.id.render_effect);
        this.filtereffect = (LinearLayout) findViewById(R.id.filter_effect1);
        this.mCurrentRender = this.noFilterRender;
        this.tap = (ImageView) findViewById(R.id.tep);
        posetion = 0;
        gaprenderadapter.selectpos = 0;
        adapteposfilter = 0;
        gapfilteradapter.filterPosition = 0;
        star11 = 0;
        star21 = 0;
        this.seekrelate = (LinearLayout) findViewById(R.id.seek_ralate);
        this.tap.setImageResource(R.drawable.hand);
        this.tap.setPadding(15, 15, 15, 15);
        this.surfacerel = (RelativeLayout) findViewById(R.id.surfacere2);
        this.mic = (ImageView) findViewById(R.id.voice);
        this.focus = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.focus);
        this.mRecordButton = (RelativeLayout) findViewById(R.id.record);
        this.rcrender.setVisibility(View.VISIBLE);
        makerenderRecycle();
        String string = getIntent().getExtras().getString("live");
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getPath());
        sb.append("/");
        sb.append(getResources().getString(R.string.parent_folder_name));
        sb.append("/");
        sb.append(getResources().getString(R.string.video_folder));
        sb.append("/video");
        sb.append(System.currentTimeMillis());
        sb.append(".mp4");
        this.path = sb.toString();
        this.myUri = Uri.parse(string);
        this.mRenderPipeline = EZFilter.input(this.myUri).setLoop(true).enableRecord(this.path, true, true).setPreparedListener(new OnPreparedListener() {
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                Log.e("VideoFilterActivity", "onPrepared");
            }
        }).setCompletionListener(new OnCompletionListener() {
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                Log.e("VideoFilterActivity", "onCompletion");
            }
        }).into(this.mRenderView);
        this.myUri = Uri.parse(this.path);
        refreshAndroidGallery(this.myUri);
        for (GLRender gLRender : this.mRenderPipeline.getEndPointRenders()) {
            if (gLRender instanceof ISupportRecord) {
                this.mSupportRecord = (ISupportRecord) gLRender;
            }
        }
        this.reject.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                if (GalleryVideoActivityGAP.this.check == 0) {
                    GalleryVideoActivityGAP.this.createNotificationAccessDisabledError(GalleryVideoActivityGAP.this);
                    return;
                }

                exitdialog(GalleryVideoActivityGAP.this);
            }
        });
        this.mRecordButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (GalleryVideoActivityGAP.this.recordstatus) {
                    GalleryVideoActivityGAP.this.stopRecording();
                    return;
                }
                GalleryVideoActivityGAP.this.startRecording();
                GalleryVideoActivityGAP.this.recordstatus = true;
            }
        });
        this.mic.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (GalleryVideoActivityGAP.this.audiorecording) {
                    GalleryVideoActivityGAP.this.mSupportRecord.enableRecordAudio(false);
                    GalleryVideoActivityGAP.this.mic.setImageResource(R.drawable.ic_microphone_off);
                    GalleryVideoActivityGAP.this.audiorecording = false;
                    return;
                }
                GalleryVideoActivityGAP.this.mSupportRecord.enableRecordAudio(true);
                GalleryVideoActivityGAP.this.mic.setImageResource(R.drawable.ic_microphone_on);
                GalleryVideoActivityGAP.this.audiorecording = true;
            }
        });
        this.rendereffect.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                GalleryVideoActivityGAP.this.filtertype = 1;
                GalleryVideoActivityGAP.this.rcfilter.setVisibility(8);
                GalleryVideoActivityGAP.this.rcrender.setVisibility(0);
                GalleryVideoActivityGAP.this.press.setVisibility(0);
                GalleryVideoActivityGAP.this.unpress.setVisibility(8);
                GalleryVideoActivityGAP.this.makerenderRecycle();
                GalleryVideoActivityGAP.this.filterpress.setVisibility(8);
                GalleryVideoActivityGAP.this.filterunpress.setVisibility(0);
                GalleryVideoActivityGAP.this.seekrender();
            }
        });
        this.filtereffect.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                GalleryVideoActivityGAP.this.filtertype = 2;
                GalleryVideoActivityGAP.this.rcfilter.setVisibility(0);
                GalleryVideoActivityGAP.this.rcrender.setVisibility(8);
                GalleryVideoActivityGAP.this.press.setVisibility(8);
                GalleryVideoActivityGAP.this.unpress.setVisibility(0);
                GalleryVideoActivityGAP.this.filterpress.setVisibility(0);
                GalleryVideoActivityGAP.this.filterunpress.setVisibility(8);
                GalleryVideoActivityGAP.this.makefilterRecycle();
                GalleryVideoActivityGAP.this.seekfilter();
            }
        });
        this.tap.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (GalleryVideoActivityGAP.this.handactive.booleanValue()) {
                    if (GalleryVideoActivityGAP.this.filtertype == 1) {
                        GalleryVideoActivityGAP.this.updaterender(GalleryVideoActivityGAP.this.seekrender);
                        StringBuilder sb = new StringBuilder();
                        sb.append("secondif ");
                        sb.append(GalleryVideoActivityGAP.this.handactive.toString());
                        Log.e(sb.toString(), NotificationCompat.CATEGORY_MESSAGE);
                    } else {
                        GalleryVideoActivityGAP.this.updatefilter(GalleryVideoActivityGAP.this.seekfilter);
                    }
                    GalleryVideoActivityGAP.this.tap.setImageResource(R.drawable.hand);
                    GalleryVideoActivityGAP.this.tap.setPadding(15, 15, 15, 15);
                    GalleryVideoActivityGAP.this.handactive = Boolean.valueOf(false);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("secondelse ");
                    sb2.append(GalleryVideoActivityGAP.this.handactive.toString());
                    Log.e(sb2.toString(), NotificationCompat.CATEGORY_MESSAGE);
                    return;
                }
                GalleryVideoActivityGAP.this.handactive = Boolean.valueOf(true);
                GalleryVideoActivityGAP.this.tap.setImageResource(R.drawable.hand_press);
                GalleryVideoActivityGAP.this.tap.setPadding(15, 15, 15, 15);
                GalleryVideoActivityGAP.this.mRenderPipeline.removeFilterRender(GalleryVideoActivityGAP.this.mCurrentRender);
                GalleryVideoActivityGAP.this.mRenderPipeline.removeFilterRender(GalleryVideoActivityGAP.this.mCurrentRenderfilter);
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Handtouchd ");
                sb3.append(GalleryVideoActivityGAP.this.handactive.toString());
                Log.e(sb3.toString(), NotificationCompat.CATEGORY_MESSAGE);
            }
        });
        final int width = getWindowManager().getDefaultDisplay().getWidth();
        this.center = width / 3;
        getWindowManager().getDefaultDisplay().getHeight();
        this.surfacerel.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        if (GalleryVideoActivityGAP.this.filtertype != 1) {
                            int x = (int) motionEvent.getX();
                            motionEvent.getY();
                            int i = width / 100;
                            GalleryVideoActivityGAP.sikBar.setMax(100);
                            GalleryVideoActivityGAP.this.abc = x / i;
                            GalleryVideoActivityGAP.this.updatefilter(GalleryVideoActivityGAP.this.abc);
                            GalleryVideoActivityGAP.sikBar.setProgress(GalleryVideoActivityGAP.this.abc);
                            StringBuilder sb = new StringBuilder();
                            sb.append("Down ");
                            sb.append(GalleryVideoActivityGAP.sikBar);
                            Log.e(sb.toString(), "");
                            break;
                        } else {
                            int x2 = (int) motionEvent.getX();
                            motionEvent.getY();
                            int i2 = width / 100;
                            GalleryVideoActivityGAP.sikBar.setMax(100);
                            GalleryVideoActivityGAP.this.abc = x2 / i2;
                            GalleryVideoActivityGAP.this.updaterender(GalleryVideoActivityGAP.this.abc);
                            GalleryVideoActivityGAP.sikBar.setProgress(GalleryVideoActivityGAP.this.abc);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Down ");
                            sb2.append(GalleryVideoActivityGAP.sikBar);
                            Log.e(sb2.toString(), "");
                            break;
                        }
                    case 1:
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("checked");
                        sb3.append(GalleryVideoActivityGAP.this.handactive.toString());
                        Log.e(sb3.toString(), "");
                        if (GalleryVideoActivityGAP.this.handactive.booleanValue()) {
                            GalleryVideoActivityGAP.this.mRenderPipeline.clearFilterRenders();
                            break;
                        }
                        break;
                    case 2:
                        if (GalleryVideoActivityGAP.this.filtertype != 1) {
                            int x3 = (int) motionEvent.getX();
                            motionEvent.getY();
                            int i3 = width / 100;
                            GalleryVideoActivityGAP.sikBar.setMax(100);
                            Log.d("Move ", "Action was MOVE");
                            GalleryVideoActivityGAP.this.abc = x3 / i3;
                            GalleryVideoActivityGAP.this.updatefilter(GalleryVideoActivityGAP.this.abc);
                            GalleryVideoActivityGAP.sikBar.setProgress(GalleryVideoActivityGAP.this.abc);
                            break;
                        } else {
                            int x4 = (int) motionEvent.getX();
                            motionEvent.getY();
                            int i4 = width / 100;
                            GalleryVideoActivityGAP.sikBar.setMax(100);
                            Log.d("Move ", "Action was MOVE");
                            GalleryVideoActivityGAP.this.abc = x4 / i4;
                            GalleryVideoActivityGAP.this.updaterender(GalleryVideoActivityGAP.this.abc);
                            GalleryVideoActivityGAP.sikBar.setProgress(GalleryVideoActivityGAP.this.abc);
                            break;
                        }
                }
                return true;
            }
        });
        if (this.audiorecording) {
            this.mic.setImageResource(R.drawable.ic_microphone_on);
        } else {
            this.mic.setImageResource(R.drawable.ic_microphone_off);
        }
    }

    public void seekrender() {
        if (posetion == 0 || gaprenderadapter.selectpos == 0) {
            sikBar.setVisibility(View.GONE);
        } else {
            sikBar.setVisibility(View.VISIBLE);
        }
    }

    public void seekfilter() {
        if (adapteposfilter == 0 || gapfilteradapter.filterPosition == 0) {
            sikBar.setVisibility(View.GONE);
        } else {
            sikBar.setVisibility(View.VISIBLE);
        }
    }


    public void startRecording() {
        this.check = 0;
        this.mSupportRecord.startRecording();
        this.mic.setVisibility(View.GONE);
        this.yesrecording.setVisibility(View.VISIBLE);
        this.startTime = SystemClock.uptimeMillis();
        this.customHandler.postDelayed(this.updateTimerThread, 0);
        this.mRecordButton.startAnimation(this.focus);
    }


    public void stopRecording() {
        ImportImageActivity.imgvid = 2;
        if (this.mSupportRecord != null) {
            this.seekrender = 0;
            this.seekfilter = 0;
            this.mSupportRecord.stopRecording();
            sikBar.setVisibility(View.GONE);
            this.timeSwapBuff += this.timeInMilliseconds;
            this.customHandler.removeCallbacks(this.updateTimerThread);
            this.mRecordButton.clearAnimation();
            this.yesrecording.setVisibility(View.INVISIBLE);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Log.d("adsa","sd");
                }
            }, 2000);
            if (this.warning == 1) {
                this.warning = 0;
                Log.e("RejectPressed", "pressed");
                finish();
                return;
            }
            Intent intent = new Intent(this, MyCreationActivity.class);
            startActivity(intent);
            finish();
//            finish();
//            Intent intent = new Intent(this, PreviewActivity.class);
//            intent.putExtra("VideoPath", this.myUri.toString());
//            intent.putExtra("isfrommain1", "0");
//            StringBuilder sb = new StringBuilder();
//            sb.append("send");
//            sb.append(this.myUri);
//            Log.e(sb.toString(), "string");
//            startActivity(intent);
        }
    }


    public void makerenderRecycle() {
        addrenderclass();
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, 0, false);
        this.gaprenderadapter = new RenderAdapter(this, this.arrrender, this.itemrenderclick, this.renderimages);
        if (star11 == 0) {
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
        } else {
            linearLayoutManager.scrollToPositionWithOffset(posetion, this.center);
        }
        this.rcrender.setLayoutManager(linearLayoutManager);
        this.rcrender.setAdapter(this.gaprenderadapter);
    }


    public void makefilterRecycle() {
        filterglitchcamera(75);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, 0, false);
        this.gapfilteradapter = new FilterAdapter(this, this.arrfilter, this.itemfilterclick, this.filterimeges);
        if (star21 == 0) {
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
        } else {
            linearLayoutManager.scrollToPositionWithOffset(adapteposfilter, this.center);
        }
        this.rcfilter.setLayoutManager(linearLayoutManager);
        this.rcfilter.setAdapter(this.gapfilteradapter);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (this.check == 0) {
            this.warning = 1;
            stopRecording();
        }
        if (this.mRenderPipeline != null) {
            ((VideoInput) this.mRenderPipeline.getStartPointRender()).pause();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        gaprenderadapter.selectpos = 0;
        gapfilteradapter.filterPosition = 0;
        adapteposfilter = 0;
        posetion = 0;
        sikBar.setVisibility(View.GONE);
        if (this.mRenderPipeline != null) {
            ((VideoInput) this.mRenderPipeline.getStartPointRender()).start();
        }
    }

    public void addrenderclass() {
        this.arrrender = new ArrayList<>();
        this.arrrender.add(this.noFilterRender);
        this.arrrender.add(this.binary);
        this.arrrender.add(this.chromaRender1);
        this.arrrender.add(this.chromaRender2);
        this.arrrender.add(this.drunkenRender);
        this.arrrender.add(this.drunkRender2);
        this.arrrender.add(this.dynamicRender);
        this.arrrender.add(this.oldMoveRender);
        this.arrrender.add(this.sinMov2);
        this.arrrender.add(this.triangleRender);
        this.arrrender.add(this.wobbleRender);
    }

    public void filterglitchcamera(int i) {
        this.arrfilter = new ArrayList<>();
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 == 0) {
                this.arrfilter.add(new NoFilterRender());
            } else {
                this.arrfilter.add(new LookupRender(this, this.filterimeges[i2]));
            }
        }
    }

    public void createNotificationAccessDisabledError(Activity activity) {
        Builder builder = new Builder(activity);
        builder.setTitle("Want to save Video ?").setCancelable(true).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (GalleryVideoActivityGAP.this.check == 0) {
                    GalleryVideoActivityGAP.this.warning = 1;
                    GalleryVideoActivityGAP.this.stopRecording();
                    File file = new File(GalleryVideoActivityGAP.this.path);
                    if (file.exists()) {
                        file.delete();
                    }
                    Intent intent = new Intent(GalleryVideoActivityGAP.this, ImportImageActivity.class);
                    intent.addFlags(1073741824);
                    GalleryVideoActivityGAP.this.startActivity(intent);
                    GalleryVideoActivityGAP.this.finish();
                }
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (GalleryVideoActivityGAP.this.check == 0) {
                    GalleryVideoActivityGAP.this.warning = 1;
                    GalleryVideoActivityGAP.this.stopRecording();
                    Intent intent = new Intent(GalleryVideoActivityGAP.this, ImportImageActivity.class);
                    intent.addFlags(1073741824);
                    GalleryVideoActivityGAP.this.startActivity(intent);
                    GalleryVideoActivityGAP.this.finish();
                }
            }
        });
        alert = builder.create();
        alert.show();
        alert.getButton(-2).setTextColor(getResources().getColor(R.color.colorPrimary));
        alert.getButton(-1).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    public void exitdialog(final Activity activity) {
        if (activity != null) {
            View inflate = LayoutInflater.from(activity).inflate(R.layout.back_dialog, (ViewGroup) null);


            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(1);
            dialog.setContentView(inflate);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));


            ((RelativeLayout) inflate.findViewById(R.id.cancel_btn)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            ((RelativeLayout) inflate.findViewById(R.id.ok_btn)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                    startActivity(new Intent(GalleryVideoActivityGAP.this, ImportImageActivity.class));
                    finish();


                }
            });
            dialog.show();
            dialog.getWindow().setLayout(-1, -2);
        }
    }


    @Override
    public void onBackPressed() {
        if (this.check == 0) {
            createNotificationAccessDisabledError(this);
            return;
        }
        exitdialog(GalleryVideoActivityGAP.this);
    }

    public void updaterender(int i) {
        this.mRenderPipeline.addFilterRender(this.mCurrentRender);
        this.mRenderPipeline.addFilterRender(this.mCurrentRenderfilter);
        this.seekrender = i;
        if (posetion == 1) {
            this.binary.adjust(((float) i) * 0.012f);
        } else if (posetion == 2) {
            this.chromaRender1.adjust(((float) i) * 0.012f);
        } else if (posetion == 3) {
            this.chromaRender2.adjust(((float) i) * 0.012f);
        } else if (posetion == 4) {
            this.drunkenRender.adjust(((float) i) * 0.012f);
        } else if (posetion == 5) {
            this.drunkRender2.adjust(((float) i) * 0.012f);
        } else if (posetion == 6) {
            this.dynamicRender.adjust(((float) i) * 0.012f);
        } else if (posetion == 7) {
            this.oldMoveRender.adjust(((float) i) * 0.012f);
        } else if (posetion == 8) {
            this.sinMov2.adjust(((float) i) * 0.012f);
        } else if (posetion == 9) {
            this.triangleRender.adjust(((float) i) * 0.012f);
        } else if (posetion == 10) {
            this.wobbleRender.adjust(((float) i) * 0.012f);
        } else if (posetion == 0 || gaprenderadapter.selectpos == 0) {
            this.mRenderPipeline.removeFilterRender(this.mCurrentRender);
            this.mRenderPipeline.addFilterRender(this.noFilterRender);
        }
    }

    public void updatefilter(int i) {
        this.mRenderPipeline.addFilterRender(this.mCurrentRenderfilter);
        this.mRenderPipeline.addFilterRender(this.mCurrentRender);
        if (adapteposfilter == 0) {
            this.lookupRender.adjust(0.0f);
            return;
        }
        this.seekfilter = i;
        this.lookupRender.adjust(((float) i) * 0.01f);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void refreshAndroidGallery(Uri uri) {
        if (VERSION.SDK_INT >= 19) {
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(uri);
            sendBroadcast(intent);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("file://");
        sb.append(Environment.getExternalStorageDirectory());
        sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse(sb.toString())));
    }
}
