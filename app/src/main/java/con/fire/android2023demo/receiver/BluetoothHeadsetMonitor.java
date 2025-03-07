package con.fire.android2023demo.receiver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

public class BluetoothHeadsetMonitor {
    private Context context;
    private BluetoothHeadset bluetoothHeadset;
    private BluetoothAdapter bluetoothAdapter;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                int state = intent.getIntExtra(BluetoothProfile.EXTRA_STATE, -1);
                switch (state) {
                    case BluetoothProfile.STATE_CONNECTED:
                        // 耳机已连接
                        Toast.makeText(context, "蓝牙耳机连接", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothProfile.STATE_DISCONNECTED:
                        // 耳机已断开
                        Toast.makeText(context, "蓝牙耳机断开", Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        }
    };

    public BluetoothHeadsetMonitor(Context context) {
        this.context = context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.getProfileProxy(context, new BluetoothProfile.ServiceListener() {
            @Override
            public void onServiceConnected(int profile, BluetoothProfile proxy) {
                bluetoothHeadset = (BluetoothHeadset) proxy;
            }

            @Override
            public void onServiceDisconnected(int profile) {
                bluetoothHeadset = null;
            }
        }, BluetoothProfile.HEADSET);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
        context.registerReceiver(receiver, filter);
    }

    // 注销监听器和释放资源
    public void unregister() {
        context.unregisterReceiver(receiver);
        if (bluetoothHeadset != null) {
            bluetoothAdapter.closeProfileProxy(BluetoothProfile.HEADSET, bluetoothHeadset);
        }
    }
}
