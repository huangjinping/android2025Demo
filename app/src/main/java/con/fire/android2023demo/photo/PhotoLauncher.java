package con.fire.android2023demo.photo;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import con.fire.android2023demo.FileUtils;
import con.fire.android2023demo.utils.LogUtils;

//https://blog.csdn.net/chaixiangyang123/article/details/125797801
public class PhotoLauncher extends PhotoSo {

    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识
    ActivityResultLauncher<String> mGetContent;
    String TAG = "Okhttp";
    ActivityResultLauncher<Uri> takePhotoLauncher;

    FileUtils fileUtils;

    public PhotoLauncher(AppCompatActivity activity) {
        super(activity);
        fileUtils = new FileUtils();
        this.mGetContent = activity.registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                String path = fileUtils.getPathFromUri(activity, result);



                if (callback != null && !TextUtils.isEmpty(path)) {
                    callback.getPath(result, path);
                }
            }
        });
        this.takePhotoLauncher = activity.registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                callback.getPath(fileUri, TAKE_PHOTO_PATH);
                LogUtils.logS(TAG, TAKE_PHOTO_PATH + "takePhotoLauncher:===" + fileUri.getPath());

            }
        });


    }

    Uri fileUri;

    @Override
    public void take_photo() {
        fileUri = createImageFileUri(activity);
        if (fileUri != null) {
            takePhotoLauncher.launch(fileUri);
        }
    }

    private String TAKE_PHOTO_PATH;

    private Uri createImageFileUri(Context context) {

        Uri uri;
        File temporaryWritableFile = createTemporaryWritableFile(".jpg");
        TAKE_PHOTO_PATH = temporaryWritableFile.getAbsolutePath();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(temporaryWritableFile.getAbsoluteFile());
        } else {
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", temporaryWritableFile.getAbsoluteFile());
        }
        return uri;
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

    @Override
    public void take_Album() {
        mGetContent.launch("image/*");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
