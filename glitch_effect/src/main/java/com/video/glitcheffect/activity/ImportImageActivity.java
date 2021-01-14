package com.video.glitcheffect.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.video.glitcheffect.R;
import com.video.glitcheffect.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class ImportImageActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE_CREATION = 202;
    private static final int PERMISSION_REQUEST_CODE_GALLERY = 403;
    private static final int REQUEST_PERMISSION_SETTING = 303;
    public static int loadimageresult = 2;
    public static int imgvid;
    int tackgalleryvideo = 1;
    AlertDialog alert;
    String applicationName;
    ImageView btncreatedimgs;
    ImageView btngallery;
    ImageView btnvideo;
    Toolbar mTopToolbar;
    String subname;
    boolean success = true;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_import_image);

//        AdView mAdView = findViewById(R.id.adView);
//        Ad_class.showBanner(mAdView);
        mTopToolbar = findViewById(R.id.my_toolbar);
//        setSupportActionBar(this.mTopToolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTopToolbar.setTitle("");
        mTopToolbar.setSubtitle("");
        mTopToolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_menu));
        applicationName = getResources().getString(R.string.parent_folder_name);
        subname = getResources().getString(R.string.video_folder);
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append(this.applicationName);
        sb.append(File.separator);
        sb.append(this.subname);
        File file = new File(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Folder");
        sb2.append(file.toString());
        Log.e(sb2.toString(), "folder");
        this.success = true;
        if (!file.exists()) {
            this.success = file.mkdirs();
        }
        this.btngallery = findViewById(R.id.btn_gallery);
        this.btncreatedimgs = findViewById(R.id.btn_created);
        this.btnvideo = findViewById(R.id.btn_video);

        this.btngallery.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                if (ImportImageActivity.this.checkCameraPermission3()) {
                    ImportImageActivity.imgvid = 0;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Image ");
                    sb.append(ImportImageActivity.imgvid);
                    Log.e(sb.toString(), "Image");
                    ImportImageActivity.this.optiondialog();
                }

                /*Ad_class.showFullAd(ImportImageActivity.this, new Ad_class.onLisoner() {
                    @Override
                    public void click() {

                    }
                });*/

            }
        });


        this.btnvideo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {


                if (ImportImageActivity.this.checkCameraPermission()) {


                    ImportImageActivity.imgvid = 1;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Video ");
                    sb.append(ImportImageActivity.imgvid);
                    Log.e(sb.toString(), "Video");
                    ImportImageActivity.this.startActivity(new Intent(ImportImageActivity.this, Camera2FilterActivityGAP.class));
                }

            }
        });

        this.btncreatedimgs.setOnClickListener(view -> {
            if (ImportImageActivity.this.checkCameraPermission2()) {
                ImportImageActivity.this.startActivity(new Intent(ImportImageActivity.this, MyCreationActivity.class));
            }

            /*Ad_class.showFullAd(ImportImageActivity.this, new Ad_class.onLisoner() {
                @Override
                public void click() {
                }
            });*/

        });
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == loadimageresult && i2 == -1 && intent != null) {
            String[] strArr = {"_data"};
            Cursor query = getContentResolver().query(intent.getData(), strArr, null, null, null);
            query.moveToFirst();
            String string = query.getString(query.getColumnIndex(strArr[0]));
            query.close();
            Utils.path = string;
            startActivity(new Intent(this, ImageCroppingActivity.class));
        } else if (i == this.tackgalleryvideo && i2 == -1) {
            String path = getPath(intent.getData());
            StringBuilder sb = new StringBuilder();
            sb.append("gallerypath: ");
            sb.append(path);
            Log.e(sb.toString(), "path");
            if (path != null) {
                Intent intent2 = new Intent(this, GalleryVideoActivityGAP.class);
                intent2.putExtra("live", path);
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
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
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


        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
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


            // MediaProvider
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


    public boolean checkCameraPermission2() {
        if (VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PERMISSION_REQUEST_CODE_CREATION);
        return false;
    }

    public boolean checkCameraPermission() {
        if (VERSION.SDK_INT < 23) {
            return true;
        }
        int checkSelfPermission = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        int checkSelfPermission2 = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
        int checkSelfPermission3 = ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO");
        if (checkSelfPermission == 0 && checkSelfPermission2 == 0 && checkSelfPermission3 == 0) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.RECORD_AUDIO"}, 101);
        return false;
    }

    public boolean checkCameraPermission3() {
        if (VERSION.SDK_INT < 23) {
            return true;
        }
        int checkSelfPermission = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        int checkSelfPermission2 = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
        int checkSelfPermission3 = ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO");
        if (checkSelfPermission == 0 && checkSelfPermission2 == 0 && checkSelfPermission3 == 0) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA", "android.permission.RECORD_AUDIO"}, PERMISSION_REQUEST_CODE_GALLERY);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        boolean z = false;
        if (i == 101) {
            if (iArr.length > 0) {
                boolean z2 = iArr[0] == 0;
                boolean z3 = iArr[1] == 0;
                if (iArr[2] == 0) {
                    z = true;
                }
                if (!z2 || !z3 || !z) {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                    Log.e("Cameraactivity ", " Not granted");
                    int checkSelfPermission = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
                    int checkSelfPermission2 = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
                    int checkSelfPermission3 = ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO");
                    if (!(checkSelfPermission == 0 && checkSelfPermission2 == 0 && checkSelfPermission3 == 0)) {
                        createNotificationAccessDisabledError(this);
                    }
                } else {
                    Log.e("Cameraactivity ", " granted");
                    Intent intent = new Intent(this, Camera2FilterActivityGAP.class);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Activity: ");
                    sb.append(intent);
                    Log.e(sb.toString(), " launched");
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }
        } else if (i == PERMISSION_REQUEST_CODE_CREATION) {
            if (iArr.length > 0) {
                if (iArr[0] == 0) {
                    z = true;
                }
                if (z) {
                    Log.e("Mycreationactivity ", " granted");
                    Intent intent2 = new Intent(this, MyCreationActivity.class);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Activity: ");
                    sb2.append(intent2);
                    Log.e(sb2.toString(), " launched");
                    startActivity(intent2);
                    finish();
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                    Log.e("Mycreationactivity ", " Not granted");
                    int checkSelfPermission4 = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
                    Log.e("MycreationActivity", "camera");
                    if (checkSelfPermission4 != 0) {
                        createNotificationAccessDisabledError(this);
                    }
                }
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }
        } else if (i == PERMISSION_REQUEST_CODE_GALLERY) {
            if (iArr.length > 0) {
                boolean z4 = iArr[0] == 0;
                boolean z5 = iArr[1] == 0;
                if (iArr[2] == 0) {
                    z = true;
                }
                if (!z4 || !z5 || !z) {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                    Log.e("Galleryactivity ", " Not granted");
                    int checkSelfPermission5 = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
                    int checkSelfPermission6 = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
                    int checkSelfPermission7 = ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO");
                    if (!(checkSelfPermission5 == 0 && checkSelfPermission6 == 0 && checkSelfPermission7 == 0)) {
                        createNotificationAccessDisabledError(this);
                    }
                } else {
                    Log.e("Galleryactivity ", " granted");
                    optiondialog();
                }
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("ResourceType")
    public void optiondialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.getWindow().clearFlags(131080);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        dialog.setContentView(R.layout.customdialog);
        dialog.setCanceledOnTouchOutside(true);
        RelativeLayout relativeLayout = (RelativeLayout) dialog.findViewById(R.id.video);
        ((RelativeLayout) dialog.findViewById(R.id.imege)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ImportImageActivity.imgvid = 0;
                ImportImageActivity.this.startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), ImportImageActivity.loadimageresult);
                dialog.dismiss();
            }
        });
        relativeLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setType("video/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Select Video"), tackgalleryvideo);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void createNotificationAccessDisabledError(Activity activity) {
        Builder builder = new Builder(activity);
        builder.setMessage(R.string.message4).setCancelable(true).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", ImportImageActivity.this.getPackageName(), null));
                ImportImageActivity.this.startActivityForResult(intent, ImportImageActivity.REQUEST_PERMISSION_SETTING);
            }
        });
        this.alert = builder.create();
        this.alert.show();
        this.alert.getButton(-2).setTextColor(getResources().getColor(R.color.colorPrimary));
        this.alert.getButton(-1).setTextColor(getResources().getColor(R.color.colorPrimary));
    }
}
