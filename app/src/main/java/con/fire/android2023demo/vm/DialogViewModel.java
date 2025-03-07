package con.fire.android2023demo.vm;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DialogViewModel extends ViewModel {

    public MutableLiveData<String> dataLog = new MutableLiveData<String>();

    public MutableLiveData<String> dataLog2 = new MutableLiveData<String>();

    public void setPostText() {

//        dataLog.setValue("" + System.currentTimeMillis());
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
////                dataLog.setValue("" + System.currentTimeMillis());
//            }
//        }, 2000);

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                dataLog.postValue("" + System.currentTimeMillis());

            }
        }.start();
    }
}
