package con.fire.android2023demo.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import con.fire.android2023demo.databinding.ActivityKycBinding
import okhttp3.Call
import org.apache.http.util.TextUtils
import org.json.JSONObject
import vision.biometric.helper.OnUrlChangeListener
import vision.biometric.main.BiometricDialog


class KycActivity : AppCompatActivity() {
    lateinit var binding: ActivityKycBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKycBinding.inflate(layoutInflater)
        setContentView(binding.root);
        binding.btnApp.setOnClickListener {

//            openApp("7aef1f20-8747-4870-b445-a9287b625525");

            getSesstionId();
        }
    }

    fun getSesstionId() {


        //                ActivityCompat.requestPermissions(Hao123ActivityC.this, permissionArr, 100);
        OkHttpUtils.postString() //
            .url("https://kyc.biometric.kz/api/v1/flows/session/create/") //
            .content("{\"api_key\":\"8KK_PJve0aye9Lh6OY_1w0ZvnliN1vTUa3QNFytJNqhMOGU\"}").build() //
            .execute(object : StringCallback() {
                override fun onError(call: Call?, e: Exception?, id: Int) {
                    Toast.makeText(this@KycActivity, "${id}========" + e?.message, Toast.LENGTH_SHORT).show()

                    Log.d("okhttp22", "${id}========" + e?.message);

                }

                override fun onResponse(response: String?, id: Int) {

                    Toast.makeText(this@KycActivity, ""+response, Toast.LENGTH_SHORT).show()

                    var jsonObject = JSONObject(response);
                    var session_id = jsonObject.optString("session_id");
                    Log.d("okhttp22", "" + response);

                    if (!TextUtils.isEmpty(session_id)) {
                        openApp(session_id);
                    }

                }
            })


//        OkHttpUtils.post() //
//            .url("https://kyc.biometric.kz/api/v1/flows/session/create/") //
////            .param("api_key","8KK_PJve0aye9Lh6OY_1w0ZvnliN1vTUa3QNFytJNqhMOGU")
//            .addParams("api_key","BEUfVK5W6g8KPslYuxuLCkx0i14bgtfvGYX7oZfUwk32khA")
//            .build() //
//            .execute(object : StringCallback() {
//                override fun onError(call: Call?, e: Exception?, id: Int) {
//                    Log.d("okhttp22", "${id}========" + e?.message);
//
//                }
//
//                override fun onResponse(response: String?, id: Int) {
//
//                    Log.d("okhttp22", "" + response);
//
//                }
//            })

//        OkHttpUtils.get().url("https://www.baidu.com").build().execute(object : StringCallback() {
//                override fun onError(call: Call?, e: Exception?, id: Int) {
//                    Log.d("okhttp22", "${id}========" + e?.message);
//
//                }
//
//                override fun onResponse(response: String?, id: Int) {
//
//                    Log.d("okhttp22", "" + response);
//
//                }
//            })

    }

    fun openApp(sessionId: String) {


        BiometricDialog.show(
            sessionId!!, fragmentManager = supportFragmentManager, this,
            object : OnUrlChangeListener {
                override fun onResultSuccess(result: String) {
                    Log.d("okhttp22", "====onResultSuccess=====" + result);
                    binding.txtResult.text = "success" + result;
                }

                override fun onResultFailure(reason: String) {
                    Log.d("okhttp22", "====onResultFailure=====" + reason);

                    binding.txtResult.text = "error" + reason;
                }
            },
        )
    }
}