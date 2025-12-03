package con.fire.android2023demo.ui;

import androidx.appcompat.app.AppCompatActivity;

public class TelegramDemoActivity extends AppCompatActivity {
//
//    private static final int TG_PASSPORT_RESULT = 352;
//    private TelegramLoginButton btn_telegram_login;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_telegramdemo);
//        btn_telegram_login = findViewById(R.id.btn_telegram_login);
//
//        btn_telegram_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TelegramPassport.AuthRequest req = new TelegramPassport.AuthRequest();
////                req.botID = Integer.parseInt("your_bot_id"); // 替换为您的 Bot ID
////                req.publicKey = "your_bot_public_key"; // 替换为 Bot 公钥
////                req.nonce = "unique_nonce_string"; // 唯一随机字符串，防止重放攻击
//
//                req.botID = 8580286035;// 替换为您的 Bot ID
//                req.publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqWpRpm/GWjJW8wXruEkW\n" +
//                        "2+CxIE+jrtP7jrg6lmTSqf18Za576qEI8s6W7lY7i5dXILEQtzTgfQGuln0qJ52I\n" +
//                        "6Pbj3oyBP7LMuLu3HKRu6Pr5lO6cdPFDYTnErmpUxRaLscKgDZG82Lw8gnDodVI4\n" +
//                        "7OzeCahdZlubdb+fEDI9jxWmikUhvoDEw1VdjQRCYR+Y/ZrX5eucjFu8kMvcE0q5\n" +
//                        "Gvv2x/r0DqteKkEYUAjGYoc6PiL0bxacQUYOIBNtaCj004ZoAAATtOm7e7kW8Uc7\n" +
//                        "kG+z6b4vqFmnjLr9Hrfm4XiqFwSBeqNmzU8YwDESGs7EsWTE7i5UJfsQzjk2PTt2\n" +
//                        "iwIDAQAB"; // 替换为 Bot 公钥
//                req.nonce = "unique_nonce_string"; // 唯一随机字符串，防止重放攻击
//
//                // 定义授权范围（scope）。以下是示例，包括护照/身份证（带自拍）、个人信息、驾照等
//                req.scope = new PassportScope(new PassportScopeElementOneOfSeveral(PassportScope.PASSPORT, PassportScope.IDENTITY_CARD).withSelfie(), new PassportScopeElementOne(PassportScope.PERSONAL_DETAILS).withNativeNames(), PassportScope.DRIVER_LICENSE, PassportScope.ADDRESS, PassportScope.ADDRESS_DOCUMENT, PassportScope.PHONE_NUMBER);
//
//                // 启动授权流程
//                TelegramPassport.request(TelegramDemoActivity.this, req, TG_PASSPORT_RESULT);
//            }
//        });
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == TG_PASSPORT_RESULT) {
//            if (resultCode == RESULT_OK) {
//                // 授权成功！从 data 中提取用户信息
//                // 示例：获取用户数据
//                String userData = data.getStringExtra("result");
//                // 发送 userData 到您的服务器验证（使用 Bot API）
//                Log.d("TelegramLogin", "Login successful: " + userData);
//                // 这里可以登录用户、保存 token 等
//            } else {
//                // 授权取消或失败
//                Log.d("TelegramLogin", "Login canceled");
//            }
//        }
//    }
}
