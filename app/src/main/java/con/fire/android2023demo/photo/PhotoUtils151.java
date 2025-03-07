package con.fire.android2023demo.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.utils.CameraUtil;
import con.fire.android2023demo.utils.CopyFileByUri;

public class PhotoUtils151 extends PhotoSo {
    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识
    private static final String TAG = "Photoils189";

    public PhotoUtils151(AppCompatActivity activity) {
        super(activity);
    }

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

        String path = "";
        String fileName = "font";
        if (data != null && data.getData() != null) {
            path = CopyFileByUri.getPath(activity, data.getData(), fileName);
        }

        // 取得图片旋转角度
        int bitmapDegree = CameraUtil.getBitmapDegree(path);
        // 压缩
        Bitmap bitmap = CameraUtil.getBitmapFromPath(path);
        if (bitmapDegree == 90) {
            // 修复图片被旋转的角度
            try {
                Bitmap bitmap1 = CameraUtil.rotateBitmap(bitmap, 90);
                // 保存修复后的图片并返回保存后的图片路径
                String newPath = CameraUtil.savePhotoToSD(bitmap1, activity);

                if (TextUtils.isEmpty(newPath)) {
                    checkImg(path, bitmap1);
                } else {
                    checkImg(newPath, bitmap1);
                }
            } catch (Exception e) {
                checkImg(path, bitmap);
            }

        } else if (bitmapDegree == 180) {

            // 修复图片被旋转的角度
            try {
                Bitmap bitmap2 = CameraUtil.rotateBitmap(bitmap, 180);
                // 保存修复后的图片并返回保存后的图片路径
                String newPath = CameraUtil.savePhotoToSD(bitmap2, activity);

                if (TextUtils.isEmpty(newPath)) {
                    checkImg(path, bitmap2);
                } else {
                    checkImg(newPath, bitmap2);
                }
            } catch (Exception e) {
                checkImg(path, bitmap);
            }

        } else if (bitmapDegree == 270) {

            // 修复图片被旋转的角度
            try {
                Bitmap bitmap3 = CameraUtil.rotateBitmap(bitmap, 270);
                // 保存修复后的图片并返回保存后的图片路径
                String newPath = CameraUtil.savePhotoToSD(bitmap3, activity);

                if (TextUtils.isEmpty(newPath)) {
                    checkImg(path, bitmap3);
                } else {
                    checkImg(newPath, bitmap3);
                }
            } catch (Exception e) {
                checkImg(path, bitmap);
            }

        } else {
            checkImg(path, bitmap);
        }
    }

    private void checkImg(String path, Bitmap bitmap) {
        callback.getPath(null, path);
    }


}
