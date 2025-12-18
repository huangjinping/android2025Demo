package con.fire.android2023demo.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.R;
import con.fire.android2023demo.utils.WebSocketClient;

public class WebSocketActivity extends AppCompatActivity {
    // 替换为你的服务器 IP 和端口
//    private static final String WEB_SOCKET_URL = "wss://bjst.ultracreditosmx.com/ws/smsRobot";
    private static final String WEB_SOCKET_URL = "ws://10.1.2.11:8888?userId=1001"; // userId=1001
//    private static final String WEB_SOCKET_URL = "ws://10.1.2.11:8888/ws/1001"; // userId=1001

    private EditText etMessage;
    private Button btnSend;
    private TextView tvLog;
    private WebSocketClient webSocketClient;
    private ScrollView src_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);

        // 初始化 UI
        etMessage = findViewById(R.id.et_message);
        btnSend = findViewById(R.id.btn_send);
        tvLog = findViewById(R.id.tv_log);
        src_list = findViewById(R.id.src_list);

        // 初始化 WebSocket 客户端（传入 Context、服务器地址、回调）
        initWebSocketClient();


        // 发送按钮点击事件
        btnSend.setOnClickListener(v -> {
            String message = etMessage.getText().toString().trim();
            if (!message.isEmpty() && webSocketClient != null) {
                webSocketClient.sendMessage(message);
                etMessage.setText("");

                addLog("我：" + message);
            } else {
                Toast.makeText(WebSocketActivity.this, "消息不能为空或未连接", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWebSocketClient() {

        webSocketClient = new WebSocketClient(this, WEB_SOCKET_URL, new WebSocketClient.OnWebSocketListener() {
            @Override
            public void onConnectSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(WebSocketActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                    addLog("✅ 连接服务器成功！");
                });
            }

            @Override
            public void onConnectFailed(String errorMsg) {
                runOnUiThread(() -> {
                    Toast.makeText(WebSocketActivity.this, "连接失败：" + errorMsg, Toast.LENGTH_SHORT).show();
                    addLog("❌ 连接失败：" + errorMsg);
                });
            }

            @Override
            public void onMessageReceived(String message) {
                if ("你好张晓三".equals(message)) {
                    if (webSocketClient != null) {
                        webSocketClient.release();
                    }
                }


                runOnUiThread(() -> addLog("📩 服务器/其他客户端：" + message));
            }

            @Override
            public void onDisconnect() {
                runOnUiThread(() -> {
                    Toast.makeText(WebSocketActivity.this, "断开连接", Toast.LENGTH_SHORT).show();
                    addLog("❌ 已断开与服务器的连接");
                });
            }

            // 新增：正在重连的回调
            @Override
            public void onReconnecting(int interval) {
                runOnUiThread(() -> addLog("🔄 正在重连... 下次重连间隔：" + interval + "秒"));
            }
        });
    }

    /**
     * 更新 UI 日志
     */
    private void addLog(String log) {
        String currentLog = tvLog.getText().toString();
        tvLog.setText(currentLog + "\n" + log);
        Log.d("okhttps", "-" + tvLog.getText().toString());
        // 滚动到最后一行
//        tvLog.scrollTo(0, tvLog.getBottom());
        int offset = src_list.getChildAt(0).getMeasuredHeight() - src_list.getHeight();
        src_list.scrollTo(0, offset);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放 WebSocket 资源（手动断开连接，停止重连）
        if (webSocketClient != null) {
            webSocketClient.release();
        }
    }
}