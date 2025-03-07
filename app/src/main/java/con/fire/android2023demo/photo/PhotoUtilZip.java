package con.fire.android2023demo.photo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class PhotoUtilZip {


    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识
    private static final String TAG = "Photo12Utils";
    public AppCompatActivity activity;
    public Callback callback;
    private Uri mImageUri = null;
    private File mediaFile;
    private Bitmap orc_bitmap;//拍照和相册获取图片的Bitmap

    public PhotoUtilZip(AppCompatActivity activity, Callback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    /**
     * 拍照获取图片
     **/
    public void take_photo() {

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mImageUri = getOutputMediaFileUri(activity);

        Log.d("okhttps", "====000===11==555222>>>>" + mImageUri.getPath());

        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        activity.startActivityForResult(takePhotoIntent, TAKE_PHOTO);

    }

    public Uri getOutputMediaFileUri(Context context) {

        try {
//            File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//            if (!mediaStorageDir.exists()) {
//                if (!mediaStorageDir.mkdirs()) {
//                    return null;
//                }
//            }
            mediaFile = new File(activity.getExternalCacheDir().getAbsolutePath() + "/" + System.currentTimeMillis() + ".png");//注意这里需要和filepaths.xml中配置的一样
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// sdk >= 24  android7.0以上
            Uri contentUri = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".fileProvider",//与清单文件中android:authorities的值保持一致
                    mediaFile);//FileProvider方式或者ContentProvider。也可使用VmPolicy方式

            return contentUri;

        } else {
            return Uri.fromFile(mediaFile);//或者 Uri.isPaise("file://"+file.toString()

        }
//        try {
////            File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
////            if (!mediaStorageDir.exists()) {
////                if (!mediaStorageDir.mkdirs()) {
////                    return null;
////                }
////            }
//            mediaFile = new File(context.getExternalCacheDir()
//                    + File.separator + System.currentTimeMillis() + ".png");//注意这里需要和filepaths.xml中配置的一样
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Log.d("okhttps", "==" + mediaFile.getAbsolutePath());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// sdk >= 24  android7.0以上
//            Uri contentUri = FileProvider.getUriForFile(context,
//                    context.getApplicationContext().getPackageName() + ".fileProvider",//与清单文件中android:authorities的值保持一致
//                    mediaFile);//FileProvider方式或者ContentProvider。也可使用VmPolicy方式
//
//            return contentUri;
//
//        } else {
//            return Uri.fromFile(mediaFile);//或者 Uri.isPaise("file://"+file.toString()
//
//        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == TAKE_PHOTO) {
//                displayImage(mImageUri.getPath());
                displayImage(mediaFile.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍完照和从相册获取玩图片都要执行的方法(根据图片路径显示图片)
     */
    private void displayImage(String imagePath) {
        try {
            Log.d(TAG, "========displayImage=1==" + imagePath);
            if (!TextUtils.isEmpty(imagePath)) {
                Log.d(TAG, "========displayImage=2==" + imagePath);

                orc_bitmap = comp(BitmapFactory.decodeFile(imagePath)); //压缩图片
                Log.d(TAG, "========displayImage==3=" + imagePath);

                if (orc_bitmap != null) {
                    Log.d(TAG, "========displayImage=4==" + imagePath);

                    ImgUpdateDirection(mImageUri, imagePath);//显示图片,并且判断图片显示的方向,如果不正就放正
                }
            } else {
                Toast.makeText(activity, "图片获取失败", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //比例压缩
    private Bitmap comp(Bitmap image) {
        if (image != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
                baos.reset();//重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            //开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            float hh = 800f;//这里设置高度为800f
            float ww = 480f;//这里设置宽度为480f
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;//be=1表示不缩放
            if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;//设置缩放比例
            newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
            //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            isBm = new ByteArrayInputStream(baos.toByteArray());
            bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
            return bitmap;//压缩好比例大小后再进行质量压缩
        } else {
            return null;
        }

    }

    //改变拍完照后图片方向不正的问题
    private void ImgUpdateDirection(Uri uri, String filepath) {
        try {
            if (callback != null) {
                callback.getPath(uri, filepath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface Callback {
        void getPath(Uri uri, String path);
    }
}
