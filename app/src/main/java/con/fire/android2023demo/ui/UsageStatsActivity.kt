package con.fire.android2023demo.ui

import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import con.fire.android2023demo.R
import java.util.concurrent.TimeUnit

class UsageStatsActivity : AppCompatActivity() {

    private val TAG = "UsageStatsDemo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usage_stats)

        // 检查是否有权限
        if (!hasUsageAccessPermission()) {
            // 请求权限
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        } else {
            // 开始获取使用数据
            startUsageStatsMonitoring()
        }
    }

    // 检查是否有Usage Access权限
    private fun hasUsageAccessPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName
            )
        } else {
            @Suppress("DEPRECATION") appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName
            )
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }

    // 开始监控使用数据
    private fun startUsageStatsMonitoring() {
        // 使用WorkManager定期获取数据
        val usageStatsWork =
            PeriodicWorkRequestBuilder<UsageStatsWorker>(15, TimeUnit.NANOSECONDS).build()

        WorkManager.getInstance(this).enqueue(usageStatsWork)
    }

    // Worker类用于获取使用统计数据
    class UsageStatsWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

        private val TAG = "UsageStatsWorker"

        override fun doWork(): Result {
            try {
                val usageStatsManager =
                    applicationContext.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

                // 获取过去24小时的使用数据
                val endTime = System.currentTimeMillis()
                val startTime = endTime - 24 * 60 * 60 * 1000

                // 获取应用使用统计
                val usageStatsList = usageStatsManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_DAILY, startTime, endTime
                )

                // 遍历并打印数据
                usageStatsList.forEach { usageStats ->
                    Log.d(TAG, "=====================================")
                    Log.d(TAG, "应用包名: ${usageStats.packageName}")
                    Log.d(TAG, "总使用时间(毫秒): ${usageStats.totalTimeInForeground}")
                    Log.d(TAG, "最后使用时间: ${usageStats.lastTimeUsed}")
//                    Log.d(TAG, "前台使用次数: ${usageStats.launchCount}")
                    Log.d(TAG, "首次使用时间: ${usageStats.firstTimeStamp}")

                    // Android 10+ 新增的更详细数据
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        Log.d(TAG, "前台活动时间: ${usageStats.foregroundActivityTime}")
                        Log.d(TAG, "屏幕开启时间: ${usageStats.totalTimeVisible}")
                    }
                }

                // 获取最近使用的应用
//                val recentTasks = usageStatsManager.queryRecentTasks(10, UsageStatsManager.INCLUDE_EXPIRED)
//                Log.d(TAG, "\n最近使用的应用列表:")
//                recentTasks.forEachIndexed { index, task ->
//                    Log.d(TAG, "${index+1}. ${task.baseIntent?.component?.packageName}")
//                }

                return Result.success()
            } catch (e: Exception) {
                Log.e(TAG, "获取使用数据失败: ${e.message}", e)
                return Result.failure()
            }
        }
    }

    // 辅助方法：转换手机类型为字符串
    private fun getPhoneTypeString(phoneType: Int): String {
        return when (phoneType) {
            TelephonyManager.PHONE_TYPE_NONE -> "NONE"
            TelephonyManager.PHONE_TYPE_GSM -> "GSM"
            TelephonyManager.PHONE_TYPE_CDMA -> "CDMA"
            TelephonyManager.PHONE_TYPE_SIP -> "SIP"
            else -> "UNKNOWN"
        }
    }

    // 辅助方法：转换网络类型为字符串
    private fun getNetworkTypeString(networkType: Int): String {
        return when (networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS -> "GPRS"
            TelephonyManager.NETWORK_TYPE_EDGE -> "EDGE"
            TelephonyManager.NETWORK_TYPE_UMTS -> "UMTS"
            TelephonyManager.NETWORK_TYPE_HSDPA -> "HSDPA"
            TelephonyManager.NETWORK_TYPE_LTE -> "LTE"
            TelephonyManager.NETWORK_TYPE_NR -> "5G"
            else -> "OTHER"
        }
    }



}