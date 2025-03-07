package con.fire.android2023demo.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class ImageUtilsSp {

    public static void compressImageFromImageUri(Context context, Uri imageUri, WCommonCallback<String> callback) {
        try {
            String srcPath = context.getExternalCacheDir().getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            OutputStream outputStream = new FileOutputStream(srcPath);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
//            Luban.with(context).load(srcPath).ignoreBy(500).setTargetDir(context.getExternalCacheDir().getAbsolutePath()).filter(new CompressionPredicate() {
//                @Override
//                public boolean apply(String path) {
//                    return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
//                }
//            }).setCompressListener(new OnCompressListener() {
//                @Override
//                public void onStart() {
//
//                }
//
//                @Override
//                public void onSuccess(File file) {
//                    callback.callback(file.getPath());
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    callback.callback("");
//                }
//            }).launch();
        } catch (IOException e) {
            e.printStackTrace();
            callback.callback("");
        }
    }
}
