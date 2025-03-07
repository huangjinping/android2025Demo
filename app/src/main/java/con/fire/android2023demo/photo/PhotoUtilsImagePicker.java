package con.fire.android2023demo.photo;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraCharacteristics;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import con.fire.android2023demo.FileUtils;
import con.fire.android2023demo.utils.AndroidImageResizer;
import con.fire.android2023demo.utils.ExifInfoCopier;


public class PhotoUtilsImagePicker extends PhotoSo {
    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 201;//启动相册标识
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

    public PhotoUtilsImagePicker(AppCompatActivity activity) {
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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = createTemporaryWritableImageFile();
        pendingCameraMediaUri = Uri.parse("file:" + imageFile.getAbsolutePath());

        useFrontCamera(intent);
        Log.d(TAG, "" + imageFile.getAbsolutePath());
        Uri imageUri = fileUriResolver.resolveFileProviderUriForFile(fileProviderName, imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        savePendingCameraMediaUriPath(pendingCameraMediaUri);
        try {
            activity.startActivityForResult(intent, TAKE_PHOTO);
        } catch (Exception e) {
            try {
                // If we can't delete the file again here, there's not really anything we can do about it.
                //noinspection ResultOfMethodCallIgnored
                imageFile.delete();
            } catch (SecurityException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void take_Album() {
        Intent pickImageIntent;
        boolean usePhotoPicker = false;
        if (usePhotoPicker && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pickImageIntent = new ActivityResultContracts.PickVisualMedia().createIntent(activity, new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
        } else {
            pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
            pickImageIntent.setType("image/*");
        }
        activity.startActivityForResult(pickImageIntent, SELECT_PHOTO);


    }

//    public void take_Album() {
//        Intent pickMediaIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        pickMediaIntent.setType("*/*");
//        String[] mimeTypes = {"image/*"};
//        pickMediaIntent.putExtra("CONTENT_TYPE", mimeTypes);
//        activity.startActivityForResult(pickMediaIntent, SELECT_PHOTO);
//    }

    public void take_Album1() {
        Intent pickImageIntent;
        boolean usePhotoPicker = true;
        if (usePhotoPicker && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pickImageIntent = new ActivityResultContracts.PickVisualMedia().createIntent(activity, new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
        } else {
            String[] mimeTypes = {"image/jpeg", "image/png"};
            pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
            pickImageIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            pickImageIntent.setType("image/*");
        }
        activity.startActivityForResult(pickImageIntent, SELECT_PHOTO);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            Log.d(TAG, "==189===============11111111");

            switch (requestCode) {
                case SELECT_PHOTO:
                    Log.d(TAG, resultCode+"==189===============22222222222==="+requestCode);

                    if (resultCode == Activity.RESULT_OK && data != null) {

                        Log.d(TAG, "==189=" + data.getData().getEncodedPath());

                        String path = fileUtils.getPathFromUri(activity, data.getData());

                        if (!fileUtils.isPicture(new File(path))) {
                            return;
                        }

//                        path = imageResizer.resizeImageIfNeeded(path, 800d, 800d, 80);

                        if (callback != null && !TextUtils.isEmpty(path)) {
                            callback.getPath(uri, path);
                        }
//            handleImageResult(path, false);
                        return;
                    }
                    break;
                case TAKE_PHOTO:
                    Log.d(TAG, "=======TAKE_PHOTO===0===");
                    if (resultCode == Activity.RESULT_OK) {
                        Log.d(TAG, "=======TAKE_PHOTO===1===");
                        handleCaptureImageResult(resultCode);
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
//            path = imageResizer.resizeImageIfNeeded(path, 1080d, 1080d, 80);
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
