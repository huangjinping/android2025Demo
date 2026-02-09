package con.fire.android2023demo.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.databinding.ActivityScreenshotBinding;
import con.fire.android2023demo.utils.ImageUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ScreenshotActivity extends AppCompatActivity {
    ActivityScreenshotBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScreenshotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onScreen();
            }
        });


    }


    private void onScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = ImageUtils.shotMultiView(binding.getRoot(), new View[]{binding.layoutCenterview});
                    shotImage(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 2000);
    }


    @SuppressLint("CheckResult")
    private void shotImage(Bitmap bitmap) {
        Log.d("insertImage", "===112==");


        Observable.create(new ObservableOnSubscribe<Uri>() {
            @Override
            public void subscribe(ObservableEmitter<Uri> emitter) throws Exception {

                Uri uri = ImageSaveNoPermissionUtil.saveImageToDownloadNoPermission(ScreenshotActivity.this, bitmap);
                emitter.onNext(uri);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Uri>() {
            @Override
            public void accept(Uri file) throws Exception {

                Toast.makeText(ScreenshotActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });

//        Observable.create(new ObservableOnSubscribe<File>() {
//            @Override
//            public void subscribe(ObservableEmitter<File> emitter) throws Exception {
//                File file = ImageUtils.saveBitMapImage(ScreenshotActivity.this, bitmap);
//                Log.d("insertImage", "===112==" + file.getAbsolutePath());
//
//                emitter.onNext(file);
//
//
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<File>() {
//            @Override
//            public void accept(File file) throws Exception {
//                ImageUtils.insertImage(ScreenshotActivity.this, file);
//
//            }
//        });
    }


}
