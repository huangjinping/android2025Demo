package con.fire.android2023demo.utils

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.accessibility.AccessibilityManager

object AccessibilityPermissionUtil {

    /**
     * 检查无障碍权限是否已开启
     * @param context 上下文
     * @param serviceName 无障碍服务的完整类名（包名+类名）
     * @return true-已开启 false-未开启
     */
    fun isAccessibilityPermissionEnabled(context: Context, serviceName: String): Boolean {
        val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        // 获取所有已开启的无障碍服务
        val enabledServices =
            am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
        // 遍历检查当前应用的服务是否在列表中
        for (service in enabledServices) {
            val resolveInfo = service.resolveInfo
            if (resolveInfo != null && resolveInfo.serviceInfo != null) {
                val currentServiceName = resolveInfo.serviceInfo.name
                if (serviceName == currentServiceName) {
                    return true
                }
            }
        }
        return false
    }



    /**
     * 跳转到系统无障碍设置页面
     * @param context 上下文
     */
    fun goToAccessibilitySetting(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } catch (e: Exception) {
            // 兼容部分特殊机型的设置页面路径
            val intent = Intent(Settings.ACTION_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}