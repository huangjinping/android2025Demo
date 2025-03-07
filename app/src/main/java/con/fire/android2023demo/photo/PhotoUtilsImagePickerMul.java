package con.fire.android2023demo.photo;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraCharacteristics;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import con.fire.android2023demo.FileUtils;
import con.fire.android2023demo.utils.ExifInfoCopier;
import con.fire.android2023demo.utils.AndroidImageResizer;


public class PhotoUtilsImagePickerMul extends PhotoSo {
    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识
    static final String SHARED_PREFERENCES_NAME = "flutter_image_picker_shared_preference";
    private static final String TAG = "PhotoUtils12ImagePicker";
    private static final String SHARED_PREFERENCE_PENDING_IMAGE_URI_PATH_KEY = "flutter_image_picker_pending_image_uri";
    FileUtils fileUtils;
    private File outputImagepath;//存储拍完照后的图片
    private Bitmap orc_bitmap;//拍照和相册获取图片的Bitmap
    private Uri uri = null;
    private Uri pendingCameraMediaUri;
    private FileUriResolver fileUriResolver;
    private String fileProviderName;
    private AndroidImageResizer imageResizer;

    public PhotoUtilsImagePickerMul(AppCompatActivity activity) {
        super(activity);
        this.fileUtils = new FileUtils();
        this.imageResizer = new AndroidImageResizer(activity, new ExifInfoCopier());
        this.fileProviderName = activity.getPackageName() + ".flutter.image_provider";
        this.fileUriResolver = new FileUriResolver() {
            @Override
            public Uri resolveFileProviderUriForFile(String fileProviderName, File file) {
                Log.d(TAG, fileProviderName);
                Uri uriForFile = FileProvider.getUriForFile(activity, fileProviderName, file);
                Log.d(TAG, file.getAbsolutePath());
                return uriForFile;
            }

            @Override
            public void getFullImagePath(final Uri imageUri, final OnPathReadyListener listener) {
                MediaScannerConnection.scanFile(activity, new String[]{(imageUri != null) ? imageUri.getPath() : ""}, null, (path, uri) -> listener.onPathReady(path));
            }
        };
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private void useFrontCamera(Intent intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            intent.putExtra("android.intent.extras.CAMERA_FACING", CameraCharacteristics.LENS_FACING_FRONT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
            }
        } else {
            intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        }
    }

    private File createTemporaryWritableFile(String suffix) {
        String filename = UUID.randomUUID().toString();
        File image;
        File externalFilesDirectory = activity.getCacheDir();

        try {
            externalFilesDirectory.mkdirs();
            image = File.createTempFile(filename, suffix, externalFilesDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    private File createTemporaryWritableImageFile() {
        return createTemporaryWritableFile(".jpg");
    }

    @Override
    public void take_photo() {

    }

    public void take_Album() {


        Intent pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickImageIntent.setType("image/*");
        pickImageIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        activity.startActivityForResult(pickImageIntent, SELECT_PHOTO);


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            switch (requestCode) {
                case SELECT_PHOTO:
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        Uri[] imageUris = null;
                        ClipData clipData = data.getClipData();
                        if (clipData != null) {
                            // 如果支持多选，则通过 getItemAt() 方法获取每个被选中的图片 URI
                            imageUris = new Uri[clipData.getItemCount()];

                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                imageUris[i] = clipData.getItemAt(i).getUri();
                            }
                        } else {
                            // 单选情况下直接获取图片 URI
                            imageUris = new Uri[]{data.getData()};
                        }


//                        Uri[] imageUrisResult = new Uri[imageUris.length];


                        List<Uri> mList = new ArrayList<>();
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri item = clipData.getItemAt(i).getUri();
                            String pathFromUri = fileUtils.getPathFromUri(activity, item);

                            Log.d("okhttp", "=============pathFromUri========>" + pathFromUri);
                            if (!fileUtils.isPicture(new File(pathFromUri))) {
                                continue;
                            }
                            mList.add(Uri.fromFile(new File(pathFromUri)));
                        }
                        Uri[] array = mList.toArray(new Uri[mList.size()]);


//                        String path = fileUtils.getPathFromUri(activity, data.getData());
//
//                        if (!fileUtils.isPicture(new File(path))) {
//                            return;
//                        }
//
//
//                        if (callback != null && !TextUtils.isEmpty(path)) {
//                            callback.getPath(uri, path);
//                        }
//            handleImageResult(path, false);
                        return;
                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void handleCaptureImageResult(int resultCode) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        fileUriResolver.getFullImagePath(pendingCameraMediaUri != null ? pendingCameraMediaUri : Uri.parse(retrievePendingCameraMediaUriPath()), path -> {
            Log.d(TAG, "==path==1=" + path);
            path = imageResizer.resizeImageIfNeeded(path, 860d, 820d, 60);
            Log.d(TAG, "==path==2=" + path);

            callback.getPath(pendingCameraMediaUri, path);
        });


    }

    void savePendingCameraMediaUriPath(Uri uri) {
        final SharedPreferences prefs = activity.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(SHARED_PREFERENCE_PENDING_IMAGE_URI_PATH_KEY, uri.getPath()).apply();
    }

    String retrievePendingCameraMediaUriPath() {
        final SharedPreferences prefs = activity.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return prefs.getString(SHARED_PREFERENCE_PENDING_IMAGE_URI_PATH_KEY, "");
    }

    interface OnPathReadyListener {
        void onPathReady(String path);
    }

    interface FileUriResolver {
        Uri resolveFileProviderUriForFile(String fileProviderName, File imageFile);

        void getFullImagePath(Uri imageUri, OnPathReadyListener listener);
    }


}
