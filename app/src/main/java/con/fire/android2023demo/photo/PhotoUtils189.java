package con.fire.android2023demo.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import con.fire.android2023demo.FileUtils;
import con.fire.android2023demo.utils.BitmapUtils;
import con.fire.android2023demo.utils.ImageUtilsSp;
import con.fire.android2023demo.utils.WCommonCallback;

public class PhotoUtils189 extends PhotoSo {
    private static final String TAG = "Photoils189";

    public PhotoUtils189(AppCompatActivity activity) {
        super(activity);
    }

    //    /**
//     * 压缩
//     */
//    public static File compressTo(Context context, File file) {
//        File newFile = new CompressHelper.Builder(context)
////                .setMaxWidth(720)  // 默认最大宽度为720
////                .setMaxHeight(960) // 默认最大高度为960
//                .setQuality(80)    // 默认压缩质量为80
////                .setFileName(yourFileName) // 设置你需要修改的文件名
//                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
////                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
////                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
//                .build()
//                .compressToFile(file);
//        return newFile;
//    }
    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识

    @Override
    public void take_photo() {

    }

    @Override
    public void take_Album() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "0====" + requestCode);

        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.d(TAG, "requestCode" + requestCode + "----------resultCode" + resultCode);

                try {

                    if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK) {

                    } else if (requestCode == SELECT_PHOTO) {
                        ImageUtilsSp.compressImageFromImageUri(activity, data.getData(), new WCommonCallback<String>() {
                            @Override
                            public void callback(String data) {
                                callback.getPath(null, data);
                            }
                        });
                    }
                    Log.d(TAG, "0==111==12222");

                } catch (Exception e) {
                    Log.d(TAG, "0==111==12" + e.getMessage());

                    e.printStackTrace();
                }
            }
        }.start();
    }

}
