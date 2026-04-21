package con.fire.android2023demo.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

import con.fire.android2023demo.R;
import con.fire.android2023demo.utils.SimInfoUtil;

public class SimXProActivity extends AppCompatActivity {

    private static final int REQUEST_PHONE_STATE = 100;

    private TextView tvSim;
    private TextView tvGyro;

    // 陀螺仪相关
    private SensorManager sensorManager;
    private Sensor gyroSensor;
    private SensorEventListener gyroListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simxpro);

        tvSim = findViewById(R.id.tv_sim);
        tvGyro = findViewById(R.id.tv_gyro);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
//        Log.d("okhttps", "==============" + SimInfoUtil.getSimInfo(this));

        // 申请 SIM 权限
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
//
//            Log.d("okhttps","==============1");
//        } else {
//            Log.d("okhttps","==============2");
//
//            getSimInfo();
//        }

        // 初始化陀螺仪
        initGyro();
    }

    // ======================== SIM 卡信息 ========================
    private void getSimInfo() {
        StringBuilder sb = new StringBuilder();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        try {
            // 运营商名称
            String operator = telephonyManager.getNetworkOperatorName();
            sb.append("运营商：").append(operator).append("\n");

            // SIM 状态
            int simState = telephonyManager.getSimState();
            String stateStr;
            switch (simState) {
                case TelephonyManager.SIM_STATE_READY:
                    stateStr = "已就绪";
                    break;
                case TelephonyManager.SIM_STATE_ABSENT:
                    stateStr = "无SIM卡";
                    break;
                case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                    stateStr = "需要PIN码";
                    break;
                default:
                    stateStr = "其他状态";
            }
            sb.append("SIM状态：").append(stateStr).append("\n\n");

            // 双卡信息
            SubscriptionManager sm = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                List<SubscriptionInfo> infos = sm.getActiveSubscriptionInfoList();
                if (infos != null && !infos.isEmpty()) {
                    for (int i = 0; i < infos.size(); i++) {
                        SubscriptionInfo info = infos.get(i);
                        sb.append("第").append(i + 1).append("张SIM卡\n");
//                        sb.append("卡槽：").append(info.getSlotIndex()).append("\n");
                        sb.append("卡槽：").append(info.getSimSlotIndex()).append("\n");
                        sb.append("运营商：").append(info.getCarrierName()).append("\n");
                        sb.append("国家：").append(info.getCountryIso()).append("\n\n");
                    }
                }
            }

            tvSim.setText(sb.toString());

        } catch (Exception e) {
            tvSim.setText("获取SIM失败：" + e.getMessage());
        }
    }

    // ======================== 陀螺仪 ========================
    private void initGyro() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        gyroListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                String text = "陀螺仪（rad/s）：\n" + "X：" + String.format("%.2f", x) + "\n" + "Y：" + String.format("%.2f", y) + "\n" + "Z：" + String.format("%.2f", z);
                tvGyro.setText(text);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
    }

    // ======================== 生命周期 ========================
    @Override
    protected void onResume() {
        super.onResume();
        if (gyroSensor != null) {
            sensorManager.registerListener(gyroListener, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroListener);
    }

    // ======================== 权限回调 ========================
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_STATE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSimInfo();
                Log.d("okhttps", "==============" + SimInfoUtil.getSimInfo(this));

            } else {
                Toast.makeText(this, "未授予SIM卡权限", Toast.LENGTH_SHORT).show();
            }
        }
    }
}