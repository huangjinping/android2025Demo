package con.fire.android2023demo.ui;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import con.fire.android2023demo.R;

public class TimerActivity extends AppCompatActivity {


    private Button button3;
    private int inx = 0;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startLoading();
                countdown();
            }
        });
    }


    private void countdown() {
        try {
            countDownTimer = new CountDownTimer(10 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    try {
                        int value = (int) (millisUntilFinished / 1000);
                        Log.d("okhttp", "========" + value);
                        button3.setText("00:" + String.format("%02d", value));
                        if (value <= 0) {
                            startPage();
//                            startPage01();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFinish() {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            countDownTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void startLoading() {
        inx = 10;
        new Thread() {
            @Override
            public void run() {
                super.run();

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            button3.setText("" + (inx--));
                        }
                    });
                }
            }
        }.start();
    }


    public void startPage01() {
        Intent intent = new Intent(this, SelectContractActivity.class);
        int flags = 1;
        PendingIntent.getActivity(this, 12, intent, 0);
    }

    public void startPage() {
        if (isForeground(this, this.getClass().getName())) {
            return;
        }
        Intent intent = new Intent(this, SelectContractActivity.class);
        startActivity(intent);
        finish();

    }


    /**
     * 判断某个界面是否在前台
     *
     * @param context   Activity的getAppliction()获取
     * @param className Activity名称 由类名.class.getName()获取
     */
    public boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.AppTask> list = am.getAppTasks();
            if (list != null && list.size() > 0) {
                ComponentName topActivity = list.get(0).getTaskInfo().topActivity;
                if (className.equals(topActivity.getClassName())) {

                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

}
