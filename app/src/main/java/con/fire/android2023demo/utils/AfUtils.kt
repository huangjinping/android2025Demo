package con.fire.android2023demo.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.resume

class AfUtils {


    suspend fun getInstallReferrer(context: Context): String = suspendCancellableCoroutine { cont ->
        val client = InstallReferrerClient.newBuilder(context).build()
        client.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        CoroutineScope(Dispatchers.Main.immediate).launch {
                            try {
                                val details = withContext(Dispatchers.IO) {
                                    client.installReferrer
                                }
                                val market = buildString {
                                    append(details.installReferrer)
                                    append("&installBeginTimestampSeconds=")
                                    append(details.installBeginTimestampSeconds)
                                    append("&referrerClickTimestampServerSeconds=")
                                    append(details.referrerClickTimestampServerSeconds)
                                }
                                cont.takeUnless { it.isCompleted }?.resume(market)
                            } catch (e: Exception) {
                                cont.takeUnless { it.isCompleted }?.resume("-error-")
                            } finally {
                                client.endConnection()
                            }
                        }
                    }

                    else -> {
                        cont.takeUnless { it.isCompleted }?.resume(responseCode.toString())
                        client.endConnection()
                    }
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                cont.takeUnless { it.isCompleted }?.resume("-error-")
            }
        })
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun getInstallReferrerForJava(context: Context): CompletableFuture<String> {
        val future = CompletableFuture<String>()
        // 启动协程执行挂起函数
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = getInstallReferrer(context)
                future.complete(result) // 成功完成
            } catch (e: Exception) {
                future.completeExceptionally(e) // 异常完成
            }
        }
        return future
    }
}