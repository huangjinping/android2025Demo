package con.fire.android2023demo.ui;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.OutputStream;

public class ImageSaveNoPermissionUtil {

    /**
     * 无权限保存Bitmap到Download目录，并同步到相册
     *
     * @param context 上下文（Activity/Fragment）
     * @param bitmap  要保存的图片
     * @return 保存成功返回图片的Uri，失败返回null
     */
    public static Uri saveImageToDownloadNoPermission(Context context, Bitmap bitmap) {
        if (bitmap == null) {

//            Toast.makeText(context, "图片不能为空", Toast.LENGTH_SHORT).show();
            Log.d("okhttps","图片不能为空");

            return null;
        }

        // 生成唯一文件名（避免重复）
        String fileName = System.currentTimeMillis() + ".jpg";

        // 构建MediaStore插入参数
        ContentValues contentValues = new ContentValues();
        // 1. 指定保存路径：Download目录（核心）
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
        // 2. 文件基本信息
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        contentValues.put(MediaStore.Images.Media.DATE_MODIFIED, System.currentTimeMillis() / 1000);

        // Android 10+ 标记为待处理，写入完成后取消
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 1);
        }

        Uri imageUri = null;
        OutputStream outputStream = null;
        try {
            // 插入图片信息到MediaStore，获取Uri（无需权限）
            imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            if (imageUri == null) {
//                Toast.makeText(context, "保存失败：无法获取存储Uri", Toast.LENGTH_SHORT).show();
                Log.d("okhttps","保存失败：无法获取存储Uri");

                return null;
            }

            // 写入图片数据
            outputStream = context.getContentResolver().openOutputStream(imageUri);
            if (outputStream == null) {
//                Toast.makeText(context, "保存失败：无法打开输出流", Toast.LENGTH_SHORT).show();
                Log.d("okhttps","保存失败：无法打开输出流");

                // 插入失败，删除无效记录
                context.getContentResolver().delete(imageUri, null, null);
                return null;
            }

            // 压缩并保存图片（质量80，可调整）
            boolean isCompressSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
            if (!isCompressSuccess) {
                Log.d("okhttps","保存失败：图片压缩失败");
//                Toast.makeText(context, "保存失败：图片压缩失败", Toast.LENGTH_SHORT).show();
                context.getContentResolver().delete(imageUri, null, null);
                return null;
            }

            // Android 10+：标记为已完成，触发系统扫描（同步相册关键）
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear();
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0);
                context.getContentResolver().update(imageUri, contentValues, null, null);
            }
            // Android 10以下：发送广播刷新相册（无需权限）
            else {
                context.sendBroadcast(new android.content.Intent(android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri));
            }
            Log.d("okhttps","图片已保存到Download并同步相册");

//            Toast.makeText(context, "图片已保存到Download并同步相册", Toast.LENGTH_SHORT).show();
            return imageUri;

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("okhttps","保存失败：" + e.getMessage());

//            Toast.makeText(context, "保存失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            // 异常时删除无效记录
            if (imageUri != null) {
                context.getContentResolver().delete(imageUri, null, null);
            }
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}