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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import con.fire.android2023demo.FileUtils;


public class PhotoPrestamoPlus extends PhotoSo {

    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识
    /**
     * 获取的时间格式
     */
    public static final String SHIJIAN_LEIXING = "yyyyMMddHHmmss";
    /**
     * 图片种类
     */
    public static final String TUPIAN_LEIXING = ".png";
    private static final String TAG = "PhotoPres1tamoPlus";
    /**
     * 存放拍摄图片的文件夹
     */
    private static final String SAVETUPIAN_FILEPATH = "/Photos";
    FileUtils fileUtils;
    private File outputImagepath;//存储拍完照后的图片
    private Bitmap orc_bitmap;//拍照和相册获取图片的Bitmap
    private Uri UrdizhiL = null;


    public PhotoPrestamoPlus(AppCompatActivity activity) {
        super(activity);
        this.fileUtils = new FileUtils();

    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    //图片空间
    public static Bitmap tuPianKongjian(Bitmap tuxiang) {
        tuxiang = xuanZhuanTupian(90, tuxiang);
        //图片允许最大空间   单位：KB
        double allZuidaKongjian = 100.00;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream zijieShujuShuchu = new ByteArrayOutputStream();
        tuxiang.compress(Bitmap.CompressFormat.JPEG, 100, zijieShujuShuchu);
        byte[] tuxaingShuzu = zijieShujuShuchu.toByteArray();
        //将字节换成KB
        double shuzuZijieDaxiao = tuxaingShuzu.length / 1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (shuzuZijieDaxiao > allZuidaKongjian) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = shuzuZijieDaxiao / allZuidaKongjian;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            tuxiang = yaSuoTupain(tuxiang, tuxiang.getWidth() / Math.sqrt(i),
                    tuxiang.getHeight() / Math.sqrt(i));
            return tuxiang;
        }
        return tuxiang;
    }

    /**
     * 旋转图片
     *
     * @param angle  被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap xuanZhuanTupian(int angle, Bitmap bitmap) {
        Bitmap zuizhongTupian = null;
// 根据旋转角度，生成旋转矩阵
        Matrix xuanzhanJuzheng = new Matrix();
        xuanzhanJuzheng.postRotate(angle);
        try {
// 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            zuizhongTupian = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), xuanzhanJuzheng, true);
        } catch (OutOfMemoryError e) {
        }
        if (zuizhongTupian == null) {
            zuizhongTupian = bitmap;
        }
        if (bitmap != zuizhongTupian) {
            bitmap.recycle();
        }
        return zuizhongTupian;
    }

    /***
     * 图片的缩放方法
     *
     * @param tupian
     *            ：源图片资源
     * @param xinKuandu
     *            ：缩放后宽度
     * @param xinGaodu
     *            ：缩放后高度
     * @return
     */
    public static Bitmap yaSuoTupain(Bitmap tupian, double xinKuandu,
                                     double xinGaodu) {
        // 获取这个图片的宽和高
        float dangqianKuandu = tupian.getWidth();
        float dangqianGaodu = tupian.getHeight();
        // 创建操作图片用的matrix对象
        Matrix tupianCaozuoDuixiang = new Matrix();
        // 计算宽高缩放率
        float kuanduSuofangLv = ((float) xinKuandu) / dangqianKuandu;
        float GaoduSuofangLv = ((float) xinGaodu) / dangqianGaodu;
        // 缩放图片动作
        tupianCaozuoDuixiang.postScale(kuanduSuofangLv, GaoduSuofangLv);
        Bitmap zuihouTupian = Bitmap.createBitmap(tupian, 0, 0, (int) dangqianKuandu,
                (int) dangqianGaodu, tupianCaozuoDuixiang, true);
        int width1 = zuihouTupian.getWidth();
        int height1 = zuihouTupian.getHeight();
        return zuihouTupian;
    }

    /**
     * 保存bitmap到本地
     *
     * @param tupian Bitmap
     */

    public static File baocunTupianDaoBendi(Bitmap tupian, String mingzi, Context chiyouzhe) {
        String baocunLujing;
        File tupianWenjian;
        baocunLujing = chiyouzhe.getExternalFilesDir(null) + "/" + mingzi + ".png";
        try {
            tupianWenjian = new File(baocunLujing);
            if (!tupianWenjian.exists()) {
                tupianWenjian.getParentFile().mkdirs();
                tupianWenjian.createNewFile();
            }
            FileOutputStream wenjanShuchuliu = new FileOutputStream(tupianWenjian);
            tupian.compress(Bitmap.CompressFormat.JPEG, 100, wenjanShuchuliu);
            wenjanShuchuliu.flush();
            wenjanShuchuliu.close();
        } catch (IOException e) {

            return null;
        }
        return tupianWenjian;
    }

    /**
     * 处理旋转后的图片
     *
     * @param yuanshiLujing 原图路径
     * @param chiyouzhe     上下文
     * @return 返回修复完毕后的图片路径
     */
    public static String chuliXuanhuanhouTupian(String yuanshiLujing, Context chiyouzhe) {
        // 取得图片旋转角度
        int tupianXuanZhuanJidou = huoquTupianXuanzhaunJiaodu(yuanshiLujing);
        // 把原图压缩后得到Bitmap对象
        Bitmap yasuohouTupian = huoquyasuoTu(yuanshiLujing);
        // 修复图片被旋转的角度
        Bitmap xiuFuhouTupian = xuanZhuanTupian(tupianXuanZhuanJidou, yasuohouTupian);
        // 保存修复后的图片并返回保存后的图片路径
        return baocunTupianDaoSD(xiuFuhouTupian, chiyouzhe);
    }

    /**
     * 读取照片旋转角度
     *
     * @param lujing 照片路径
     * @return 角度
     */
    public static int huoquTupianXuanzhaunJiaodu(String lujing) {
        int jiaodu = 0;
        try {
            ExifInterface daochuJiekou = new ExifInterface(lujing);
            int tupianFangxiang = daochuJiekou.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (tupianFangxiang) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    jiaodu = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    jiaodu = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    jiaodu = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String shoujiXinghao = Build.MODEL;
        if (shoujiXinghao.contains("Redmi Note 8 Pro") || shoujiXinghao.contains("M2007J17C")) {
            jiaodu = 90;
        }
        return jiaodu;
    }


    //获取压缩图片
    public static Bitmap huoquyasuoTu(String lujing) {
        BitmapFactory.Options tupianCaozuo = new BitmapFactory.Options();
        Bitmap yasuoHouTupian = BitmapFactory.decodeFile(lujing, tupianCaozuo);
        tupianCaozuo = null;
        return yasuoHouTupian;
    }


    //保存图片到sd卡
    public static String baocunTupianDaoSD(Bitmap tuxiang, Context chiouzhe) {
        FileOutputStream shuchuLiu = null;
        String wenjianMing = huoquTupianMing(chiouzhe);
        try {
            shuchuLiu = new FileOutputStream(wenjianMing);
// 把数据写入文件，100表示不压缩
            tuxiang.compress(Bitmap.CompressFormat.PNG, 100, shuchuLiu);
            return wenjianMing;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (shuchuLiu != null) {
// 记得要关闭流！
                    shuchuLiu.close();
                }
                if (tuxiang != null) {
                    tuxiang.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取图片名
    public static String huoquTupianMing(Context chiyouZhe) {
        File wenjianming = new File(getPhoneRootPath(chiyouZhe) + SAVETUPIAN_FILEPATH);
// 判断文件是否已经存在，不存在则创建
        if (!wenjianming.exists()) {
            wenjianming.mkdirs();
        }
// 设置图片文件名称
        SimpleDateFormat tupianGeshi = new SimpleDateFormat(SHIJIAN_LEIXING, Locale.getDefault());
        Date shijianchuo = new Date(System.currentTimeMillis());
        String shijianStr = tupianGeshi.format(shijianchuo);
        String tupianMingzi = "/" + shijianStr + TUPIAN_LEIXING;
        return wenjianming + tupianMingzi;
    }

    private static String getPhoneRootPath(Context chiyouZhe) {
// 是否有SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || !Environment.isExternalStorageRemovable()) {
// 获取SD卡根目录
            return chiyouZhe.getExternalCacheDir().getPath();
        } else {
// 获取apk包下的缓存路径
            return chiyouZhe.getCacheDir().getPath();
        }
    }

    public void take_Album() {


        /**
         * https://blog.csdn.net/zldd0373/article/details/107683407
         * 这种做法弊端
         */
        try {
//            Intent intent = new Intent(Intent.ACTION_PICK, null);
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//            activity.startActivityForResult(intent, SELECT_PHOTO);

            Intent pickImageIntent = new Intent();
            pickImageIntent.setAction(Intent.ACTION_PICK);
            pickImageIntent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(pickImageIntent, SELECT_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照获取图片
     **/
    public void take_photo() {
        File cunchuWenjian = null;
        try {
            cunchuWenjian = new File(activity.getExternalCacheDir(), "1001" + "output_image.jpg");
            if (cunchuWenjian.exists()) {
                cunchuWenjian.delete();
            }
            cunchuWenjian.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UrdizhiL = Uri.fromFile(cunchuWenjian);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            UrdizhiL = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileProvider", cunchuWenjian);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, UrdizhiL);
        activity.startActivityForResult(intent, TAKE_PHOTO);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
            String path = fileUtils.getPathFromUri(activity, data.getData());
            if (callback != null && !TextUtils.isEmpty(path)) {
                callback.getPath(null, path);
            }
            return;
        }

        try {
            if (AppCompatActivity.RESULT_OK == resultCode) {
                Uri[] jieguoShuzu = null;
                if (UrdizhiL == null)
                    return;
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        jieguoShuzu = new Uri[]{UrdizhiL};
                    } else {
                        String shujuChuan = data.getDataString();
                        ClipData huafenSHUJU = data.getClipData();
                        if (huafenSHUJU != null) {
                            jieguoShuzu = new Uri[huafenSHUJU.getItemCount()];
                            for (int i = 0; i < huafenSHUJU.getItemCount(); i++) {
                                ClipData.Item item = huafenSHUJU.getItemAt(i);
                                jieguoShuzu[i] = item.getUri();
                            }
                        }
                        if (shujuChuan != null)
                            jieguoShuzu = new Uri[]{Uri.parse(shujuChuan)};
                    }

                    if (requestCode == TAKE_PHOTO && jieguoShuzu != null) {
                        Bitmap bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(jieguoShuzu[0]));
                        Bitmap bitmap1 = tuPianKongjian(bitmap);
                        File path1 = baocunTupianDaoBendi(bitmap1, "photo1001", activity);
                        String beimianTu = chuliXuanhuanhouTupian(path1.getAbsolutePath(), activity);
                        Uri uri = Uri.fromFile(new File(beimianTu));

                        Log.d(TAG, "" + beimianTu);
                        if (callback != null) {
                            callback.getPath(uri, path1.getAbsolutePath());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//
//
//            }
//        }.start();
    }

}
