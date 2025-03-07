package con.fire.android2023demo.receiver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

public class BluetoothListener {

    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    Log.d("okhttp", "蓝牙耳机已连接");

                    Toast.makeText(context, "蓝牙耳机已连接", Toast.LENGTH_SHORT).show();

                    // 蓝牙耳机已连接
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // 可以通过device获取蓝牙设备信息
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    Log.d("okhttp", "蓝牙耳机已断开连接");

                    // 蓝牙耳机已断开连接
                    Toast.makeText(context, "蓝牙耳机已断开连接", Toast.LENGTH_SHORT).show();

                    break;
                case BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED:
                    // 连接状态改变
                    int state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, -1);
                    switch (state) {
                        case BluetoothHeadset.STATE_CONNECTED:
                            Log.d("okhttp", "已连接");

                            // 已连接
                            Toast.makeText(context, "已连接", Toast.LENGTH_SHORT).show();

                            break;
                        case BluetoothHeadset.STATE_DISCONNECTED:
                            Log.d("okhttp", "已断开连接");

                            // 已断开连接
                            Toast.makeText(context, "已断开连接", Toast.LENGTH_SHORT).show();

                            break;
                    }
                    break;
            }
        }
    };
    private Context context;
    private AudioManager audioManager;
    private BluetoothHeadset bluetoothHeadset;
    private BluetoothAdapter bluetoothAdapter;

    public BluetoothListener(Context context) {
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Toast.makeText(context, "注册蓝牙监听器监听器", Toast.LENGTH_SHORT).show();

        // 注册蓝牙连接状态变化的广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
        context.registerReceiver(bluetoothReceiver, intentFilter);
    }

    // 注销蓝牙广播接收器
    public void unregisterReceiver() {
        context.unregisterReceiver(bluetoothReceiver);
    }
}
