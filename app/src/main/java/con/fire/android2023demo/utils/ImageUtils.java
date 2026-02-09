package con.fire.android2023demo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * author Created by "branch" on 11/15/21.
 * email : "branchCode"
 */
public class ImageUtils {


    // 黑白
    public static final float colormatrix_heibai[] = {0.8f, 1.6f, 0.2f, 0, -163.9f, 0.8f, 1.6f, 0.2f, 0, -163.9f, 0.8f, 1.6f, 0.2f, 0, -163.9f, 0, 0, 0, 1.0f, 0};
    // 怀旧
    public static final float colormatrix_huaijiu[] = {0.2f, 0.5f, 0.1f, 0, 40.8f, 0.2f, 0.5f, 0.1f, 0, 40.8f, 0.2f, 0.5f, 0.1f, 0, 40.8f, 0, 0, 0, 1, 0};
    // 哥特
    public static final float colormatrix_gete[] = {1.9f, -0.3f, -0.2f, 0, -87.0f, -0.2f, 1.7f, -0.1f, 0, -87.0f, -0.1f, -0.6f, 2.0f, 0, -87.0f, 0, 0, 0, 1.0f, 0};
    // 淡雅
    public static final float colormatrix_danya[] = {0.6f, 0.3f, 0.1f, 0, 73.3f, 0.2f, 0.7f, 0.1f, 0, 73.3f, 0.2f, 0.3f, 0.4f, 0, 73.3f, 0, 0, 0, 1.0f, 0};
    // 蓝调
    public static final float colormatrix_landiao[] = {2.1f, -1.4f, 0.6f, 0.0f, -71.0f, -0.3f, 2.0f, -0.3f, 0.0f, -71.0f, -1.1f, -0.2f, 2.6f, 0.0f, -71.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 光晕
    public static final float colormatrix_guangyun[] = {0.9f, 0, 0, 0, 64.9f, 0, 0.9f, 0, 0, 64.9f, 0, 0, 0.9f, 0, 64.9f, 0, 0, 0, 1.0f, 0};
    // 梦幻
    public static final float colormatrix_menghuan[] = {0.8f, 0.3f, 0.1f, 0.0f, 46.5f, 0.1f, 0.9f, 0.0f, 0.0f, 46.5f, 0.1f, 0.3f, 0.7f, 0.0f, 46.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 酒红
    public static final float colormatrix_jiuhong[] = {1.2f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0, 0, 0, 1.0f, 0};
    // 胶片
    public static final float colormatrix_fanse[] = {-1.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, -1.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, -1.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 湖光掠影
    public static final float colormatrix_huguang[] = {0.8f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f, 0, 0, 0, 1.0f, 0};
    // 褐片
    public static final float colormatrix_hepian[] = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0, 0, 0, 1.0f, 0};
    // 复古
    public static final float colormatrix_fugu[] = {0.9f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0, 0, 0, 1.0f, 0};
    // 泛黄
    public static final float colormatrix_huan_huang[] = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0, 0, 0, 1.0f, 0};
    // 传统
    public static final float colormatrix_chuan_tong[] = {1.0f, 0.0f, 0.0f, 0, -10f, 0.0f, 1.0f, 0.0f, 0, -10f, 0.0f, 0.0f, 1.0f, 0, -10f, 0, 0, 0, 1, 0};
    // 胶片2
    public static final float colormatrix_jiao_pian[] = {0.71f, 0.2f, 0.0f, 0.0f, 60.0f, 0.0f, 0.94f, 0.0f, 0.0f, 60.0f, 0.0f, 0.0f, 0.62f, 0.0f, 60.0f, 0, 0, 0, 1.0f, 0};
    // 锐色
    public static final float colormatrix_ruise[] = {4.8f, -1.0f, -0.1f, 0, -388.4f, -0.5f, 4.4f, -0.1f, 0, -388.4f, -0.5f, -1.0f, 5.2f, 0, -388.4f, 0, 0, 0, 1.0f, 0};
    // 清宁
    public static final float colormatrix_qingning[] = {0.9f, 0, 0, 0, 0, 0, 1.1f, 0, 0, 0, 0, 0, 0.9f, 0, 0, 0, 0, 0, 1.0f, 0};
    // 浪漫
    public static final float colormatrix_langman[] = {0.9f, 0, 0, 0, 63.0f, 0, 0.9f, 0, 0, 63.0f, 0, 0, 0.9f, 0, 63.0f, 0, 0, 0, 1.0f, 0};
    // 夜色
    public static final float colormatrix_yese[] = {1.0f, 0.0f, 0.0f, 0.0f, -66.6f, 0.0f, 1.1f, 0.0f, 0.0f, -66.6f, 0.0f, 0.0f, 1.0f, 0.0f, -66.6f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    public static Bitmap shotMultiView(View contanierViewWith, View... views) {
        if (views == null || views.length == 0) {
            return null;
        }

        Bitmap mBitmap = null;
        List<Bitmap> bitmaps = new ArrayList<>();
        int height = 0;
        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            Bitmap bitmapTemp = null;
            if (view.getVisibility() == View.GONE) {
                continue;
            }

            if (view instanceof ListView) {
                bitmapTemp = shotListView((ListView) view);
            } else if (view instanceof RecyclerView) {
                bitmapTemp = shotRecyclerView((RecyclerView) view);
            } else if (view instanceof ScrollView) {
                bitmapTemp = shotScrollView((ScrollView) view);
            } else {
                bitmapTemp = getViewBp(view);
            }
            if (bitmapTemp != null) {
                height += bitmapTemp.getHeight();
                bitmaps.add(bitmapTemp);
            }
        }


        mBitmap = Bitmap.createBitmap(contanierViewWith.getWidth(), height, Bitmap.Config.ARGB_8888);
        Drawable lBackground = contanierViewWith.getBackground();
        Canvas bigCanvas = new Canvas(mBitmap);
        if (lBackground instanceof ColorDrawable) {
            ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
            int lColor = lColorDrawable.getColor();
            bigCanvas.drawColor(lColor);
        }


        Paint paint = new Paint();
        int iHeight = 0;
        for (int i = 0; i < bitmaps.size(); i++) {
            Bitmap bmp = bitmaps.get(i);
            bigCanvas.drawBitmap(bmp, 0, iHeight, paint);
            iHeight += bmp.getHeight();
            bmp.recycle();
            bmp = null;
        }
        return mBitmap;
    }

    public static Bitmap getViewBp(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(), (int) v.getX() + v.getMeasuredWidth(), (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return b;
    }

    public static File saveBitMapImage(Context mContext, Bitmap bmp) {
        File appDir = new File(mContext.getExternalCacheDir(), "harris");

        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file;
        } catch (FileNotFoundException e) {
            Log.d("saveBitMapImage", "==============okhttp   ===1=======" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("saveBitMapImage", "==============okhttp   =2=========" + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    public static Bitmap shotScrollView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    public static Bitmap shotListView(ListView listview) {

        ListAdapter adapter = listview.getAdapter();
        int itemscount = adapter.getCount();
        int allitemsheight = 0;
        List<Bitmap> bmps = new ArrayList<Bitmap>();

        for (int i = 0; i < itemscount; i++) {

            View childView = adapter.getView(i, null, listview);
            childView.measure(View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            childView.setDrawingCacheEnabled(true);
            childView.buildDrawingCache();
            bmps.add(childView.getDrawingCache());
            allitemsheight += childView.getMeasuredHeight();
        }

        Bitmap bigbitmap = Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
        Canvas bigcanvas = new Canvas(bigbitmap);

        Paint paint = new Paint();
        int iHeight = 0;

        for (int i = 0; i < bmps.size(); i++) {
            Bitmap bmp = bmps.get(i);
            bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
            iHeight += bmp.getHeight();
            bmp.recycle();
            bmp = null;
        }

        return bigbitmap;
    }

    public static Bitmap shotRecyclerView(RecyclerView view) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = view.getBackground();
            if (lBackground instanceof ColorDrawable) {
                ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
                int lColor = lColorDrawable.getColor();
                bigCanvas.drawColor(lColor);
            }

            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
        }
        return bigBitmap;
    }

    /**
     * 调整图片的色相，饱和度，灰度
     *
     * @param srcBitmap
     * @param rotate
     * @param saturation
     * @param scale
     * @return
     */
    public static Bitmap changeImageTheme(Bitmap srcBitmap, float rotate, float saturation, float scale) {

        //调整色相
        ColorMatrix rotateMatrix = new ColorMatrix();
        rotateMatrix.setRotate(0, rotate);
        rotateMatrix.setRotate(1, rotate);
        rotateMatrix.setRotate(2, rotate);

        //调整色彩饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        //调整灰度
        ColorMatrix scaleMatrix = new ColorMatrix();
        scaleMatrix.setScale(scale, scale, scale, 1);

        //叠加效果
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.postConcat(rotateMatrix);
        colorMatrix.postConcat(saturationMatrix);
        colorMatrix.postConcat(scaleMatrix);

        //创建一个大小相同的空白Bitmap
        Bitmap dstBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //载入Canvas,Paint
        Canvas canvas = new Canvas(dstBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        //绘图
        canvas.drawBitmap(srcBitmap, 0, 0, paint);

        return dstBitmap;
    }

    /**
     * 通过更改图片像素点的RGBA值，生成底片效果
     *
     * @param scrBitmap
     * @return
     */
    public static Bitmap beautyImage(Bitmap scrBitmap) {

        int width = scrBitmap.getWidth();
        int height = scrBitmap.getHeight();
        int count = width * height;

        int[] oldPixels = new int[count];
        int[] newPixels = new int[count];

        scrBitmap.getPixels(oldPixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < oldPixels.length; i++) {
            int pixel = oldPixels[i];
            int r = Color.red(pixel);
            int g = Color.green(pixel);
            int b = Color.blue(pixel);

            r = 255 - r;
            g = 255 - g;
            b = 255 - b;

            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }

            if (r < 0) {
                r = 0;
            }
            if (g < 0) {
                g = 0;
            }
            if (b < 0) {
                b = 0;
            }

            newPixels[i] = Color.rgb(r, g, b);

        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(newPixels, 0, width, 0, 0, width, height);

        return bitmap;
    }

    public static void insertImage(Context context, File file) throws FileNotFoundException {
//        String fileName = System.currentTimeMillis() + "image.png";
//        String insertImage = MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(file);
//        intent.setData(uri);
//        context.sendBroadcast(intent);


        String fileName = System.currentTimeMillis() + "image.png";
        String insertImage = null;
        try {
            insertImage = MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("okhees", "==========" + insertImage);



        File file1 = new File(getRealPathFromURI(Uri.parse(insertImage), context));
        updatePhotoMedia(file1, context);
    }



    /**
     * 无权限保存Bitmap到Download目录，并同步到相册
     * @param context 上下文（Activity/Fragment）
     * @param bitmap 要保存的图片
     * @return 保存成功返回图片的Uri，失败返回null
     */
    public static Uri saveImageToDownloadNoPermission(Context context, Bitmap bitmap) {
        if (bitmap == null) {
            Toast.makeText(context, "图片不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }

        // 生成唯一文件名（避免重复）
        String fileName = new SimpleDateFormat("IMG_yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date()) + ".jpg";

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
            imageUri = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
            );

            if (imageUri == null) {
                Toast.makeText(context, "保存失败：无法获取存储Uri", Toast.LENGTH_SHORT).show();
                return null;
            }

            // 写入图片数据
            outputStream = context.getContentResolver().openOutputStream(imageUri);
            if (outputStream == null) {
                Toast.makeText(context, "保存失败：无法打开输出流", Toast.LENGTH_SHORT).show();
                // 插入失败，删除无效记录
                context.getContentResolver().delete(imageUri, null, null);
                return null;
            }

            // 压缩并保存图片（质量80，可调整）
            boolean isCompressSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
            if (!isCompressSuccess) {
                Toast.makeText(context, "保存失败：图片压缩失败", Toast.LENGTH_SHORT).show();
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
                context.sendBroadcast(
                        new android.content.Intent(
                                android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                imageUri
                        )
                );
            }

            Toast.makeText(context, "图片已保存到Download并同步相册", Toast.LENGTH_SHORT).show();
            return imageUri;

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "保存失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    //更新图库
    private static void updatePhotoMedia(File file, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        context.sendBroadcast(intent);
    }

    //得到绝对地址
    private static String getRealPathFromURI(Uri contentUri, Context context) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String fileStr = cursor.getString(column_index);
        cursor.close();
        return fileStr;
    }


}
