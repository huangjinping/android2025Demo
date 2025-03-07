package con.fire.android2023demo.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public class LoadingDialogUtils {
    private LoadingPopupView popupView;

    /**
     * 显示loading的dialog
     *
     * @param msg
     */
    public void showLoadingDialog(Activity currentActivity, String msg) {
//        //如果传入为空就不做任何操作
        if(TextUtils.isEmpty(msg)){return;}
        Activity mActivity = currentActivity;
        if(mActivity == null){
            return;
        }
        popupView = new XPopup.Builder(mActivity)
                .dismissOnTouchOutside(false)
                .dismissOnBackPressed(false)
                .hasShadowBg(false)
                .asLoading(msg);
        if(mActivity != null && !mActivity.isDestroyed() && !mActivity.isFinishing()){
            if(!popupView.isShow()){
                popupView.show();
            }
        }




        try {

            View cv = currentActivity.getWindow().getDecorView().findViewById(16908290);
            cv.post(new Runnable() {
                public void run() {
                    int a = 1 / 0;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("okhttp2", "=======");
    }

    /**
     * 消失dialog
     */
    public void dismissLoadingDialog() {
        if (popupView == null) {
            return;
        } else {
            popupView.dismiss();
            popupView = null;
        }
    }

}
