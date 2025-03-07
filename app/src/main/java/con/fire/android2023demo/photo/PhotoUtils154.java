package con.fire.android2023demo.photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import con.fire.android2023demo.FileUtils;

public class PhotoUtils154 extends PhotoSo {
    private static final int ALBUM_REQUEST_CODE = 0x00000011;
    private static final String TAG = "Photo54Util";
    FileUtils fileUtils;
    private boolean isAndroidQ = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    public PhotoUtils154(AppCompatActivity activity) {
        super(activity);
        fileUtils = new FileUtils();
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

    @Override
    public void take_photo() {

    }

    @Override
    public void take_Album() {
        Intent albumIntent = new Intent("android.intent.action.GET_CONTENT");
        albumIntent.setType("image/*"); // 开启Pictures画面Type设定为image
        activity.startActivityForResult(albumIntent, ALBUM_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = null;
        if (data != null) {
            uri = data.getData();
            String frontPath = fileUtils.getPathFromUri(activity, uri);
            Log.d(TAG, "0====" + frontPath);
//            File file = compressTo(activity, new File(frontPath));
//            Log.d(TAG, "1====" + file.getAbsolutePath());


        }
    }

}
