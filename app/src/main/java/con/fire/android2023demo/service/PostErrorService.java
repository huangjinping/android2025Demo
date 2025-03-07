package con.fire.android2023demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class PostErrorService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String error = intent.getStringExtra("error");
        Log.d("posterror", "---" + error);
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        new Thread() {
            @Override
            public void run() {
                super.run();

            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }
}
