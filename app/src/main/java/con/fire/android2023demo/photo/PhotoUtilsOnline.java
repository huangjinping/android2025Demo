package con.fire.android2023demo.photo;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.luck.picture.lib.basic.PictureSelectionCameraModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;




public class PhotoUtilsOnline {

    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识
    private static final String TAG = "PhotoUtil11sOnline";
    public AppCompatActivity activity;
    public Callback callback;
    private File outputImagepath;//存储拍完照后的图片
    private Bitmap orc_bitmap;//拍照和相册获取图片的Bitmap
    private Uri uri = null;

    public PhotoUtilsOnline(AppCompatActivity activity, Callback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap Bitmap
     */

    public static File saveBitmap(Bitmap bitmap, String name, Context ct) {
        String savePath;
        File filePic;
        savePath = ct.getExternalFilesDir(null) + "/" + name + ".png";
//        savePath=ct.getCacheDir().getAbsolutePath()+ "/" + name + ".png";

        try {
            filePic = new File(savePath);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {

            return null;
        }
        return filePic;
    }

    public void take_Album() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            activity.startActivityForResult(intent, SELECT_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照获取图片
     **/
    public void take_photo() {

        PictureSelector select = PictureSelector.create(activity);
        PictureSelectionCameraModel camera = select.openCamera(SelectMimeType.ofImage());
        camera.isCameraAroundState(false);

        Log.d(TAG, "----0---");
//        camera.setOutputCameraDir(activity.getExternalCacheDir().getPath());
        camera.setCompressEngine(new CompressFileEngine() {
            @Override
            public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call) {
//                Log.d(TAG, "onStartCompress"+source.get(0).getPath());


//                Luban.with(context).load(source).ignoreBy(100).setCompressListener(new OnCompressListener() {
//                    @Override
//                    public void onStart() {
//                        Log.d(TAG, "onStart");
//
//                    }
//
//                    @Override
//                    public void onSuccess(int index, File compressFile) {
//                        Log.d(TAG, "onSuccess" + compressFile.getAbsolutePath());
//                        displayImage(compressFile.getAbsolutePath());
//                        call.onCallback(String.valueOf(source), compressFile.getAbsolutePath());
//                    }
//
//                    @Override
//                    public void onError(int index, Throwable e) {
//                        Log.d(TAG, "onError");
//
//                    }
//
//                }).launch();
            }
        });
        camera.forResult(new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(ArrayList<LocalMedia> result) {
                Log.d(TAG, "onResult");

                String zhenUrl = result.get(0).getCompressPath();


            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel");


            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK) {



                displayImage(outputImagepath.getAbsolutePath());
            } else if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
                Uri data1 = data.getData();

                Log.d(TAG, "===101===" + data1.getPath());

                Bitmap bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(data.getData()));

                File file = saveBitmap(bitmap, System.currentTimeMillis() + "ko.jpg", activity);


//                File file = uriToFileApiQ(data1, activity);
                Log.d(TAG, "===102===" + file.getAbsolutePath());
                String imagePath = file.getAbsolutePath();
                displayImage(imagePath);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void displayImage(String imagePath) {
        try {
            Log.d(TAG, "========displayImage===");
            if (!TextUtils.isEmpty(imagePath)) {
                orc_bitmap = comp(BitmapFactory.decodeFile(imagePath)); //压缩图片

                if (orc_bitmap != null) {
                    ImgUpdateDirection(uri, imagePath);//显示图片,并且判断图片显示的方向,如果不正就放正
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
