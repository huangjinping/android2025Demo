package con.fire.android2023demo.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import java.util.List;

public class SimInfoUtil {

    public static String getSimInfo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

        StringBuilder sb = new StringBuilder();

        // 1. 单卡基础信息（最常用）
        sb.append("SIM 状态: ").append(getSimStateText(telephonyManager.getSimState())).append("\n");
        sb.append("运营商名称: ").append(telephonyManager.getSimOperatorName()).append("\n");
        sb.append("运营商代码: ").append(telephonyManager.getSimOperator()).append("\n");
        sb.append("国家码: ").append(telephonyManager.getSimCountryIso()).append("\n");

        // 2. 双卡/多卡支持（Android 5.1+）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            List<SubscriptionInfo> subInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            if (subInfoList != null && !subInfoList.isEmpty()) {
                sb.append("\n=== 双卡信息 ===\n");
                for (SubscriptionInfo subInfo : subInfoList) {
                    sb.append("SIM 槽位: ").append(subInfo.getSimSlotIndex())
                            .append(" | 显示名称: ").append(subInfo.getDisplayName())
                            .append(" | 运营商: ").append(subInfo.getCarrierName())
                            .append("\n");
                }
            }
        }

        // 3. 尝试获取 ICCID（Android 10+ 可能因权限限制返回 null）
        try {
            if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
                String iccid = telephonyManager.getSimSerialNumber();
                sb.append("SIM 序列号 (ICCID): ").append(iccid != null ? iccid : "无法获取（权限/版本限制）").append("\n");
            }
        } catch (SecurityException e) {
            sb.append("SIM 序列号: 需要特殊权限\n");
        }

        return sb.toString();
    }

    private static String getSimStateText(int state) {
        switch (state) {
            case TelephonyManager.SIM_STATE_READY:
                return "已就绪";
            case TelephonyManager.SIM_STATE_ABSENT:
                return "无 SIM 卡";
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                return "需要 PIN";
            default:
                return "其他状态 (" + state + ")";
        }
    }
}