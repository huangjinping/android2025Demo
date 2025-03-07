package con.fire.android2023demo.utils;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileUtils {

    public  static void saveToSDCard(Activity mActivity, String filename, String content) {
        String en = Environment.getExternalStorageState();

        Log.d("saveToSDCard","1");
        //获取SDCard状态,如果SDCard插入了手机且为非写保护状态
        if (en.equals(Environment.MEDIA_MOUNTED)) {
            try {
//                File file = new File(mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Pictures");
                File file = new File(mActivity.getExternalCacheDir(), filename);
//                Log.d("filename", "==" + filename);
                OutputStream out = new FileOutputStream(file);
                out.write(content.getBytes());
                out.close();
                Log.d("saveToSDCard","2");

            } catch (Exception e) {
                Log.d("saveToSDCard","3");


                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mActivity, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }
        } else {
            Log.d("saveToSDCard","4");

            //提示用户SDCard不存在或者为写保护状态
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mActivity, "sdcard保存失败", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    //读取指定目录下的所有TXT文件的文件内容
    public static String getFileContent(Activity activity, String filename) {
        String content = "";
        File file = new File(activity.getExternalCacheDir(), filename);

        try {
            InputStream instream = new FileInputStream(file);
            if (instream != null) {
                InputStreamReader inputreader
                        = new InputStreamReader(instream, "utf-8");
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line = "";
                //分行读取
                while ((line = buffreader.readLine()) != null) {
                    content += line + "\n";
                }
                instream.close();        //关闭输入流
            }
        } catch (java.io.FileNotFoundException e) {
            Log.d("TestFile", "The File doesn't not exist.");
        } catch (Exception e) {
            Log.d("TestFile", e.getMessage());
        }
        return content;
    }
}
