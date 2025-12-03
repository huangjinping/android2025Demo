package con.fire.android2023demo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * 带自动重连功能的 Android WebSocket 客户端工具类
 */
public class WebSocketClient {
    private static final String TAG = "WebSocketClient";
    private static final int MAX_RECONNECT_INTERVAL = 30 * 1000; // 最大重连间隔（30秒）
    private final Context mContext; // 上下文，用于监听网络状态
    private final String mServerUrl; // 服务器地址
    private final OkHttpClient mOkHttpClient;
    private WebSocket mWebSocket;
    private OnWebSocketListener mListener;
    // 重连相关参数
    private Timer mReconnectTimer; // 重连定时器
    private int mReconnectInterval = 1000; // 初始重连间隔（1秒）
    private boolean isManualDisconnect = false; // 是否手动断开连接（用于控制是否重连）
    private boolean isReconnecting = false; // 是否正在重连中（避免重复重连）
    /**
     * 网络状态广播接收器：网络恢复时立即重连
     */
    private final BroadcastReceiver mNetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                // 检查网络是否可用
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isNetworkAvailable = activeNetwork != null && activeNetwork.isConnected();

                if (isNetworkAvailable) {
                    Log.d(TAG, "网络恢复，触发立即重连");
                    // 网络恢复时，重置重连间隔为1秒，立即重连
                    mReconnectInterval = 1000;
                    startReconnectTimer();
                }
            }
        }
    };

    // 构造方法：需传入上下文（用于网络监听）、服务器地址、回调接口
    public WebSocketClient(Context context, String serverUrl, OnWebSocketListener listener) {
        this.mContext = context;
        this.mServerUrl = serverUrl;
        this.mListener = listener;
        this.mOkHttpClient = new OkHttpClient();

        // 注册网络状态广播接收器（监听网络恢复）
        registerNetworkReceiver();
        // 初始化第一次连接
        initWebSocket();
    }

    /**
     * 初始化 WebSocket 连接
     */
    private void initWebSocket() {
        if (isManualDisconnect) {
            Log.d(TAG, "手动断开连接后，不初始化连接");
            return;
        }

        Request request = new Request.Builder().url(mServerUrl).build();

        mWebSocket = mOkHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                Log.d(TAG, "WebSocket 连接成功");
                // 连接成功：停止重连定时器，重置重连间隔
                cancelReconnectTimer();
                mReconnectInterval = 1000;
                isReconnecting = false;

                if (mListener != null) {
                    mListener.onConnectSuccess();
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                Log.d(TAG, "收到服务器消息：" + text);
                if (mListener != null) {
                    mListener.onMessageReceived(text);
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                String binaryMsg = bytes.utf8();
                Log.d(TAG, "收到二进制消息：" + binaryMsg);
                if (mListener != null) {
                    mListener.onMessageReceived(binaryMsg);
                }
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                Log.d(TAG, "WebSocket 正在关闭：" + reason);
                webSocket.close(code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                Log.d(TAG, "WebSocket 已关闭：" + reason);
                if (mListener != null) {
                    mListener.onDisconnect();
                }
                // 非手动断开连接，触发自动重连
                if (!isManualDisconnect) {
                    startReconnectTimer();
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                String errorMsg = t.getMessage() != null ? t.getMessage() : "连接失败";
                Log.e(TAG, "WebSocket 连接失败：" + errorMsg);
                if (mListener != null) {
                    mListener.onConnectFailed(errorMsg);
                }
                // 非手动断开连接，触发自动重连
                if (!isManualDisconnect) {
                    startReconnectTimer();
                }
            }
        });
    }

    /**
     * 启动重连定时器（指数退避策略）
     */
    private void startReconnectTimer() {
        if (isManualDisconnect || isReconnecting) {
            return;
        }

        isReconnecting = true;
        // 回调正在重连的状态（间隔时间转为秒，方便 UI 显示）
        if (mListener != null) {
            mListener.onReconnecting(mReconnectInterval / 1000);
        }

        // 取消之前的定时器（避免重复）
        cancelReconnectTimer();

        mReconnectTimer = new Timer();
        mReconnectTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "开始重连，当前间隔：" + mReconnectInterval + "ms");
                // 子线程中重新初始化连接
                initWebSocket();
                // 更新下次重连间隔（翻倍，不超过最大值）
                mReconnectInterval = Math.min(mReconnectInterval * 2, MAX_RECONNECT_INTERVAL);
            }
        }, mReconnectInterval);
    }

    /**
     * 取消重连定时器
     */
    private void cancelReconnectTimer() {
        if (mReconnectTimer != null) {
            mReconnectTimer.cancel();
            mReconnectTimer = null;
        }
        isReconnecting = false;
    }

    /**
     * 注册网络状态广播接收器：监听网络恢复
     */
    private void registerNetworkReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mContext.registerReceiver(mNetworkReceiver, filter);
    }

    /**
     * 发送文本消息给服务器
     */
    public void sendMessage(String message) {
        if (mWebSocket != null && mWebSocket.send(message)) {
            Log.d(TAG, "发送消息成功：" + message);
        } else {
            Log.e(TAG, "发送消息失败（连接未建立或已断开）：" + message);
        }
    }

    /**
     * 手动断开 WebSocket 连接（停止重连）
     */
    public void disconnect() {
        isManualDisconnect = true;
        cancelReconnectTimer();
        if (mWebSocket != null) {
            mWebSocket.close(1000, "主动断开连接");
            mWebSocket = null;
        }
        // 注销广播接收器（避免内存泄漏）
        try {
            mContext.unregisterReceiver(mNetworkReceiver);
        } catch (Exception e) {
            Log.e(TAG, "注销网络广播接收器失败：" + e.getMessage());
        }
    }

    /**
     * 释放资源（在 Activity/Fragment 销毁时调用）
     */
    public void release() {
        disconnect();
        mListener = null;
    }

    // 回调接口（新增网络状态回调）
    public interface OnWebSocketListener {
        void onConnectSuccess(); // 连接成功

        void onConnectFailed(String errorMsg); // 连接失败

        void onMessageReceived(String message); // 收到文本消息

        void onDisconnect(); // 断开连接

        void onReconnecting(int interval); // 正在重连（回调当前重连间隔）
    }
}