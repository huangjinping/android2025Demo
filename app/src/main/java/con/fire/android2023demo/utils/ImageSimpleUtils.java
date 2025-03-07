package con.fire.android2023demo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * https://mp.weixin.qq.com/s/t3XSEDUAcWegs9VYyFc0Gg
 */
public class ImageSimpleUtils {


    public static String getSimplePicPath(Context context) {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String filename = timeStampFormat.format(new Date());

        File outputImagepath = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filename + ".jpg");

        return outputImagepath.getAbsolutePath();
    }


    private int calculateSampleSize(BitmapFactory.Options options, int targetWidth, int targetHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int sampleSize = 1;
        if (height > targetHeight || width > targetWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / sampleSize) >= targetHeight && (halfWidth / sampleSize) >= targetWidth) {
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }
    public static void resizePicture(Context context, String srcPath, String desPath) {
//        BitmapFactory.Options op = new BitmapFactory.Options();
//        Bitmap scaledBitmap = BitmapFactory.decodeFile(srcPath, op);
//
//
//        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, op);
//
//        scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
//        saveBitmapFile(scaledBitmap, new File(getSimplePicPath(context)));
    }

    public static void compressPicture(Context context, String srcPath, String desPath) {

        try {
//            Bitmap bitmap = ImageUtil.decodeSampledBitmapFromFile(new File(srcPath), 1000, 1000);
//            saveBitmapFile(bitmap, new File(getSimplePicPath(context)));
//            Log.d("saveBitmapFile", "------------------------1-------");


            FileOutputStream fos = null;
            BitmapFactory.Options op = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            op.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(srcPath, op);
            op.inJustDecodeBounds = false;
            // 缩放图片的尺寸
            float w = op.outWidth;
            float h = op.outHeight;
            float hh = 1024f;//
            float ww = 1024f;//
            // 最长宽度或高度1024
            float be = 1.0f;
            if (w > h && w > ww) {
                be = (float) (w / ww);
            } else if (w < h && h > hh) {
                be = (float) (h / hh);
            }


//            Bitmap scaledBitmap = BitmapFactory.decodeFile(srcPath, op);
//
//            //check the rotation of the image and display it properly
//            ExifInterface exif;
//            exif = new ExifInterface(srcPath);
//            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
//            Matrix matrix = new Matrix();
//            if (orientation == 6) {
//                matrix.postRotate(90);
//            } else if (orientation == 3) {
//                matrix.postRotate(180);
//            } else if (orientation == 8) {
//                matrix.postRotate(270);
//            }
//            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
//            saveBitmapFile(scaledBitmap, new File(getSimplePicPath(context)));
//            Log.d("saveBitmapFile", "---------------------库-------");

            //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


            op.inSampleSize = ImageUtil.calculateInSampleSize(op, 1000, 1000);// 设置缩放比例,这个数字越大,图片大小越小.
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            bitmap = BitmapFactory.decodeFile(srcPath, op);
            int i = getPictureDegree(srcPath);
            if (i != ExifInterface.ORIENTATION_UNDEFINED) {
                bitmap = rotaingImageView(i, bitmap);
            }

            saveBitmapFile(bitmap, new File(getSimplePicPath(context)));
            Log.d("saveBitmapFile", "------------------------1-------");

            int desWidth = (int) (w / be);
            int desHeight = (int) (h / be);

            bitmap = Bitmap.createScaledBitmap(bitmap, desWidth, desHeight, true);
//        saveBitmapFile(bitmap, new File(getSimplePicPath(context)));
//        Log.d("saveBitmapFile", "------------------------2-------");

            try {
                fos = new FileOutputStream(desPath);
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveBitmapFile(Bitmap bitmap, File filePath) {


        Log.d("saveBitmapFile", filePath + "");
        try {
            File file = new File(filePath.getAbsolutePath());//将要保存图片的路径
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int getPictureDegree(String path) {
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


    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

}
