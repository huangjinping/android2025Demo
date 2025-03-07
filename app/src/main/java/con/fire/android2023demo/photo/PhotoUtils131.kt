package con.fire.android2023demo.photo

import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.os.EnvironmentCompat
import con.fire.android2023demo.utils.u131.ImgCompressLinster
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PhotoUtils131(activity: AppCompatActivity?) : PhotoSo(activity) {

    private val isAndroidQ = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    private var mCameraImagePath: String? = null
    private var mCameraUri: Uri? = null
    private var pageInfoDot = false


    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private fun createImageUri(): Uri? {

        val status: String = Environment.getExternalStorageState()
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        return if (status == Environment.MEDIA_MOUNTED) {
            activity.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues()
            )
        } else {
            activity.contentResolver.insert(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI, ContentValues()
            )
        }
    }

    /**
     * 创建保存图片的文件
     */
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val imageName: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!storageDir?.exists()!!) {
            storageDir.mkdir()
        }
        val tempFile = File(storageDir, imageName)
        return if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            null
        } else tempFile
    }

    override fun take_photo() {
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // 判断是否有相机
        var photoFile: File? = null
        var photoUri: Uri? = null
        if (isAndroidQ) {
            // 适配android 10
            photoUri = createImageUri()
        } else {
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (photoFile != null) {
                mCameraImagePath = photoFile.getAbsolutePath()
                photoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                    FileProvider.getUriForFile(
                        activity, "${activity.packageName}.fileProvider", photoFile
                    )
                } else {
                    Uri.fromFile(photoFile)
                }
            }
        }
        mCameraUri = photoUri
        if (photoUri != null) {
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            activity.startActivityForResult(captureIntent, 110)
        }
    }

    override fun take_Album() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 110) {
            if (resultCode == RESULT_OK) {
                if (isAndroidQ) {
                    Log.d("PhotoUtils131", "=====1"+mCameraUri)
//                    ImageUtil131.openCompress(mCameraUri, object : ImgCompressLinster {
//                        override fun success(file: File) {
//                            callback.getPath(null, file.absolutePath)
//                        }
//                    })
                } else {
                    Log.d("PhotoUtils131", "=====0")

                    // 使用图片路径加载
//                    ImageUtil131.openCompress(mCameraImagePath, object : ImgCompressLinster {
//                        override fun success(file: File) {
//                            callback.getPath(null, file.absolutePath)
//                        }
//                    })
                }

                //crop_imageview.setImageToCrop(bitmap)
            } else {

            }
        }
    }

}