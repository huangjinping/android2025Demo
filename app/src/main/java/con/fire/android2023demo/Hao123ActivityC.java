package con.fire.android2023demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.facebook.appevents.AppEventsLogger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import con.fire.android2023demo.base.BaseActivity;
import con.fire.android2023demo.databinding.ActivityHao123Binding;
import con.fire.android2023demo.ui.AppCurrentActivity;
import con.fire.android2023demo.ui.AudioManagerActivity;
import con.fire.android2023demo.ui.BaseWeb154Activity;
import con.fire.android2023demo.ui.BluetoothActivity;
import con.fire.android2023demo.ui.CrashActivity;
import con.fire.android2023demo.ui.DialogTestActivity;
import con.fire.android2023demo.ui.EditActivity;
import con.fire.android2023demo.ui.FaceBookTestActivity;
import con.fire.android2023demo.ui.GoogleOpActivity;
import con.fire.android2023demo.ui.GooleLogin2Activity;
import con.fire.android2023demo.ui.InxAgentwebActivity;
import con.fire.android2023demo.ui.LaoLinActivity;
import con.fire.android2023demo.ui.MainActivity;
import con.fire.android2023demo.ui.PackageUsageStatsActivity;
import con.fire.android2023demo.ui.PermissionActivity;
import con.fire.android2023demo.ui.PermissionNewActivity;
import con.fire.android2023demo.ui.QueriesActivity;
import con.fire.android2023demo.ui.ReferrerActivity;
import con.fire.android2023demo.ui.RemoteConfigActivity;
import con.fire.android2023demo.ui.SMSActivity;
import con.fire.android2023demo.ui.ScreenRecordActivity;
import con.fire.android2023demo.ui.SelectContractActivity;
import con.fire.android2023demo.ui.SnippetsActivity;
import con.fire.android2023demo.ui.SysModelActivity;
import con.fire.android2023demo.ui.TimerActivity;
import con.fire.android2023demo.ui.UIFragmentActivity;
import con.fire.android2023demo.ui.UploadWebActivity;
import con.fire.android2023demo.ui.ViewActivity;
import con.fire.android2023demo.ui.WebViewActivity;
import con.fire.android2023demo.ui.login.EmailLoginActivity;
import con.fire.android2023demo.ui.login.PhoneLoginActivity;
import con.fire.android2023demo.ui.login.SystemLoginActivity;
import con.fire.android2023demo.utils.Constants;
import con.fire.android2023demo.utils.ToastUtils;
import im.crisp.client.ChatActivity;
import im.crisp.client.Crisp;

//Android 12之启动画面Splash Screens（一） -- 适配
//https://openatomworkshop.csdn.net/664ff735b12a9d168eb73c7a.html
public class Hao123ActivityC extends BaseActivity {
    ActivityHao123Binding binding;
//    App appContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        appContext = App.getAppContext();
        super.onCreate(savedInstanceState);
//        appContext.showToastApp();
        app.showToastApp();
//        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        String filename = UUID.randomUUID().toString();

        Log.d("==filename===", "" + filename);

        binding = ActivityHao123Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onClickListener();
        String uuid = UUID.randomUUID().toString();
//        MainKt.start();

//        com.facebook.appevents.AppEventsConstants.
//                EVENT_NAME_SUBSCRIBE

        Log.d("uuid", uuid);
        Log.d("uuid", "cbc060ee4a6141729083d75c13bcf325".length() + "");


        Log.d("233Act", "" + io.branch.referral.util.BRANCH_STANDARD_EVENT.COMPLETE_REGISTRATION);
        Map<String, Object> eventValues = new HashMap<String, Object>();
        eventValues.put(AFInAppEventParameterName.CONTENT_ID, "1234567");

        AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.LOGIN, eventValues, new AppsFlyerRequestListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int i, @NonNull String s) {

            }
        });


    }

    private void onClickListener() {
        binding.txtToast01.setOnClickListener(v -> {
            String msg = "以下服务端接口可免 access_token 调用的场景：使用微信云托管通过微信令牌/开放接口服务调用；使用微信云开发通过云函数免服务器发起云调用以下服务端接口可免 access_token 调用的场景：使用微信云托管通过微信令牌/开放接口服务调用；使用微信云开发通过云函数免服务器发起云调用。 ";
//            ToastUtils.showToast(Hao123Activity.this, msg);
            ToastUtils.makeTextOrder(Hao123ActivityC.this).show();
//            ToastUtils.makeText(Hao123Activity.this, "" + msg, ToastUtils.LENGTH_LONG).show();
//            Toast.makeText(Hao123Activity.this, msg, Toast.LENGTH_SHORT).show();
        });

        binding.txtImage.setText("照片" + Build.VERSION.SDK_INT);
        binding.txtImage.setOnClickListener(v -> startActivity(new Intent(Hao123ActivityC.this, MainActivity.class)));

        binding.txtWeb.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123ActivityC.this, WebViewActivity.class);
            startActivity(intent);
        });
        binding.txtPermission.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123ActivityC.this, PermissionActivity.class);
            startActivity(intent);
        });

        binding.txtSelectContract.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123ActivityC.this, SelectContractActivity.class);
            startActivity(intent);
        });

        binding.txtQueries.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123ActivityC.this, QueriesActivity.class);
            startActivity(intent);
        });
        binding.txtTimer.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123ActivityC.this, TimerActivity.class);
            startActivity(intent);
        });
        binding.txtPop.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123ActivityC.this, ViewActivity.class);
            startActivity(intent);
        });
        binding.txtUiFragment.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123ActivityC.this, UIFragmentActivity.class);
            startActivity(intent);
        });
        binding.txtPermissionNew.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123ActivityC.this, PermissionNewActivity.class);
            startActivity(intent);
        });

        binding.txtScreenRecord.setOnClickListener(v -> {
            Intent intent = new Intent(Hao123ActivityC.this, ScreenRecordActivity.class);
            startActivity(intent);
        });

        binding.txtEditText.setOnClickListener(view -> {
            Intent intent = new Intent(Hao123ActivityC.this, EditActivity.class);
            startActivity(intent);

        });

        binding.txtReferrer.setOnClickListener(view -> {
            Intent intent = new Intent(Hao123ActivityC.this, ReferrerActivity.class);
            startActivity(intent);
        });

        binding.txtCrisp.setOnClickListener(view -> {
            Crisp.configure(Hao123ActivityC.this, "6d124072-1b0f-461a-bec3-6e3ad6fa0287");
            Crisp.setTokenID("182000000");
            Crisp.setUserPhone("182000000");
            Crisp.setSessionSegment(getString(R.string.app_name));
            Intent crispIntent = new Intent(Hao123ActivityC.this, ChatActivity.class);
            startActivity(crispIntent);

//                openOutUrl("https://play.google.com/store/apps/details?id=com.ss.android.ugc.trill&hl=en-TW");
        });
        binding.txtWebAgent.setOnClickListener(view -> {
            Intent crispIntent = new Intent(Hao123ActivityC.this, InxAgentwebActivity.class);
            startActivity(crispIntent);

        });
        binding.txtGoogleLogin.setOnClickListener(view -> {
//                PeopleApi api = new PeopleApi(Hao123Activity.this);
//                api.startLocal();
            Intent intent = new Intent(Hao123ActivityC.this, GooleLogin2Activity.class);
//            Intent intent = new Intent(Hao123Activity.this, GoogleOpActivity.class);
            startActivity(intent);

        });
        binding.txtPeopleApi.setOnClickListener(view -> {
//                PeopleApi api = new PeopleApi(Hao123Activity.this);
//                api.startLocal();
//                Intent intent = new Intent(Hao123Activity.this, GooleLogin2Activity.class);
            Intent intent = new Intent(Hao123ActivityC.this, GoogleOpActivity.class);
            startActivity(intent);

        });

        binding.txtWeb154.setOnClickListener(view -> {
            Intent intent = new Intent(Hao123ActivityC.this, BaseWeb154Activity.class);
            startActivity(intent);
        });
        binding.txtTestdialog.setOnClickListener(view -> {
            Intent intent = new Intent(Hao123ActivityC.this, DialogTestActivity.class);
            startActivity(intent);
        });

        binding.txtCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.sSsid(Hao123ActivityC.this, "100");

                Intent intent = new Intent(Hao123ActivityC.this, CrashActivity.class);
                startActivity(intent);
            }
        });

        binding.txtWebupload.setOnClickListener(view -> {
            Intent intent = new Intent(Hao123ActivityC.this, UploadWebActivity.class);
            startActivity(intent);

//            openOutUrl("https://play.google.com/store/apps/details?id=com.kash.credito.aztaca.paypal.dinero.banrural&gl=co&hl=es");

//            openPlay("https://cl214.onelink.me/psuA/cy17htgd");

        });

        binding.txtPackageUsageStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hao123ActivityC.this, PackageUsageStatsActivity.class);
                startActivity(intent);
            }
        });

        binding.txtFacebooklog.setOnClickListener(view -> {
            AppEventsLogger logger = AppEventsLogger.newLogger(this);
            String strDateFormat = "yyyyMMddHHmmss";//设置日期格式
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(strDateFormat);
            Calendar instance = Calendar.getInstance();
            String key = "Log" + simpleDateFormat2.format(instance.getTime());
            Log.d("facekey", key);
            logger.logEvent(key);

        });
        binding.txtBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BluetoothHeadsetMonitor monitor = new BluetoothHeadsetMonitor(Hao123ActivityC.this);

                Intent intent = new Intent(Hao123ActivityC.this, BluetoothActivity.class);
                startActivity(intent);
//                BluetoothListener bluetoothListener = new BluetoothListener(Hao123ActivityC.this);

            }
        });
        binding.txtFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hao123ActivityC.this, FaceBookTestActivity.class);
                startActivity(intent);
            }
        });

        binding.txtSnippets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hao123ActivityC.this, SnippetsActivity.class);
                startActivity(intent);
            }
        });
        binding.txtAudioManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hao123ActivityC.this, AudioManagerActivity.class);
                startActivity(intent);

            }
        });

        binding.txtGetLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hao123ActivityC.this, AppCurrentActivity.class);
                startActivity(intent);
            }
        });

        binding.txtLaolin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hao123ActivityC.this, LaoLinActivity.class);
                startActivity(intent);
            }
        });
        binding.txtSysModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hao123ActivityC.this, SysModelActivity.class);
                startActivity(intent);
            }
        });
        binding.txtSystemlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int a = 1 / 0;
                Intent intent = new Intent(Hao123ActivityC.this, SystemLoginActivity.class);
                startActivity(intent);
            }
        });

        binding.txtAuthlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hao123ActivityC.this, PhoneLoginActivity.class);
                startActivity(intent);
            }
        });
        binding.txtAuthemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hao123ActivityC.this, EmailLoginActivity.class);
                startActivity(intent);
            }
        });

        binding.txtAppList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("okhttp", "=============" + FileUtils.getAppList(Hao123ActivityC.this));
            }
        });
        this.binding.txtRemoteConfig.setOnClickListener(new View.OnClickListener() { // from class: con.fire.android2023demo.Hao123ActivityC.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(Hao123ActivityC.this, RemoteConfigActivity.class);
                Hao123ActivityC.this.startActivity(intent);
            }
        });
        this.binding.txtSmsbuild.setOnClickListener(new View.OnClickListener() { // from class: con.fire.android2023demo.Hao123ActivityC.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(Hao123ActivityC.this, SMSActivity.class);
                Hao123ActivityC.this.startActivity(intent);
            }
        });
    }


    private void openPlay(String url) {

        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } catch (Throwable throwable) {
        }
    }

    public void openOutUrl(String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void gotoGooglePlay() {
//        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.setPackage("com.android.vending");
//        // 判断是否安装了谷歌商店
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        } else {
////            Utils.toast("Please download and install Google Play first");
//        }
    }
}
