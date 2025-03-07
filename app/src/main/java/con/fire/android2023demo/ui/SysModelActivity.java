package con.fire.android2023demo.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import con.fire.android2023demo.databinding.ActivitySysmodelBinding;
import con.fire.android2023demo.vm.DialogViewModel;

public class SysModelActivity extends AppCompatActivity {
    private static AlertDialog alertDialog = null;
    ActivitySysmodelBinding binding;
    DialogViewModel dialogViewModel;

    public static boolean checkNetWorkStatus(Context context, boolean openSet) {
        boolean isNetUsable = false;
        try {
            if (context != null) {
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (manager != null) {
                    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                    if (networkInfo != null) {
                        boolean connected = networkInfo.isConnected();
                        if (connected) {
                            isNetUsable = networkInfo.getState() == NetworkInfo.State.CONNECTED;
                        }
                    }
                }
                if (!isNetUsable && openSet) {
                    showOpenNetDialog(context);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isNetUsable;
    }

    private static void showOpenNetDialog(Context context) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }
            ;
            alertDialog = new AlertDialog.Builder(context).setMessage("open_network_set").setCancelable(false).setPositiveButton("ok", (dialog, which) -> {
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                context.startActivity(intent);
            }).setNegativeButton("cancel", (dialog, which) -> dialog.dismiss()).create();
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySysmodelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialogViewModel = new ViewModelProvider(this).get(DialogViewModel.class);

        dialogViewModel.dataLog.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textView5.setText("" + s);
            }
        });
        binding.button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialogViewModel.setPostText();
                checkNetWorkStatus(SysModelActivity.this, true);
            }
        });
    }
}
