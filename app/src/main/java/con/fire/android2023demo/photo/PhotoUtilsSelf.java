package con.fire.android2023demo.photo;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import con.fire.android2023demo.utils.BitmapUtils;


public class PhotoUtilsSelf extends PhotoSo {

    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识
    private static final String TAG = "Photo12Utils";
    private File outputImagepath;//存储拍完照后的图片
    private Bitmap orc_bitmap;//拍照和相册获取图片的Bitmap
    private Uri uri = null;

    public PhotoUtilsSelf(AppCompatActivity activity) {
        super(activity);
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

    public static Bitmap rotateBitmapByDegree(Bitmap bm, String path) {
        int degree = getBitmapDegree(path);
        Log.d(TAG, "===104===" + degree);

        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    private static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
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


        Log.d(TAG, "take_photo");
        try {
            //获取系統版本
            int currentapiVersion = Build.VERSION.SDK_INT;
            // 激活相机
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 判断存储卡是否可以用，可用进行存储
            if (hasSdcard()) {
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String filename = timeStampFormat.format(new Date());

                outputImagepath = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename + ".jpg");
                if (currentapiVersion < Build.VERSION_CODES.N) {
                    // 从文件中创建uri
                    uri = Uri.fromFile(outputImagepath);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                } else {

                    uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileProvider", outputImagepath);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                }
            }

            activity.startActivityForResult(intent, TAKE_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File uriToFileApiQ(Uri uri, Context context) {


        File file = null;
        if (uri == null) return file;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = context.getContentResolver();
            String displayName = System.currentTimeMillis() + Math.round((Math.random() + 1) * 1000)
                    + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));

            try {
                InputStream is = contentResolver.openInputStream(uri);
                File cache = new File(context.getCacheDir().getAbsolutePath(), displayName);
                FileOutputStream fos = new FileOutputStream(cache);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    FileUtils.copy(is, fos);
                }
                file = cache;
                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
                        displayImage(outputImagepath.getAbsolutePath());

                    } else if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
                        Uri data1 = data.getData();


                        Log.d(TAG, "===101===" + data1.getPath());
                        Bitmap bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(data.getData()));
//                bitmap = rotaingImageView(90, bitmap);

                        File path2 = BitmapUtils.saveBitmap(bitmap, System.currentTimeMillis() + "photo2", activity);
//                        String filepath = PhotoBitmapUtils.amendRotatePhoto(path2.getPath(), activity);

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                displayImage(path2.getAbsolutePath());
                                ImgUpdateDirection(uri, path2.getAbsolutePath());//显示图片,并且判断图片显示的方向,如果不正就放正

                            }
                        });

//                File file = saveBitmap(bitmap, System.currentTimeMillis() + "ko.jpg", activity);
//
//                bitmap = rotateBitmapByDegree(bitmap, file.getAbsolutePath());
//                 file = saveBitmap(bitmap, System.currentTimeMillis() + "k1o.jpg", activity);
//
////                File file = uriToFileApiQ(data1, activity);
//                Log.d(TAG, "===102===" + file.getAbsolutePath());
//                String imagePath = file.getAbsolutePath();
//                displayImage(imagePath);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     *
     */
    private void displayImage(String imagePath) {
        try {
            Log.d(TAG, "========displayImage==="+imagePath);

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

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    public Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        takeImg.setImageBitmap(bitmap1); // 展示刚拍过的照片
//        Log.d("111111 TakeActivity >>", "打印base64>>:" + takeimage);

        return bitmap1;
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


}
