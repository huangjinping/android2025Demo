package con.fire.android2023demo.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import con.fire.android2023demo.databinding.ActivityBluetoothaBinding;
import con.fire.android2023demo.receiver.BluetoothHeadsetMonitor;
import con.fire.android2023demo.receiver.BluetoothListener;

public class BluetoothActivity extends AppCompatActivity {

    ActivityBluetoothaBinding binding;
    String[] permissionArr = {Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBluetoothaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegister();
            }
        });
    }

    public void onRegister() {
        ActivityCompat.requestPermissions(this, permissionArr, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (checkPermission(this, permissions)) {
                onStartEar();
            }
        }
    }

    public boolean checkPermission(Context context, String[] perm) {
        for (String item : perm) {
            if (ActivityCompat.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED) {

                return false;
            }
        }
        return true;
    }


    public void onStartEar() {
        Toast.makeText(this, "注册耳机监听1", Toast.LENGTH_SHORT).show();
//        BluetoothHeadsetMonitor bluetoothHeadsetMonitor = new BluetoothHeadsetMonitor(this);
        BluetoothListener bluetoothListener=new BluetoothListener(this);

    }
}
