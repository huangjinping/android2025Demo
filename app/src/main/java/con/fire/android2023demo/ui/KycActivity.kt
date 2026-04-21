package con.fire.android2023demo.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.util.Log
import android.view.WindowManager
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import con.fire.android2023demo.bean.FairThoughtAncestor
import con.fire.android2023demo.databinding.ActivityKycBinding
import okhttp3.Call
import vision.biometric.helper.OnUrlChangeListener
import vision.biometric.main.BiometricDialog
import java.util.Calendar


class KycActivity : AppCompatActivity() {
    lateinit var binding: ActivityKycBinding

    var permissions: Array<String?> = arrayOf<String?>(
        Manifest.permission.READ_CALL_LOG
    )

    val pickContact = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val resultUri = it.data?.data ?: return@registerForActivityResult

            // Process the result URI in a background thread to fetch all selected contacts
//            coroutine.launch {
//                contacts = processContactPickerResultUri(resultUri, context)
//            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKycBinding.inflate(layoutInflater)
        setContentView(binding.root);
        binding.btnApp.setOnClickListener {

//            openApp("7aef1f20-8747-4870-b445-a9287b625525");

            getSesstionId();

//            onGetCallList()
        }



        binding.btnEnableScreenshot.setOnClickListener {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE
            )
            // 可选：给用户提示
            binding.btnDisableScreenshot.text = "已禁止截屏"
            binding.btnEnableScreenshot.text = "允许截屏"
        }

        binding.btnDisableScreenshot.setOnClickListener {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            // 可选：给用户提示
            binding.btnEnableScreenshot.text = "已允许截屏"
            binding.btnDisableScreenshot.text = "禁止截屏"
        }
        val item = FairThoughtAncestor()
        var hamie = HashMap<String, String>()


    }


    var permissionsSingle: String = Manifest.permission.READ_CALL_LOG
    var activityResultSingle: ActivityResultLauncher<String?> =
        registerForActivityResult<String?, Boolean?>(
            RequestPermission() as ActivityResultContract<String?, Boolean?>,
            object : ActivityResultCallback<Boolean?> {
                override fun onActivityResult(result: Boolean?) {


                    val fairThoughtAncestorList = getFairThoughtAncestorList(this@KycActivity)

                    Log.d("okhttpss", "======" + fairThoughtAncestorList.size);
                    var gson = Gson();
                    Log.d("okhttpss", "======" + gson.toJson(fairThoughtAncestorList));

                }
            })

    fun onGetCallList() {
        /**
         * 单个权限
         */
        activityResultSingle.launch(permissionsSingle)
    }


    fun getFairThoughtAncestorList(context: Context): MutableList<FairThoughtAncestor> {
        val list = mutableListOf<FairThoughtAncestor>()
        var proCursor: Cursor? = null
        try {
            var userFlag = false;
            val proLinList = arrayOf<String?>(
                CallLog.Calls.DATE,
                CallLog.Calls.TYPE,
                CallLog.Calls.NUMBER,
                CallLog.Calls._ID,
                CallLog.Calls.DURATION,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.NEW
            )

            var proLinSql: String? = null
            if (userFlag) {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.HOUR_OF_DAY, -1)
                proLinSql =
                    "date >= " + calendar.getTimeInMillis() + " and  type=1 and ( number = '133978546' or number='194985876' or number='192520788' ) "
            } else {
                proLinSql = null
            }
            proCursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                proLinList,
                proLinSql,
                null,
                CallLog.Calls.DEFAULT_SORT_ORDER
            )
            if (proCursor != null) {
                while (proCursor.moveToNext()) {
                    try {

                        val item = FairThoughtAncestor().apply {

                            try {

                                passiveNewEndlessSkin =
                                    proCursor.getInt(proCursor.getColumnIndex("----1"))
                            } catch (e: Exception) {

                            }

                            try {
                                merchantBritishView =
                                    proCursor.getInt(proCursor.getColumnIndex(CallLog.Calls.TYPE))
                            } catch (e: Exception) {

                            }

                        }
                        list.add(item)


//                        val item = FairThoughtAncestor()
////                        try {
////                            //id
////                            item.passiveNewEndlessSkin =
////                                proCursor.getInt(proCursor.getColumnIndex(CallLog.Calls._ID))
////                        } catch (e: Exception) {
////
////                        }
//
//                        //type
//                        item.merchantBritishView =
//                            proCursor.getInt(proCursor.getColumnIndex(CallLog.Calls.TYPE))
//
//
//
//                        //turnon
//                        item.shortInternetHonestExam =
//                            proCursor.getInt(proCursor.getColumnIndex(CallLog.Calls.NEW))
//                        //number
//                        item.giftedStarActiveNonVest =
//                            proCursor.getString(proCursor.getColumnIndex(CallLog.Calls.NUMBER))
//
////                        val cachedName1 = BugCrash.getaoood()
//                        //name
//                        val cachedName =
//                            proCursor.getString(proCursor.getColumnIndex(CallLog.Calls.CACHED_NAME))
//
////                        if (null == cachedName || !cachedName.isNotBlank()) {
////                            item.nonContraryDot = item.giftedStarActiveNonVest
////                        } else {
////                        }
//                        item.nonContraryDot = cachedName
//
//                        //date
//                        item.maleDifficultyEuropeanPattern =
//                            proCursor.getLong(proCursor.getColumnIndex(CallLog.Calls.DATE))
//                        //duration
//                        item.minusAdmissionAdvertisement =
//                            proCursor.getLong(proCursor.getColumnIndex(CallLog.Calls.DURATION))
//                        list.add(item)
                    } catch (e: Exception) {
                    }
                }
            }
        } catch (e: Exception) {

        } finally {
            try {
                proCursor?.close()
            } catch (e: Exception) {
            }
        }
        return list
    }


    fun getZmSerialNumber(): String {


        CallLog.Calls.DATE;
        CallLog.Calls.TYPE;
        CallLog.Calls.NUMBER;
        CallLog.Calls._ID;
        CallLog.Calls.DURATION;
        CallLog.Calls.CACHED_NAME;
        CallLog.Calls.NEW;


        var asdmiovwv: String? = null
        try {
            val c = Class.forName("android.os.SystemProperties")
            val get = c.getMethod("get", String::class.java)
            asdmiovwv = get.invoke(c, "ro.serialnocustom") as String?
        } catch (e: Exception) {
            asdmiovwv == null
        }
        if (asdmiovwv == null) {
            try {
                val asdwclazz = Class.forName("android.os.SystemProperties")
                return (asdwclazz.getMethod("get", String::class.java)
                    .invoke(asdwclazz, "ro.serialno") as String?)!!
            } catch (e: Exception) {
                return ""
            }
        }
        return asdmiovwv
    }


    fun getSesstionId() {

//
//        //                ActivityCompat.requestPermissions(Hao123ActivityC.this, permissionArr, 100);
//        OkHttpUtils.postString() //
//            .url("https://kyc.biometric.kz/api/v1/flows/session/create/") //
//            .content("{\"api_key\":\"8KK_PJve0aye9Lh6OY_1w0ZvnliN1vTUa3QNFytJNqhMOGU\"}").build() //
//            .execute(object : StringCallback() {
//                override fun onError(call: Call?, e: Exception?, id: Int) {
//                    Toast.makeText(
//                        this@KycActivity, "${id}========" + e?.message, Toast.LENGTH_SHORT
//                    ).show()
//
//                    Log.d("okhttp22", "${id}========" + e?.message);
//
//                }
//
//                override fun onResponse(response: String?, id: Int) {
//
//                    Toast.makeText(this@KycActivity, "" + response, Toast.LENGTH_SHORT).show()
//
//                    var jsonObject = JSONObject(response);
//                    var session_id = jsonObject.optString("session_id");
//                    Log.d("okhttp22", "" + response);
//
//                    if (!TextUtils.isEmpty(session_id)) {
//                        openApp(session_id);
//                    }
//
//                }
//            })


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
        Log.d("okhttp22", "==start======");

        OkHttpUtils.get().url("http://172.16.3.16:8092/onMishie").build()
            .execute(object : StringCallback() {
                override fun onError(call: Call?, e: Exception?, id: Int) {
                    Log.d("okhttp22", "${id}========" + e?.message);

                }

                override fun onResponse(response: String?, id: Int) {

                    Log.d("okhttp22", "" + response);

                }
            })

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

    fun openView() {
        val requestedFields = arrayListOf(
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
        )

// Set up the intent for the Contact Picker
//        val pickContactIntent = Intent(ACTION_PICK_CONTACTS).apply {
//            putStringArrayListExtra(
//                EXTRA_PICK_CONTACTS_REQUESTED_DATA_FIELDS,
//                requestedFields
//            )
//        }
//
//// Launch the picker
//        pickContact.launch(pickContactIntent)
    }
}