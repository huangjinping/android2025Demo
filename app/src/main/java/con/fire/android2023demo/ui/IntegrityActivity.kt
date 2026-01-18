package con.fire.android2023demo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import con.fire.android2023demo.R
import con.fire.android2023demo.utils.IntegrityChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class IntegrityActivity : AppCompatActivity() {

    private lateinit var integrityChecker: IntegrityChecker
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        integrityChecker = IntegrityChecker(this)

//        checkIntegrityButton.setOnClickListener {
//            checkAppIntegrity()
//        }
        checkAppIntegrity()

    }

    private fun checkAppIntegrity() {

        coroutineScope.launch {
            integrityChecker.requestIntegrityToken(1L, {

            }, {

            })

//            val integrityToken = integrityChecker.requestIntegrityToken()
//            integrityToken?.let { token ->
//                // 将 token 发送到服务器进行验证
//                verifyWithServer(token)
//            }
        }
    }

    private fun verifyWithServer(integrityToken: String) {
        // 发送到你的后端服务器
        // 服务器会调用 Google Play Integrity API 验证此 token
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}