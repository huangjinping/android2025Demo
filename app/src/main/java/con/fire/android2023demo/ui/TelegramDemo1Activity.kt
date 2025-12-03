package con.fire.android2023demo.ui

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import con.fire.android2023demo.R
import java.net.URLEncoder

class TelegramDemo1Activity : AppCompatActivity() {


    private fun buildTelegramLoginUrl(): String {
        return "https://oauth.telegram.org/auth?" +
                "bot_id=8580286035" +  // 替换为你的 Bot ID
                "&origin=${URLEncoder.encode("https://yourdomain.com", "UTF-8")}" +
                "&embed=1" +
                "&request_access=write" +
                "&return_to=${URLEncoder.encode("https://yourdomain.com/callback", "UTF-8")}" +
                "&lang=en"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telegramdemo1)
        val apiId = "8580286035";
        val authUrl =
            "https://oauth.telegram.org/auth" + "?client_id=$apiId" + "&redirect_uri=https://yourdomain.com/telegram/callback" + "&response_type=code" + "&scope=read" // 权限范围
        var webView = findViewById<WebView>(R.id.webview)
// 2. 在WebView中打开授权页面
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?, request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                if (url.startsWith("https://yourdomain.com/telegram/callback")) {
                    // 解析授权码
//                    val code = Uri.parse(url).getQueryParameter("code")
//                    // 用code换取access_token（后端处理）
//                    getAccessToken(code)
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        webView.loadUrl(buildTelegramLoginUrl())
    }
}