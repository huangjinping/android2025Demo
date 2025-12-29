package con.fire.android2023demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.R;

public class Activity2 extends AppCompatActivity {

    private boolean isActivityShowing = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demospa);
        Button bttton = findViewById(R.id.btn_tton);
        bttton.setText("Activity2");

        bttton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                new Handler() {
                }.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (isActivityShowing) {
                            Toast.makeText(Activity2.this, "ddddd", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 5000);

//                startActivity(new Intent(Activity2.this, Activity3.class));

            }
        });
    }

    // 页面进入前台，开始展示
    @Override
    protected void onResume() {
        super.onResume();
        isActivityShowing = true;
    }

    // 页面退到后台，停止展示（如跳转其他Activity、按Home键）
    @Override
    protected void onPause() {
        super.onPause();
        isActivityShowing = false;
    }

}
