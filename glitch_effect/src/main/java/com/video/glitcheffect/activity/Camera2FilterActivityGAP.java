package com.video.glitcheffect.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
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

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetSequence.Listener;
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
import com.video.glitcheffect.render.SinMov2;
import com.video.glitcheffect.render.TriangleRender;
import com.video.glitcheffect.render.WobbleRender;
import com.video.glitcheffect.utils.LogUtils;
import com.video.glitcheffect.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.ezandroid.ezfilter.EZFilter;
import cn.ezandroid.ezfilter.camera.ISupportTakePhoto;
import cn.ezandroid.ezfilter.core.FBORender;
import cn.ezandroid.ezfilter.core.FilterRender;
import cn.ezandroid.ezfilter.core.GLRender;
import cn.ezandroid.ezfilter.core.RenderPipeline;
import cn.ezandroid.ezfilter.core.environment.FitViewHelper.ScaleType;
import cn.ezandroid.ezfilter.core.environment.SurfaceFitView;
import cn.ezandroid.ezfilter.core.output.BitmapOutput.BitmapOutputCallback;
import cn.ezandroid.ezfilter.media.record.ISupportRecord;
import cn.ezandroid.ezfilter.video.VideoInput;

@TargetApi(21)
public class Camera2FilterActivityGAP extends BaseActivity {

    public static int adapterposfilter = 0;
    public static int index = -1;
    public static int position = 0;
    public static SeekBar seekBar = null;
    public static int star1 = 0;
    public static int star2 = 0;

    FilterAdapter gapfilteradapter;
    RenderAdapter gaprenderadapter;
    int takevideofromgallery = 1;
    int abc;
    AlertDialog alert;
    private ArrayList<FilterRender> arrfilter;
    private ArrayList<FilterRender> arrrender;
    boolean audiorecording = true;
    Binary binary = new Binary();
    String cameraId;
    CameraManager cameraManager;
    int cameraswitchid = 1;
    ImageView capture;
    int centre;
    int check = -1;
    ChromaRender1 chromaRender1 = new ChromaRender1();
    ChromaRender2 chromaRender2 = new ChromaRender2();

    public Handler customHandler = new Handler();
    DisplayMetrics displayMetrics;
    DrunkRender2 drunkRender2 = new DrunkRender2();
    DrunkenRender drunkenRender = new DrunkenRender();
    DynamicRender dynamicRender = new DynamicRender();
    LinearLayout filtereffect;
    int[] filterimeges = {R.drawable.duo_14, R.drawable.duo_1, R.drawable.duo_2, R.drawable.duo_3, R.drawable.duo_4, R.drawable.duo_5, R.drawable.duo_17, R.drawable.duo_7, R.drawable.duo_8, R.drawable.duo_9, R.drawable.duo_10, R.drawable.duo_11, R.drawable.duo_12, R.drawable.duo_13, R.drawable.duo_14, R.drawable.duo_15, R.drawable.duo_16, R.drawable.duo_17, R.drawable.duo_18, R.drawable.duo_19, R.drawable.duo_20, R.drawable.filter2, R.drawable.filter3, R.drawable.filter6, R.drawable.filter7, R.drawable.filter8, R.drawable.filter14, R.drawable.filter15, R.drawable.filter16, R.drawable.filter17, R.drawable.filter18, R.drawable.filter20, R.drawable.filter21, R.drawable.filter22, R.drawable.filter23, R.drawable.filter24, R.drawable.filter25, R.drawable.filter26, R.drawable.filter27, R.drawable.filter28, R.drawable.filter29, R.drawable.filter30, R.drawable.filter31, R.drawable.filter32, R.drawable.filter33, R.drawable.filter34, R.drawable.filter35, R.drawable.filter36, R.drawable.filter37, R.drawable.filter38, R.drawable.filter39, R.drawable.filter40, R.drawable.filter41, R.drawable.filter42, R.drawable.filter43, R.drawable.filter44, R.drawable.filter45, R.drawable.trippy_1, R.drawable.trippy_2, R.drawable.trippy_3, R.drawable.trippy_4, R.drawable.trippy_5, R.drawable.trippy_6, R.drawable.trippy_7, R.drawable.trippy_8, R.drawable.trippy_9, R.drawable.trippy_10, R.drawable.trippy_11, R.drawable.trippy_12, R.drawable.trippy_13, R.drawable.trippy_14, R.drawable.trippy_15, R.drawable.trippy_16, R.drawable.trippy_17, R.drawable.trippy_18};
    ImageView filterpress;
    int filtertype = 1;
    ImageView filterunpress;
    ImageView flash;
    LinearLayout flashswitch;
    Animation focus;
    Boolean handactive;
    int heightscreen;

    public Boolean isTorchOn;
    public ItemfilterclickInterface itemfilterclick = new ItemfilterclickInterface() {
        public void onfilterClick(int i, FilterRender filterRender) {
            Camera2FilterActivityGAP.adapterposfilter = i;
            Camera2FilterActivityGAP.seekBar.setProgress(50);
            Camera2FilterActivityGAP.this.seekfilter();
            if (i == 0) {
                Camera2FilterActivityGAP.this.mRenderPipeline.removeFilterRender(Camera2FilterActivityGAP.this.mCurrentRenderfilter);
                Camera2FilterActivityGAP.this.mCurrentRenderfilter = null;
            } else if (Camera2FilterActivityGAP.this.mCurrentRenderfilter != null) {
                Camera2FilterActivityGAP.this.lookupRender.image(Camera2FilterActivityGAP.this.filterimeges[i]);
                Camera2FilterActivityGAP.this.lookupRender.adjust(0.5f);
            } else {
                Camera2FilterActivityGAP.this.lookupRender = new LookupRender(Camera2FilterActivityGAP.this, Camera2FilterActivityGAP.this.filterimeges[i]);
                Camera2FilterActivityGAP.this.mCurrentRenderfilter = Camera2FilterActivityGAP.this.lookupRender;
                if (!Camera2FilterActivityGAP.this.handactive.booleanValue()) {
                    Camera2FilterActivityGAP.this.mRenderPipeline.addFilterRender(2, Camera2FilterActivityGAP.this.lookupRender);
                }
            }
        }
    };
    public ItemfilterclickInterface itemrenderclick = new ItemfilterclickInterface() {
        public void onfilterClick(int i, FilterRender filterRender) {
            StringBuilder sb = new StringBuilder();
            sb.append("position ");
            sb.append(i);
            LogUtils.log(sb.toString());
            Camera2FilterActivityGAP.seekBar.setProgress(50);
            Camera2FilterActivityGAP.position = i;
            Camera2FilterActivityGAP.this.seekrender();
            if (i == 0) {
                Camera2FilterActivityGAP.this.mRenderPipeline.clearFilterRenders();
                Camera2FilterActivityGAP.this.mRenderPipeline.removeFilterRender(Camera2FilterActivityGAP.this.mCurrentRender);
                Camera2FilterActivityGAP.this.mCurrentRender = null;
            } else if (Camera2FilterActivityGAP.this.mCurrentRender != null) {
                Camera2FilterActivityGAP.this.mOldRender = Camera2FilterActivityGAP.this.mCurrentRender;
                Camera2FilterActivityGAP.this.mCurrentRender = filterRender;
                if (!Camera2FilterActivityGAP.this.handactive.booleanValue()) {
                    Camera2FilterActivityGAP.this.mRenderPipeline.removeFilterRender(Camera2FilterActivityGAP.this.mOldRender);
                    Camera2FilterActivityGAP.this.mRenderPipeline.addFilterRender(Camera2FilterActivityGAP.this.mCurrentRender);
                }
            } else {
                Camera2FilterActivityGAP.this.mOldRender = Camera2FilterActivityGAP.this.mCurrentRender;
                Camera2FilterActivityGAP.this.mCurrentRender = filterRender;
                Camera2FilterActivityGAP.this.mRenderPipeline.addFilterRender(Camera2FilterActivityGAP.this.mCurrentRender);
            }
        }
    };
    ImageView livegallery;
    LookupRender lookupRender = new LookupRender(this, R.drawable.duo_1);

    Camera mCamera;

    CameraManager mCameraManager;
    int mCurrentCameraId = 0;

    FilterRender mCurrentRender;

    FilterRender mCurrentRenderfilter;


    FilterRender mOldRender;

    int mOrientation;
    MyOrientationEventListener mOrientationEventListener;

    RelativeLayout mRecordButton;

    RenderPipeline mRenderPipeline;
    SurfaceFitView mRenderView;

    ISupportRecord mSupportRecord;
    ISupportTakePhoto mSupportTakePhoto;
    Uri myUri;
    NoFilterRender noFilterRender = new NoFilterRender();
    OldMoveRender oldMoveRender = new OldMoveRender();
    Parameters parameter;
    String path = null;
    SharedPreferences prefs = null;
    ImageView press;

    RecyclerView rcfilter;

    RecyclerView rcrender;
    boolean recordstatus = false;
    LinearLayout rendereffect;
    int[] renderimages = {R.drawable.no_filter, R.drawable.fil_0, R.drawable.fil_1, R.drawable.fil_2, R.drawable.fil_3, R.drawable.fil_4, R.drawable.fil_5, R.drawable.fil_6, R.drawable.fil_7, R.drawable.fil_8, R.drawable.fil_9};
    LinearLayout seekrelate;
    int seekfilter = 50;
    int seekrender = 50;
    int select;
    TapTargetSequence sequence = null;
    SinMov2 sinMov2 = new SinMov2();

    public long startTime = 0;
    RelativeLayout surfacerel;
    LinearLayout switchcamera;
    ImageView tap;
    long timeInMilliseconds = 0;
    long timeSwapBuff = 0;
    TextView timerValue;
    TriangleRender triangleRender = new TriangleRender();
    ImageView unpress;
    private Runnable updateTimerThread = new Runnable() {
        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        public void run() {
            Camera2FilterActivityGAP.this.timeInMilliseconds = SystemClock.uptimeMillis() - Camera2FilterActivityGAP.this.startTime;
            Camera2FilterActivityGAP.this.updatedTime = Camera2FilterActivityGAP.this.timeSwapBuff + Camera2FilterActivityGAP.this.timeInMilliseconds;
            int i = (int) (Camera2FilterActivityGAP.this.updatedTime / 1000);
            int i2 = i / 60;
            int i3 = i % 60;
            TextView textView = Camera2FilterActivityGAP.this.timerValue;
            StringBuilder sb = new StringBuilder();
            sb.append("0");
            sb.append(i2);
            sb.append(":");
            sb.append(String.format("%02d", new Object[]{Integer.valueOf(i3)}));
            textView.setText(sb.toString());
            Camera2FilterActivityGAP.this.customHandler.postDelayed(this, 0);
        }
    };
    long updatedTime = 0;
    ImageView voice;
    int warning = 0;
    int widthscreen;
    WobbleRender wobbleRender = new WobbleRender();
    ImageView yesrecording;

    private class MyOrientationEventListener extends OrientationEventListener {
        MyOrientationEventListener(Context context) {
            super(context);
        }

        public void onOrientationChanged(int i) {
            if (i != -1) {
                Camera2FilterActivityGAP.this.mOrientation = Camera2FilterActivityGAP.this.roundOrientation(i, Camera2FilterActivityGAP.this.mOrientation);
            }
        }
    }

    public int roundOrientation(int i, int i2) {
        boolean z = true;
        if (i2 != -1) {
            int abs = Math.abs(i - i2);
            if (Math.min(abs, 360 - abs) < 50) {
                z = false;
            }
        }
        return z ? (((i + 45) / 90) * 90) % 360 : i2;
    }

    @Override
    @SuppressLint({"ClickableViewAccessibility", "WrongConstant", "ResourceType"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_camera2_filter);


//        AdView mAdView = findViewById(R.id.adView);
//        Ad_class.showBanner(mAdView);


        if (VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }


        this.mRenderView = (SurfaceFitView) findViewById(R.id.render_view);
        this.mRenderView.setScaleType(ScaleType.CENTER_CROP);
        this.mRecordButton = (RelativeLayout) $(R.id.record);
        this.switchcamera = (LinearLayout) findViewById(R.id.switch_camera);
        this.livegallery = (ImageView) findViewById(R.id.livegallery);
        this.rcrender = (RecyclerView) findViewById(R.id.rc_render1);
        this.rcfilter = (RecyclerView) findViewById(R.id.rc_filter1);
        this.rcrender.setVisibility(View.VISIBLE);
        this.flashswitch = (LinearLayout) findViewById(R.id.flash_switch);
        ImportImageActivity.imgvid = 1;
        star1 = 0;
        star2 = 0;
        position = 0;
        gaprenderadapter.selectpos = 0;
        adapterposfilter = 0;
        gapfilteradapter.filterPosition = 0;
        this.isTorchOn = Boolean.valueOf(false);
        this.tap = (ImageView) findViewById(R.id.tap);
        this.voice = (ImageView) findViewById(R.id.mic);
        this.timerValue = (TextView) findViewById(R.id.timer);
        this.capture = (ImageView) findViewById(R.id.capture);
        this.yesrecording = (ImageView) findViewById(R.id.yess_recording);
        this.flash = (ImageView) findViewById(R.id.flash);
        this.flash.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_flash_off, null));
        this.prefs = getSharedPreferences(getPackageName(), 0);
        this.surfacerel = (RelativeLayout) findViewById(R.id.surfacerel);
        this.filterpress = (ImageView) findViewById(R.id.filter_prass);
        this.filterunpress = (ImageView) findViewById(R.id.filter_unprass);
        this.rendereffect = (LinearLayout) findViewById(R.id.render_affect);
        this.filtereffect = (LinearLayout) findViewById(R.id.filter_affect);
        this.press = (ImageView) findViewById(R.id.render_prass);
        this.unpress = (ImageView) findViewById(R.id.render_unprass);
        this.seekrelate = (LinearLayout) findViewById(R.id.seek_ralate);
        this.press.setVisibility(View.VISIBLE);
        this.focus = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.focus);
        this.mOrientationEventListener = new MyOrientationEventListener(this);
        this.mCameraManager = (CameraManager) getSystemService("camera");
        seekBar = (SeekBar) findViewById(R.id.intensity);
        seekBar.setProgress(50);
        this.handactive = Boolean.valueOf(false);
        this.mCurrentRender = this.noFilterRender;
        this.tap.setImageResource(R.drawable.hand);
        this.tap.setPadding(15, 15, 15, 15);
        makerenderRecycle();

        this.livegallery.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                Intent intent1 = new Intent();
                intent1.setType("video/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Select Video"), takevideofromgallery);


            }
        });

        this.switchcamera.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Camera2FilterActivityGAP.this.switchCamera();
            }
        });
        this.flashswitch.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Camera2FilterActivityGAP.this.isTorchOn.booleanValue()) {
                    Camera2FilterActivityGAP.this.turnOffFlashLight();
                    Camera2FilterActivityGAP.this.flash.setImageDrawable(ResourcesCompat.getDrawable(Camera2FilterActivityGAP.this.getResources(), R.drawable.ic_flash_off, null));
                    return;
                }
                Camera2FilterActivityGAP.this.turnOnFlashLight();
                Camera2FilterActivityGAP.this.flash.setImageDrawable(ResourcesCompat.getDrawable(Camera2FilterActivityGAP.this.getResources(), R.drawable.ic_flash, null));
            }
        });
        this.capture.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Utils.transfer = 0;
                ImportImageActivity.imgvid = 0;
                Camera2FilterActivityGAP.this.mRenderPipeline.output(new BitmapOutputCallback() {
                    public void bitmapOutput(final Bitmap bitmap) {
                        Camera2FilterActivityGAP.this.runOnUiThread(new Runnable() {
                            public void run() {
                                StringBuilder sb = new StringBuilder();
                                sb.append(Environment.getExternalStorageDirectory());
                                sb.append("/");
                                sb.append(Camera2FilterActivityGAP.this.getResources().getString(R.string.parent_folder_name));
                                sb.append("/");
                                sb.append(Camera2FilterActivityGAP.this.getResources().getString(R.string.live_folder));
                                File file = new File(sb.toString());
                                file.mkdirs();
                                String format = new SimpleDateFormat("ddMMyyhh:mm:ss").format(new Date(System.currentTimeMillis()));
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(Camera2FilterActivityGAP.this.getResources().getString(R.string.nameimage));
                                sb2.append("_");
                                sb2.append(format);
                                sb2.append(".jpg");
                                String sb3 = sb2.toString();
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("LiveImage");
                                sb4.append(sb3);
                                Log.e(sb4.toString(), "name");
                                File file2 = new File(file, sb3);
                                Log.e("Path1::", file2.getAbsolutePath());
                                if (file2.exists()) {
                                    file2.delete();
                                }
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                                    bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                File file3 = new File(Camera2FilterActivityGAP.this.path);
                                if (file3.exists()) {
                                    file3.delete();
                                }
                                Intent intent = new Intent(Camera2FilterActivityGAP.this, MainActivity.class);
                                Utils.path3 = file2.getAbsolutePath();
                                Camera2FilterActivityGAP.this.startActivity(intent);
                            }
                        });
                    }
                }, true);
            }
        });
        $(R.id.reject).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {


                if (Camera2FilterActivityGAP.this.check == 0) {
                    Camera2FilterActivityGAP.this.createNotificationAccessDisabledError(Camera2FilterActivityGAP.this);
                    return;
                }

                exitdialog(Camera2FilterActivityGAP.this);

            }
        });
        this.mRecordButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Camera2FilterActivityGAP.this.switchcamera.setVisibility(4);
                if (Camera2FilterActivityGAP.this.recordstatus) {
                    Camera2FilterActivityGAP.this.stopRecording();
                    return;
                }
                Camera2FilterActivityGAP.this.startRecording();
                Camera2FilterActivityGAP.this.recordstatus = true;
            }
        });
        this.voice.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Camera2FilterActivityGAP.this.audiorecording) {
                    Camera2FilterActivityGAP.this.mSupportRecord.enableRecordAudio(false);
                    Camera2FilterActivityGAP.this.voice.setImageResource(R.drawable.ic_microphone_off);
                    Camera2FilterActivityGAP.this.audiorecording = false;
                    return;
                }
                Camera2FilterActivityGAP.this.mSupportRecord.enableRecordAudio(true);
                Camera2FilterActivityGAP.this.voice.setImageResource(R.drawable.ic_microphone_on);
                Camera2FilterActivityGAP.this.audiorecording = true;
            }
        });
        this.rendereffect.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Camera2FilterActivityGAP.this.filtertype = 1;
                Camera2FilterActivityGAP.this.rcfilter.setVisibility(8);
                Camera2FilterActivityGAP.this.press.setVisibility(0);
                Camera2FilterActivityGAP.this.rcrender.setVisibility(0);
                Camera2FilterActivityGAP.this.unpress.setVisibility(8);
                Camera2FilterActivityGAP.this.filterpress.setVisibility(8);
                Camera2FilterActivityGAP.this.filterunpress.setVisibility(0);
                Camera2FilterActivityGAP.this.makerenderRecycle();
                Camera2FilterActivityGAP.this.seekrender();
            }
        });
        this.filtereffect.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Camera2FilterActivityGAP.this.filtertype = 2;
                Camera2FilterActivityGAP.this.rcfilter.setVisibility(0);
                Camera2FilterActivityGAP.this.rcrender.setVisibility(8);
                Camera2FilterActivityGAP.this.press.setVisibility(8);
                Camera2FilterActivityGAP.this.unpress.setVisibility(0);
                Camera2FilterActivityGAP.this.filterpress.setVisibility(0);
                Camera2FilterActivityGAP.this.filterunpress.setVisibility(8);
                Camera2FilterActivityGAP.this.makefilterRecycle();
                Camera2FilterActivityGAP.this.seekfilter();
            }
        });
        this.tap.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Camera2FilterActivityGAP.this.handactive.booleanValue()) {
                    if (Camera2FilterActivityGAP.this.filtertype == 1) {
                        Camera2FilterActivityGAP.this.updaterender(Camera2FilterActivityGAP.this.seekrender);
                        StringBuilder sb = new StringBuilder();
                        sb.append("secondif ");
                        sb.append(Camera2FilterActivityGAP.this.handactive.toString());
                        Log.e(sb.toString(), NotificationCompat.CATEGORY_MESSAGE);
                    } else {
                        Camera2FilterActivityGAP.this.updatefilter(Camera2FilterActivityGAP.this.seekfilter);
                    }
                    Camera2FilterActivityGAP.this.tap.setImageResource(R.drawable.hand);
                    Camera2FilterActivityGAP.this.tap.setPadding(15, 15, 15, 15);
                    Camera2FilterActivityGAP.this.handactive = Boolean.valueOf(false);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("secondelse ");
                    sb2.append(Camera2FilterActivityGAP.this.handactive.toString());
                    Log.e(sb2.toString(), NotificationCompat.CATEGORY_MESSAGE);
                    return;
                }
                Camera2FilterActivityGAP.this.handactive = Boolean.valueOf(true);
                Camera2FilterActivityGAP.this.tap.setImageResource(R.drawable.hand_press);
                Camera2FilterActivityGAP.this.tap.setPadding(15, 15, 15, 15);
                Camera2FilterActivityGAP.this.mRenderPipeline.removeFilterRender(Camera2FilterActivityGAP.this.mCurrentRenderfilter);
                Camera2FilterActivityGAP.this.mRenderPipeline.removeFilterRender(Camera2FilterActivityGAP.this.mCurrentRender);
                Camera2FilterActivityGAP.this.mRenderPipeline.addFilterRender(Camera2FilterActivityGAP.this.noFilterRender);
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Handtouchd ");
                sb3.append(Camera2FilterActivityGAP.this.handactive.toString());
                Log.e(sb3.toString(), NotificationCompat.CATEGORY_MESSAGE);
            }
        });
        final int width = getWindowManager().getDefaultDisplay().getWidth();
        this.centre = width / 3;
        getWindowManager().getDefaultDisplay().getHeight();
        this.surfacerel.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                        if (Camera2FilterActivityGAP.this.filtertype != 1) {
                            int x = (int) motionEvent.getX();
                            motionEvent.getY();
                            int i = width / 100;
                            Camera2FilterActivityGAP.seekBar.setMax(100);
                            Camera2FilterActivityGAP.this.abc = x / i;
                            Camera2FilterActivityGAP.this.updatefilter(Camera2FilterActivityGAP.this.abc);
                            Camera2FilterActivityGAP.seekBar.setProgress(Camera2FilterActivityGAP.this.abc);
                            StringBuilder sb = new StringBuilder();
                            sb.append("Down ");
                            sb.append(Camera2FilterActivityGAP.seekBar);
                            Log.e(sb.toString(), "");
                            break;
                        } else {
                            int x2 = (int) motionEvent.getX();
                            motionEvent.getY();
                            int i2 = width / 100;
                            Camera2FilterActivityGAP.seekBar.setMax(100);
                            Camera2FilterActivityGAP.this.abc = x2 / i2;
                            Camera2FilterActivityGAP.this.updaterender(Camera2FilterActivityGAP.this.abc);
                            Camera2FilterActivityGAP.seekBar.setProgress(Camera2FilterActivityGAP.this.abc);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Down ");
                            sb2.append(Camera2FilterActivityGAP.seekBar);
                            Log.e(sb2.toString(), "");
                            break;
                        }
                    case 1:
                        if (Camera2FilterActivityGAP.this.handactive.booleanValue()) {
                            Camera2FilterActivityGAP.this.mRenderPipeline.clearFilterRenders();
                            break;
                        }
                        break;
                    case 2:
                        if (Camera2FilterActivityGAP.this.filtertype != 1) {
                            int x3 = (int) motionEvent.getX();
                            motionEvent.getY();
                            int i3 = width / 100;
                            Camera2FilterActivityGAP.seekBar.setMax(100);
                            Log.d("Move ", "Action was MOVE");
                            Camera2FilterActivityGAP.this.abc = x3 / i3;
                            Camera2FilterActivityGAP.this.updatefilter(Camera2FilterActivityGAP.this.abc);
                            Camera2FilterActivityGAP.seekBar.setProgress(Camera2FilterActivityGAP.this.abc);
                            break;
                        } else {
                            int x4 = (int) motionEvent.getX();
                            motionEvent.getY();
                            int i4 = width / 100;
                            Camera2FilterActivityGAP.seekBar.setMax(100);
                            Log.d("Move ", "Action was MOVE");
                            Camera2FilterActivityGAP.this.abc = x4 / i4;
                            Camera2FilterActivityGAP.this.updaterender(Camera2FilterActivityGAP.this.abc);
                            Camera2FilterActivityGAP.seekBar.setProgress(Camera2FilterActivityGAP.this.abc);
                            break;
                        }
                }
                return true;
            }
        });
        this.sequence = new TapTargetSequence((Activity) this).targets(TapTarget.forView(this.livegallery, "Gallery", "Click here to open gallery.").dimColor(17170443).outerCircleColor(R.color.colorAccent).targetCircleColor(R.color.white).textColor(R.color.white), TapTarget.forView(this.capture, "Image Capture", "Click here to capture photo.").dimColor(17170443).outerCircleColor(R.color.Color20).targetCircleColor(R.color.white).textColor(R.color.white), TapTarget.forView(this.mRecordButton, "Record video", "Click here to start and stop recording.").dimColor(17170443).outerCircleColor(R.color.colorAccent).targetCircleColor(R.color.white).textColor(R.color.white), TapTarget.forView(this.rendereffect, "Glitch", "Click here for Glitch effects.").dimColor(17170443).outerCircleColor(R.color.Color20).targetCircleColor(R.color.white).textColor(R.color.white), TapTarget.forView(this.filtereffect, "Filters", "Click here for camera filters.").dimColor(17170443).outerCircleColor(R.color.colorAccent).targetCircleColor(R.color.white).textColor(R.color.white), TapTarget.forView(this.switchcamera, "Switch camera", "Click here for change your camera view.").dimColor(17170443).outerCircleColor(R.color.Color20).targetCircleColor(R.color.white).textColor(R.color.white), TapTarget.forView(this.voice, "Audio", "Click here to on/off audio.").dimColor(R.color.white).outerCircleColor(R.color.colorAccent).targetCircleColor(R.color.white).textColor(R.color.white), TapTarget.forView(this.tap, "Touch", "Click here to enable touch mode for apply effects on touch.").dimColor(17170443).outerCircleColor(R.color.Color20).targetCircleColor(R.color.white).textColor(R.color.white)).listener(new Listener() {

            @Override
            public void onSequenceFinish() {
                Log.d("dffdf", "");
            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                Log.d("dffdf", "");
            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {
                Log.d("dffdf", "");
            }
        });
        if (this.audiorecording) {
            this.voice.setImageResource(R.drawable.ic_microphone_on);
        } else {
            this.voice.setImageResource(R.drawable.ic_microphone_off);
        }
    }

    @SuppressLint("WrongConstant")
    public void seekrender() {
        if (position == 0 || gaprenderadapter.selectpos == 0) {
            seekBar.setVisibility(8);
        } else {
            seekBar.setVisibility(0);
        }
    }

    @SuppressLint("WrongConstant")
    public void seekfilter() {
        if (adapterposfilter == 0 || gapfilteradapter.filterPosition == 0) {
            seekBar.setVisibility(8);
        } else {
            seekBar.setVisibility(0);
        }
    }

    public void turnOnFlashLight() {
        this.parameter = this.mCamera.getParameters();
        this.parameter.setFlashMode("torch");
        this.mCamera.setParameters(this.parameter);
        this.isTorchOn = Boolean.valueOf(true);
    }

    public void turnOffFlashLight() {
        this.parameter = this.mCamera.getParameters();
        this.parameter.setFlashMode("off");
        this.mCamera.setParameters(this.parameter);
        this.isTorchOn = Boolean.valueOf(false);
    }


    @SuppressLint("WrongConstant")
    public void startRecording() {
        this.check = 0;
        if (this.mSupportRecord != null) {
            this.mSupportRecord.startRecording();
            this.yesrecording.setVisibility(0);
            this.livegallery.setVisibility(8);
            this.voice.setVisibility(8);
            this.capture.setVisibility(8);
            this.startTime = SystemClock.uptimeMillis();
            this.customHandler.postDelayed(this.updateTimerThread, 0);
            this.mRecordButton.startAnimation(this.focus);
        }
    }


    @SuppressLint("WrongConstant")
    public void stopRecording() {
        if (this.mSupportRecord != null) {
            this.seekfilter = 0;
            this.seekrender = 0;
            this.mSupportRecord.stopRecording();
            seekBar.setVisibility(8);
            this.yesrecording.setVisibility(4);
            this.timeSwapBuff += this.timeInMilliseconds;
            this.customHandler.removeCallbacks(this.updateTimerThread);
            this.mRecordButton.clearAnimation();
            if (this.isTorchOn.booleanValue()) {
                turnOffFlashLight();
            }
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Log.d("dffdf", "");
                }
            }, 2000);
            if (this.warning == 1) {
                this.warning = 0;
                Log.e("RejectPressed", "pressed");
                finish();
                return;
            }

            Intent intent1 = new Intent(this, MyCreationActivity.class);
            startActivity(intent1);
            finish();


        }
    }


    public void makerenderRecycle() {
        addrenderclass();
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, 0, false);
        this.gaprenderadapter = new RenderAdapter(this, this.arrrender, this.itemrenderclick, this.renderimages);
        if (star1 == 0) {
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
        } else {
            linearLayoutManager.scrollToPositionWithOffset(position, this.centre);
        }
        this.rcrender.setLayoutManager(linearLayoutManager);
        this.rcrender.setAdapter(this.gaprenderadapter);
    }


    public void makefilterRecycle() {
        addallclassFilter(75);
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, 0, false);
        this.gapfilteradapter = new FilterAdapter(this, this.arrfilter, this.itemfilterclick, this.filterimeges);
        if (star2 == 0) {
            linearLayoutManager.scrollToPositionWithOffset(0, 0);
        } else {
            linearLayoutManager.scrollToPositionWithOffset(adapterposfilter, this.centre);
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
        this.mOrientationEventListener.disable();
        releaseCamera();
        Log.e("Pause", "pause");
        if (this.select == 1 && this.mRenderPipeline != null) {
            ((VideoInput) this.mRenderPipeline.getStartPointRender()).pause();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        this.mOrientationEventListener.enable();
        releaseCamera();
        openCamera(this.mCurrentCameraId);
        gaprenderadapter.selectpos = 0;
        gapfilteradapter.filterPosition = 0;
        adapterposfilter = 0;
        position = 0;
        seekBar.setVisibility(View.GONE);
        if (this.select == 1 && this.mRenderPipeline != null) {
            ((VideoInput) this.mRenderPipeline.getStartPointRender()).start();
        }
        if (this.prefs.getBoolean("firstrun", true)) {
            this.sequence.start();
            this.prefs.edit().putBoolean("firstrun", false).commit();
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

    public void addallclassFilter(int i) {
        this.arrfilter = new ArrayList<>();
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 == 0) {
                this.arrfilter.add(new NoFilterRender());
            } else {
                this.arrfilter.add(new LookupRender(this, this.filterimeges[i2]));
            }
        }
    }

    private void setCameraParameters() {
        int i;
        this.parameter = this.mCamera.getParameters();
        if (getResources().getConfiguration().orientation != 2) {
            CameraInfo cameraInfo = new CameraInfo();
            Camera.getCameraInfo(this.mCurrentCameraId, cameraInfo);
            this.parameter.set("orientation", "portrait");
            if (this.mCurrentCameraId == 1) {
                this.parameter.set("rotation", 270);
                i = 360 - cameraInfo.orientation;
            } else {
                this.parameter.setFocusMode("continuous-video");
                this.parameter.set("rotation", 90);
                i = cameraInfo.orientation;
            }
            this.mCamera.setDisplayOrientation(i);
        } else {
            this.parameter.set("orientation", "landscape");
            this.mCamera.setDisplayOrientation(0);
        }
        if (getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.camera.flash")) {
            this.parameter.setFlashMode("off");
        } else {
            Log.e("Error", "no flash in device");
        }
        this.mCamera.setParameters(this.parameter);
    }

    @SuppressLint({"MissingPermission"})
    private void openCamera(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getPath());
        sb.append("/");
        sb.append(getResources().getString(R.string.parent_folder_name));
        sb.append("/");
        sb.append(getResources().getString(R.string.video_folder));
        sb.append("/");
        sb.append(getResources().getString(R.string.namevideo));
        sb.append(System.currentTimeMillis());
        sb.append(".mp4");
        this.path = sb.toString();
        try {
            this.mCamera = Camera.open(i);
            this.parameter = this.mCamera.getParameters();
            setCameraParameters();
            if (hasFlash(this.mCamera)) {
                this.flashswitch.setVisibility(View.VISIBLE);
            } else {
                this.flashswitch.setVisibility(View.INVISIBLE);
                this.flash.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_flash_off, null));
            }
            if (this.handactive.booleanValue()) {
                this.mRenderPipeline = EZFilter.input(this.mCamera, this.mCamera.getParameters().getPreviewSize()).addFilter(this.mCurrentRender).addFilter(this.mCurrentRenderfilter).enableRecord(this.path, true, true).into(this.mRenderView);
                this.mRenderPipeline.clearFilterRenders();
            } else {
                this.mRenderPipeline = EZFilter.input(this.mCamera, this.mCamera.getParameters().getPreviewSize()).addFilter(this.mCurrentRender).addFilter(this.mCurrentRenderfilter).enableRecord(this.path, true, true).into(this.mRenderView);
            }
        } catch (Throwable unused) {
            Log.d("dffdf", "");
        }
        FBORender startPointRender = this.mRenderPipeline.getStartPointRender();
        if (startPointRender instanceof ISupportTakePhoto) {
            this.mSupportTakePhoto = (ISupportTakePhoto) startPointRender;
        }
        for (GLRender gLRender : this.mRenderPipeline.getEndPointRenders()) {
            if (gLRender instanceof ISupportRecord) {
                this.mSupportRecord = (ISupportRecord) gLRender;
            }
        }
        this.displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
        this.heightscreen = this.displayMetrics.heightPixels;
        this.widthscreen = this.displayMetrics.widthPixels;
        this.mSupportRecord.setRecordSize(this.widthscreen - 100, this.heightscreen);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("dsd  ");
        sb2.append(this.path);
        Log.e("PAAAATH", sb2.toString());
    }


    public void switchCamera() {
        this.mCurrentCameraId = (this.mCurrentCameraId + 1) % Camera.getNumberOfCameras();
        releaseCamera();
        openCamera(this.mCurrentCameraId);
    }

    private void releaseCamera() {
        if (this.mCamera != null) {
            this.mCamera.setPreviewCallback(null);
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    public void createNotificationAccessDisabledError(Activity activity) {
        Builder builder = new Builder(activity);
        builder.setMessage(R.string.message3).setCancelable(true).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (Camera2FilterActivityGAP.this.check == 0) {
                    Camera2FilterActivityGAP.this.warning = 1;
                    Camera2FilterActivityGAP.this.stopRecording();
                    File file = new File(Camera2FilterActivityGAP.this.path);
                    if (file.exists()) {
                        file.delete();
                    }
                    Camera2FilterActivityGAP.this.finish();
                }
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (Camera2FilterActivityGAP.this.check == 0) {
                    Camera2FilterActivityGAP.this.warning = 1;
                    Camera2FilterActivityGAP.this.stopRecording();
                }
            }
        });
        this.alert = builder.create();
        this.alert.show();
        this.alert.getButton(-2).setTextColor(getResources().getColor(R.color.colorPrimary));
        this.alert.getButton(-1).setTextColor(getResources().getColor(R.color.colorPrimary));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == this.takevideofromgallery && i2 == -1) {
            Uri data = intent.getData();
            Log.e("hello", "hello");
            String path2 = getPath(data);
            StringBuilder sb = new StringBuilder();
            sb.append("gallerypath: ");
            sb.append(path2);
            Log.e(sb.toString(), "path");
            if (path2 != null) {
                Intent intent2 = new Intent(this, GalleryVideoActivityGAP.class);
                intent2.putExtra("live", path2);
                startActivity(intent2);
            }
        }
    }

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);

            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    private String getDriveFilePath(Uri uri) {
        Uri returnUri = uri;
        Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);


        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(getCacheDir(), name);
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();


            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public boolean isWhatsAppFile(Uri uri) {
        return "com.whatsapp.provider.media".equals(uri.getAuthority());
    }

    private boolean isGoogleDriveUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority()) || "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
    }

    Uri contentUri = null;

    private String copyFileToInternalStorage(Uri uri, String newDirName) {
        Uri returnUri = uri;

        Cursor returnCursor = getContentResolver().query(returnUri, new String[]{
                OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE
        }, null, null, null);


        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));

        File output;
        if (!newDirName.equals("")) {
            File dir = new File(getFilesDir() + "/" + newDirName);
            if (!dir.exists()) {
                dir.mkdir();
            }
            output = new File(getFilesDir() + "/" + newDirName + "/" + name);
        } else {
            output = new File(getFilesDir() + "/" + name);
        }
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(output);
            int read = 0;
            int bufferSize = 1024;
            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }

            inputStream.close();
            outputStream.close();

        } catch (Exception e) {

            Log.e("Exception", e.getMessage());
        }

        return output.getPath();
    }

    private boolean fileExists(String filePath) {
        File file = new File(filePath);

        return file.exists();
    }

    private String getPathFromExtSD(String[] pathData) {
        final String type = pathData[0];
        final String relativePath = "/" + pathData[1];
        String fullPath = "";

        // on my Sony devices (4.4.4 & 5.1.1), `type` is a dynamic string
        // something like "71F8-2C0A", some kind of unique id per storage
        // don't know any API that can get the root path of that storage based on its id.
        //
        // so no "primary" type, but let the check here for other devices
        if ("primary".equalsIgnoreCase(type)) {
            fullPath = Environment.getExternalStorageDirectory() + relativePath;
            if (fileExists(fullPath)) {
                return fullPath;
            }
        }

        // Environment.isExternalStorageRemovable() is `true` for external and internal storage
        // so we cannot relay on it.
        //
        // instead, for each possible path, check if file exists
        // we'll start with secondary storage as this could be our (physically) removable sd card
        fullPath = System.getenv("SECONDARY_STORAGE") + relativePath;
        if (fileExists(fullPath)) {
            return fullPath;
        }

        fullPath = System.getenv("EXTERNAL_STORAGE") + relativePath;
        if (fileExists(fullPath)) {
            return fullPath;
        }

        return fullPath;
    }


    private String getFilePathForWhatsApp(Uri uri) {
        return copyFileToInternalStorage(uri, "whatsapp");
    }

    public String getPath(final Uri uri) {
        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        String selection = null;
        String[] selectionArgs = null;
        // DocumentProvider
        if (isKitKat) {
            // ExternalStorageProvider

            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                String fullPath = getPathFromExtSD(split);
                if (fullPath != "") {
                    return fullPath;
                } else {
                    return null;
                }
            }


            // DownloadsProvider

            if (isDownloadsDocument(uri)) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    final String id;
                    Cursor cursor = null;
                    try {
                        cursor = getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DISPLAY_NAME}, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            String fileName = cursor.getString(0);
                            String path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
                            if (!TextUtils.isEmpty(path)) {
                                return path;
                            }
                        }
                    } finally {
                        if (cursor != null)
                            cursor.close();
                    }
                    id = DocumentsContract.getDocumentId(uri);
                    if (!TextUtils.isEmpty(id)) {
                        if (id.startsWith("raw:")) {
                            return id.replaceFirst("raw:", "");
                        }
                        String[] contentUriPrefixesToTry = new String[]{
                                "content://downloads/public_downloads",
                                "content://downloads/my_downloads"
                        };
                        for (String contentUriPrefix : contentUriPrefixesToTry) {
                            try {
                                final Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));


                                return getDataColumn(this, contentUri, null, null);
                            } catch (NumberFormatException e) {
                                //In Android 8 and Android P the id is not a number
                                return uri.getPath().replaceFirst("^/document/raw:", "").replaceFirst("^raw:", "");
                            }
                        }


                    }
                } else {
                    final String id = DocumentsContract.getDocumentId(uri);

                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:", "");
                    }
                    try {
                        contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (contentUri != null) {

                        return getDataColumn(this, contentUri, null, null);
                    }
                }
            }


            if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;

                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{split[1]};


                return getDataColumn(this, contentUri, selection,
                        selectionArgs);
            }

            if (isGoogleDriveUri(uri)) {
                return getDriveFilePath(uri);
            }

            if (isWhatsAppFile(uri)) {
                return getFilePathForWhatsApp(uri);
            }


            if ("content".equalsIgnoreCase(uri.getScheme())) {

                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                if (isGoogleDriveUri(uri)) {
                    return getDriveFilePath(uri);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {


                    return copyFileToInternalStorage(uri, "userfiles");

                } else {
                    return getDataColumn(this, uri, null, null);
                }

            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else {

            if (isWhatsAppFile(uri)) {
                return getFilePathForWhatsApp(uri);
            }

            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {
                        MediaStore.Images.Media.DATA
                };
                Cursor cursor = null;
                try {
                    cursor = getContentResolver()
                            .query(uri, projection, selection, selectionArgs, null);
                    int columnindex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (cursor.moveToFirst()) {
                        return cursor.getString(columnindex);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }


    public void updaterender(int i) {
        this.mRenderPipeline.addFilterRender(this.mCurrentRender);
        this.mRenderPipeline.addFilterRender(this.mCurrentRenderfilter);
        this.seekrender = i;
        StringBuilder sb = new StringBuilder();
        sb.append(GLRender.ATTRIBUTE_POSITION);
        sb.append(position);
        Log.e(sb.toString(), GLRender.ATTRIBUTE_POSITION);
        if (position == 0 || gaprenderadapter.selectpos == 0) {
            this.mRenderPipeline.removeFilterRender(this.mCurrentRender);
            this.mRenderPipeline.addFilterRender(this.noFilterRender);
        } else if (position == 1) {
            this.binary.adjust(((float) i) * 0.012f);
        } else if (position == 2) {
            this.chromaRender1.adjust(((float) i) * 0.012f);
        } else if (position == 3) {
            this.chromaRender2.adjust(((float) i) * 0.012f);
        } else if (position == 4) {
            this.drunkenRender.adjust(((float) i) * 0.012f);
        } else if (position == 5) {
            this.drunkRender2.adjust(((float) i) * 0.012f);
        } else if (position == 6) {
            this.dynamicRender.adjust(((float) i) * 0.012f);
        } else if (position == 7) {
            this.oldMoveRender.adjust(((float) i) * 0.012f);
        } else if (position == 8) {
            this.sinMov2.adjust(((float) i) * 0.012f);
        } else if (position == 9) {
            this.triangleRender.adjust(((float) i) * 0.012f);
        } else if (position == 10) {
            this.wobbleRender.adjust(((float) i) * 0.012f);
        }
    }

    public void updatefilter(int i) {
        this.mRenderPipeline.addFilterRender(this.mCurrentRenderfilter);
        this.mRenderPipeline.addFilterRender(this.mCurrentRender);
        StringBuilder sb = new StringBuilder();
        sb.append("seekfilter");
        sb.append(this.seekfilter);
        Log.e(sb.toString(), "filter");
        if (adapterposfilter == 0) {
            this.lookupRender.adjust(0.0f);
            return;
        }
        this.seekfilter = i;
        this.lookupRender.adjust(((float) i) * 0.01f);
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
                    startActivity(new Intent(Camera2FilterActivityGAP.this, ImportImageActivity.class));
                    finish();


                }
            });
            dialog.show();
            dialog.getWindow().setLayout(-1, -2);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.check == 0 || this.recordstatus) {
            createNotificationAccessDisabledError(this);
            return;
        }

        exitdialog(Camera2FilterActivityGAP.this);

    }

    public boolean hasFlash(Camera camera) {
        if (camera == null) {
            return false;
        }
        Parameters parameters = camera.getParameters();
        if (parameters.getFlashMode() == null) {
            return false;
        }
        List supportedFlashModes = parameters.getSupportedFlashModes();
        if (supportedFlashModes == null || supportedFlashModes.isEmpty() || (supportedFlashModes.size() == 1 && ((String) supportedFlashModes.get(0)).equals("off"))) {
            return false;
        }
        return true;
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
