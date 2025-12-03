package con.fire.android2023demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import con.fire.android2023demo.R;

public class TTSActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TTSA22ctivity";
    private TextToSpeech tts;
    private Button btnSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);

        // 初始化控件
        btnSpeak = findViewById(R.id.btn_speak);
        btnSpeak.setOnClickListener(this);

        // 初始化 TTS 引擎
        initTTS();
    }

    /**
     * 初始化 TTS 引擎
     */
    private void initTTS() {
        // 参数1：上下文；参数2：初始化回调监听器
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                Log.d(TAG, "TTS 引擎初始化成功");
                // 设置语言（中文：Locale.CHINA）
                int result = tts.setLanguage(Locale.CHINA);
                // 检查语言是否支持
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(TTSActivity.this, "未安装中文语音包，前往设置下载", Toast.LENGTH_LONG).show();
                    // 跳转到语音包下载页面
                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                } else {
                    Toast.makeText(TTSActivity.this, "TTS 准备就绪", Toast.LENGTH_SHORT).show();
                    // 初始化播报参数（语速、音量、音调）
                    initTTSConfig();
                    // 监听播报状态
                    setTTSListener();
                }
            } else {
                String errorMsg = getTTSInitErrorMsg(status);
                Toast.makeText(TTSActivity.this, "TTS初始化失败：" + errorMsg, Toast.LENGTH_LONG).show();
                Log.e(TAG, "TTS初始化失败：" + errorMsg);
            }
        });
    }

    // 状态码对应错误信息（Android 官方定义）
    private String getTTSInitErrorMsg(int status) {
        switch (status) {
            case TextToSpeech.ERROR:
                return "通用错误（引擎初始化失败）";
            case TextToSpeech.ERROR_INVALID_REQUEST:
                return "无效请求（参数错误）";
            case TextToSpeech.ERROR_NOT_INSTALLED_YET:
                return "TTS引擎未安装";
//            case TextToSpeech.ERROR_SERVICE_DISCONNECTED:
//                return "TTS服务断开连接";
            case TextToSpeech.ERROR_NETWORK:
                return "网络错误（下载语音包时）";
            case TextToSpeech.ERROR_NETWORK_TIMEOUT:
                return "网络超时（下载语音包时）";
            default:
                return "未知错误（状态码：" + status + "）";
        }
    }

    /**
     * 配置 TTS 参数（语速、音量、音调）
     */
    private void initTTSConfig() {
        tts.setSpeechRate(1.0f); // 语速：0.1f~2.0f（默认1.0f）
//        tts.setVolume(1.0f);     // 音量：0.0f~1.0f（默认1.0f）
//        tts.setVoice(1.0f);
        tts.setPitch(1.0f);      // 音调：0.5f~2.0f（默认1.0f）
    }

    /**
     * 设置 TTS 播报监听器（开始、完成、失败）
     */
    private void setTTSListener() {
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                // 子线程回调，更新 UI 需切换到主线程
                runOnUiThread(() -> Toast.makeText(TTSActivity.this, "播报开始", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onDone(String utteranceId) {
                runOnUiThread(() -> Toast.makeText(TTSActivity.this, "播报完成", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onError(String utteranceId) {
                runOnUiThread(() -> Toast.makeText(TTSActivity.this, "播报失败", Toast.LENGTH_SHORT).show());
            }
        });
    }

    /**
     * 语音播报核心方法
     */
    private void speakText(String text) {
        if (tts == null || text.isEmpty()) return;

        // 如果正在播报，先停止
        if (tts.isSpeaking()) {
            tts.stop();
        }

        // 开始播报
        // 参数说明：
        // 1. 播报文本；2. 队列模式（QUEUE_FLUSH：清空队列立即播；QUEUE_ADD：追加到队列）；
        // 3. 额外参数（null 为默认）；4. 播报唯一标识（用于监听器回调）
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_utterance_id");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_speak) {
            // 测试播报文本
            String testText = "你好，这是 Android 原生 TTS 语音播报的 Java 实现，支持语速、音量调节。";
            speakText(testText);
        }
    }

    /**
     * 释放 TTS 资源（关键：避免内存泄漏）
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();       // 停止播报
            tts.shutdown();   // 释放引擎资源
            tts = null;
        }
    }
}