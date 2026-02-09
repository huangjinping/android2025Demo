package con.fire.android2023demo.ui

import android.Manifest
import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import con.fire.android2023demo.Hao123ActivityC
import con.fire.android2023demo.R
import con.fire.android2023demo.databinding.ActivityNotificationsBinding
import con.fire.android2023demo.utils.PermissionUtils.checkPermission


class NotificationsActivity : AppCompatActivity() {

    var permissionsSingle: String = Manifest.permission.POST_NOTIFICATIONS


    var permissionsLocationSingle: String = Manifest.permission.ACCESS_COARSE_LOCATION
    private var activityResultSingle: ActivityResultLauncher<String>? = null

    private var activityLocationResultSingle: ActivityResultLauncher<String>? = null


    var permissionLocationArr: Array<String?> = arrayOf<String?>(
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


    var permissionArr: Array<String?> = arrayOf<String?>(
        Manifest.permission.POST_NOTIFICATIONS
    )

    private var binding: ActivityNotificationsBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater())
        setContentView(binding?.getRoot())
        activityResultSingle =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {

//                    sendNotification(
//                        "messageTitle" + System.currentTimeMillis(),
//                        "body" + System.currentTimeMillis()
//                    );
                    sendNotificationDelay()

                } else {
                    Toast.makeText(this@NotificationsActivity, "用户不授权权限", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        activityLocationResultSingle =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {

//                    sendNotification(
//                        "messageTitle" + System.currentTimeMillis(),
//                        "body" + System.currentTimeMillis()
//                    );
                    Toast.makeText(
                        this@NotificationsActivity, "用户授权定位权限", Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        this@NotificationsActivity, "用户不授权定位权限", Toast.LENGTH_SHORT
                    ).show()
                }
            }

        binding?.button15?.setOnClickListener {
            openActionPermission()
        }
//        openActionPermission()
//        activityLocationResultSingle?.launch(permissionsLocationSingle)

        ActivityCompat.requestPermissions(
            this@NotificationsActivity, permissionLocationArr, 101
        )

        ActivityCompat.requestPermissions(
            this@NotificationsActivity, permissionArr, 100
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (101 == requestCode) {
            if (checkPermission(this@NotificationsActivity, permissionLocationArr)) {
                Toast.makeText(
                    this@NotificationsActivity, "判断有权限--定位", Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@NotificationsActivity, "判断没有权限--定位", Toast.LENGTH_SHORT
                ).show()
            }
        } else if (100 == requestCode) {
            if (checkPermission(this@NotificationsActivity, permissionArr)) {
                Toast.makeText(
                    this@NotificationsActivity, "判断有权限--通知", Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@NotificationsActivity, "判断没有权限--通知", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestPermissionCode == requestCode) {
//
//        } else if (requestPermissionCodeV2 == requestCode) {
//            Log.d("PermissionsV2", "==================000=" + requestCount)
//            if (checkPermission(this@PermissionActivity, permissionArr)) {
//                Toast.makeText(
//                    this@PermissionActivity,
//                    "判断有权限--开始读取json",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//
//            }
//        }
//    }

    fun openActionPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if (checkPermission(this@NotificationsActivity, permissionArr)) {

                Toast.makeText(this@NotificationsActivity, "有权限", Toast.LENGTH_SHORT).show()

            } else {
                activityResultSingle?.launch(permissionsSingle)

                Toast.makeText(this@NotificationsActivity, "没有权限", Toast.LENGTH_SHORT).show()
            }

        } else {
            sendNotification(
                "messageTitle" + System.currentTimeMillis(), "body" + System.currentTimeMillis()
            )

        }
    }


    fun checkPermission(context: Context, perm: Array<String>): Boolean {
        for (item in perm) {
            if (ActivityCompat.checkSelfPermission(
                    context, item
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun sendNotificationDelay(

    ) {
        Thread() {
            run {

                Log.d("okhtt22", "================0==");
                Thread.sleep(5000)
                Log.d("okhtt22", "=================1=");
                sendNotification(
                    "messageTitle" + System.currentTimeMillis(), "body" + System.currentTimeMillis()
                )
            }

        }.start();
    }


    private fun sendNotification(
        messageTitle: String?,
        messageBody: String?,

        ) {
        //val intent: Intent?
        //if (isRunBackground(this)) {
        //    intent = Intent(this, SplashActivity::class.java)
        //} else {
        //    intent = Intent()
        //}
        val intent = Intent(this, Hao123ActivityC::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)


        val channelID = "channeId" //渠道ID
        val channelName = "channeName" //渠道名称


        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder: NotificationCompat.Builder
        //android 8 开始要 创建通知渠道，否则通知栏不弹出
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder = NotificationCompat.Builder(this, channelID)
            val channel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        } else {
            notificationBuilder = NotificationCompat.Builder(this)
        }

        //设置title
        if (messageTitle != null && !messageTitle.isEmpty()) {
            notificationBuilder.setContentTitle(messageTitle)
        } else {
            notificationBuilder.setContentTitle("")
        }

        //设置body
        if (messageBody != null && !messageBody.isEmpty()) {
            notificationBuilder.setContentText(messageBody)
        } else {
            notificationBuilder.setContentText("")
        }
        //        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        var uniqueInt = (System.currentTimeMillis() and 0xff).toInt();
        var pendingIntent: PendingIntent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent =
                PendingIntent.getActivity(this, uniqueInt, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            pendingIntent = PendingIntent.getActivity(
                this, uniqueInt, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        notificationBuilder.setSmallIcon(R.drawable.sldea) //设置通知栏的小图标,必需设置，否则crash
            .setAutoCancel(true) //点击通知后，通知自动消失
//            .setLargeIcon(Icon.createWithResource(this,R.mipmap.ceshi))
            .setWhen(System.currentTimeMillis()) // 设置通知时间，此事件用于通知栏排序
            .setPriority(NotificationCompat.PRIORITY_HIGH) // 设置优先级，低优先级可能被隐藏

            //                .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent);//设置通知栏被点击时的事件

        notificationManager.notify(uniqueInt,  /* ID of notification */notificationBuilder.build())
    }

    /** 判断程序是否在后台运行  */
    fun isRunBackground(context: Context): Boolean {
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses
        for (appProcess in appProcesses) {
            if (appProcess.processName == context.packageName) {
                return if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    // 表明程序在后台运行
                    true
                } else {
                    false
                }
            }
        }
        return false
    }
}