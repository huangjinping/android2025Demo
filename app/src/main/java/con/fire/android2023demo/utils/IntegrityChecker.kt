package con.fire.android2023demo.utils

import android.content.Context
import android.util.Base64
import com.google.android.gms.tasks.Task
import com.google.android.play.core.integrity.IntegrityManager
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.IntegrityServiceException
import com.google.android.play.core.integrity.IntegrityTokenRequest
import com.google.android.play.core.integrity.IntegrityTokenResponse
import com.google.android.play.core.integrity.model.IntegrityErrorCode
import kotlinx.coroutines.tasks.await
import java.security.SecureRandom

class IntegrityChecker(context: Context) {

    private val integrityManager: IntegrityManager =
        IntegrityManagerFactory.create(context)

    // 方法 1: 使用回调方式
    fun requestIntegrityToken(
        cloudProjectNumber: Long,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val task: Task<IntegrityTokenResponse> =
            integrityManager.requestIntegrityToken(
                IntegrityTokenRequest.builder()
                    .setNonce(generateNonce())
                    .setCloudProjectNumber(cloudProjectNumber)
                    .build()
            )

        task.addOnSuccessListener { response ->
            val token = response.token()
            onSuccess(token)
        }

        task.addOnFailureListener { exception ->
            onError(exception as Exception)
        }
    }

    // 方法 2: 返回 Task 对象（让调用者自己处理）
    fun requestIntegrityTokenTask(cloudProjectNumber: Long): Task<IntegrityTokenResponse> {
        return integrityManager.requestIntegrityToken(
            IntegrityTokenRequest.builder()
                .setNonce(generateNonce())
                .setCloudProjectNumber(cloudProjectNumber)
                .build()
        )
    }

    // 方法 3: 使用协程的扩展函数（需要添加依赖）
    suspend fun requestIntegrityTokenSuspend(cloudProjectNumber: Long): String? {
        return try {
            val task = integrityManager.requestIntegrityToken(
                IntegrityTokenRequest.builder()
                    .setNonce(generateNonce())
                    .setCloudProjectNumber(cloudProjectNumber)
                    .build()
            )

            // 使用协程挂起等待任务完成
            val response = task.await()
            response.token()
        } catch (e: Exception) {
            handleIntegrityError(e)
            null
        }
    }

    private fun generateNonce(): String {
        // 生成随机 nonce（至少16字节）
        val nonce = ByteArray(16)
        SecureRandom().nextBytes(nonce)
        return Base64.encodeToString(nonce, Base64.NO_WRAP)
    }

    private fun handleIntegrityError(e: Exception) {
        when (e) {
            is IntegrityServiceException -> {
                when (e.errorCode) {
                    IntegrityErrorCode.API_NOT_AVAILABLE -> {
                        // API 不可用（设备不支持）
                    }
                    IntegrityErrorCode.NETWORK_ERROR -> {
                        // 网络错误
                    }
                    IntegrityErrorCode.TOO_MANY_REQUESTS -> {
                        // 请求过多
                    }
                    IntegrityErrorCode.PLAY_STORE_NOT_FOUND -> {
                        // Play Store 未找到
                    }
                    IntegrityErrorCode.PLAY_STORE_ACCOUNT_NOT_FOUND -> {
                        // Play Store 账户未找到
                    }
                    IntegrityErrorCode.PLAY_SERVICES_NOT_FOUND -> {
                        // Play 服务未找到
                    }

                    IntegrityErrorCode.APP_NOT_INSTALLED -> {
                        // 应用未安装
                    }
                    IntegrityErrorCode.APP_UID_MISMATCH -> {
                        // 应用 UID 不匹配
                    }
                    IntegrityErrorCode.NONCE_TOO_SHORT -> {
                        // Nonce 太短
                    }
                    IntegrityErrorCode.NONCE_TOO_LONG -> {
                        // Nonce 太长
                    }
                    IntegrityErrorCode.NONCE_IS_NOT_BASE64 -> {
                        // Nonce 不是 Base64 编码
                    }
                    IntegrityErrorCode.CLOUD_PROJECT_NUMBER_IS_INVALID -> {
                        // 云项目编号无效
                    }
                    else -> {
                        // 其他错误
                    }
                }
            }
            else -> {
                // 非 IntegrityServiceException 的其他异常
            }
        }
    }
}