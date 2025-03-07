package con.fire.android2023demo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import con.fire.android2023demo.App;
import con.fire.android2023demo.databinding.ActivityCrashBinding;
import con.fire.android2023demo.ui.ap162.ViewPager2Activity;
import con.fire.android2023demo.utils.CommonUtil;
import con.fire.android2023demo.utils.Constants;

public class CrashActivity extends AppCompatActivity {
    public static final String TAG = "carsh02";
    ActivityCrashBinding binding;
    Drawable mResource;
    String content = "";

    /**
     * Bitmap缩放大小的方法
     */
    public static Bitmap scale(Bitmap bitmap, float w, float h) {
        Matrix matrix = new Matrix();
        matrix.postScale(w, h);  //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * drawable 转换成bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.txtCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onCreash();
                Toast.makeText(CrashActivity.this, "" + Constants.CLIENT_ID_VALUE, Toast.LENGTH_SHORT).show();
//                int a = 1 / 0;
//                Exception drawException = new RuntimeException("image bitmap Recycled $context imageID $id");


                binding.txtCrash.setText(CommonUtil.formatMoney2("10000"));
            }
        });
        binding.txtAppssid151.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.txtGoogleopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGoogleopen();
            }
        });
        binding.txt154.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "=============111111");
                onGlide154();
                finish();

//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        Log.d(TAG, "=============222222222");
//
//
//                        try {
//                            Thread.sleep(4000);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.d(TAG, "=============3333333333");
//
//                                try {
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//
//                                }
//                            }
//                        });
//
//                    }
//                }.start();
            }
        });
        binding.txtGlideclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        binding.txtViewpager2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrashActivity.this, ViewPager2Activity.class);
                startActivity(intent);
            }
        });
    }

    private void onGlide154() {
        String imageUrl = "https://pics3.baidu.com/feed/1c950a7b02087bf462556254718e6f2210dfcfb9.jpeg@f_auto?token=8fb35118639fc75a201cdc0f6e2939fb&" + System.currentTimeMillis();
//
//        imageUrl="http://111.203.220.52:93/30031719457017.jpg";
//        RequestOptions options = new RequestOptions().fitCenter().placeholder(R.mipmap.ic_launcher)  //设置占位图
//                .error(R.mipmap.ic_launcher)      //加载错误图
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE); // 磁盘缓存策略  .
//        Glide.with(this).load(imageUrl).apply(options).listener(new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                mResource = resource;
////                Bitmap bitmap = scale(drawableToBitmap(resource), 0.01f, 0.01f);
////                binding.imgError.setImageBitmap(bitmap);
//
//
//                clear();
//                addError();
//                return false;
//            }
//        }).into(binding.imgBanner);


//        Glide.with(activity).asBitmap().load(Url).into(object : CustomTarget<Bitmap>() {
//            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                //resource就是要加载的bitmap
//            }
//            override fun onLoadCleared(placeholder: Drawable?) {
//                //收到这个回调，之前的resource不能再引用了，需要置为null
//            }
//        })

        String url1 = "http://111.203.220.52:93/30031719457017.jpg?v=" + System.currentTimeMillis();
        String url2 = "http://111.203.220.52:93/30041719457039.jpg?v=" + System.currentTimeMillis();
        String url3 = "http://111.203.220.52:93/30051719457077.jpg?v=" + System.currentTimeMillis();

        imageUrl = url1;
        Glide.with(this).asBitmap().load(imageUrl).into(new CustomTarget<Bitmap>() {

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Log.d(TAG, "=============000===onResourceReady");

                binding.imgBanner.setImageBitmap(resource);


            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                Log.d(TAG, "=============111===onLoadCleared");

            }
        });

    }

    private void clear() {
        Glide.get(App.application).clearMemory();


        new Thread() {
            @Override
            public void run() {
                super.run();
                Glide.get(App.application).clearDiskCache();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "====" + content.length());

                        Toast.makeText(CrashActivity.this, "清除了glide 缓存", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }.start();
    }


    private void onGoogleopen() {
        Toast.makeText(this, "onGoogleopen", Toast.LENGTH_SHORT).show();

//        zzt.zzb("com.google.android.gms", this.getPackageName());
//        startActivity(zzt.zzb("com.google.android.gms", this.getPackageName()));
//        startActivity(zzt.zzb("com.google.android.gms", "com.govern.sad.clothing"));


        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int statusCode = googleApiAvailability.isGooglePlayServicesAvailable(this);
        Log.d("OKHTTPS", "========" + statusCode);
        if (statusCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(statusCode)) {
                Intent intent = googleApiAvailability.getErrorResolutionIntent(this, 2, null);
//                Intent intent = googleApiAvailability.getResolutionIntent(this, statusCode);
                // 使用这个意图来解决问题，例如启动这个意图来让用户更新Google Play服务
                startActivity(intent);
            }
        }
    }


    private void addError() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "================");
                binding.imgError.setImageDrawable(mResource);
            }
        }, 3000);
    }

    private void onCreash() {
        try {
            int a = 1 / 0;
        } finally {

        }
    }


}
