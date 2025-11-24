package con.fire.android2023demo.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import con.fire.android2023demo.databinding.ActivitySelectcontractBinding;

//https://blog.csdn.net/s779528166/article/details/79061258
//https://blog.csdn.net/windowsxp2014/article/details/131117214
//官方地址  https://developer.android.com/guide/components/intents-common?hl=zh-cn
public class SelectContractActivity extends AppCompatActivity {

    static final int REQUEST_SELECT_PHONE_NUMBER = 1;
    public static int REQUEST_CONTRACT = 1002;
    final String TAG = "SELCS";
    ActivitySelectcontractBinding binding;
    ActivityResultLauncher getContact = registerForActivityResult(new ActivityResultContracts.PickContact(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {

            Uri contactUri = result;
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                if (TextUtils.isEmpty(number)) {
                    number = number.replaceAll(" ", "");
                }
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String display_name = cursor.getString(nameIndex);
                setData(number, display_name);

            }
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectcontractBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button0.setOnClickListener(v -> startIntent0());
        binding.button1.setOnClickListener(v -> startIntent1());
        binding.button2.setOnClickListener(v -> startIntent2());
        binding.button3.setOnClickListener(v -> startIntent3());
        binding.button4.setOnClickListener(v -> startIntent4());
        binding.button5.setOnClickListener(v -> startIntent5());
        binding.button6.setOnClickListener(v -> startIntent6());
        binding.button7.setOnClickListener(v -> startIntent7());
        binding.button8.setOnClickListener(v -> startIntent8());

        binding.button9.setOnClickListener((v -> startIntent9()));
    }

    private void startIntent9() {

        try {
            startActivityForResult(new Intent("android.intent.action.PICK", ContactsContract.CommonDataKinds.Phone.CONTENT_URI), REQUEST_CONTRACT);
        } catch (ActivityNotFoundException e9) {
            e9.printStackTrace();
//            b0.d("No se ha podido obtener el contacto, por favor rellene manualmente");
        }

    }

    /**
     * 有选项弹框
     */
    private void startIntent0() {

        try {
            Log.d(TAG, "startIntent0");
            resetData();
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, REQUEST_CONTRACT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 没有选项弹框
     */
    private void startIntent1() {

        Log.d(TAG, "startIntent1");
        resetData();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);

        startActivityForResult(intent, REQUEST_CONTRACT);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 10 * 1000);
    }

    /**
     * 有默认选项弹框
     */
    private void startIntent2() {
        Log.d(TAG, "startIntent2");
        resetData();
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CONTRACT);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /**
     * 没有选项弹框
     */
    private void startIntent3() {
        Log.d(TAG, "startIntent3");
        resetData();
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("vnd.android.cursor.dir/phone");
//        i.setType("vnd.android.cursor.dir/contact");

        startActivityForResult(i, REQUEST_CONTRACT);
    }

    private void startIntent4() {
        Log.d(TAG, "startIntent4");
        resetData();
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivityForResult(i, REQUEST_CONTRACT);
    }

    private void startIntent5() {
        Log.d(TAG, "startIntent5");
        resetData();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(intent, REQUEST_CONTRACT);
    }

    public void startIntent6() {
        /**
         * 官方
         *
         * 选择特定联系人数据
         * 如需让用户选择某一条具体的联系人信息，如电话号码、电子邮件地址或其他数据类型，请使用 ACTION_PICK 操作，并将 MIME 类型指定为下列其中一个内容类型（如 CommonDataKinds.Phone.CONTENT_TYPE），以获取联系人的电话号码。
         *
         * 如果您只需要检索一种类型的联系人数据，则将此方法与来自 ContactsContract.CommonDataKinds 类的 CONTENT_TYPE 配合使用要比使用 Contacts.CONTENT_TYPE 更高效（如上一部分所示），因为结果可让您直接访问所需数据，无需对联系人提供程序执行更复杂的查询。
         *
         * 传送至您的 onActivityResult() 回调的结果 Intent 包含指向所选联系人数据的 content: URI。响应会为您的应用授予该联系人数据的临时读取权限，即使您的应用不具备 READ_CONTACTS 权限也没有关系。
         *
         */
        // Start an activity for the user to pick a phone number from contacts
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
        }


    }

    public void startIntent7() {
        getContact.launch(null);

    }

    public void startIntent8() {
        Intent intent = new Intent();
        intent.setData(Uri.parse("content://contacts"));
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, requestCode + "==========" + resultCode);
//        onResult0(requestCode, resultCode, data);
//        onResult1(requestCode, resultCode, data);
//        onResult2(requestCode, resultCode, data);
//        onResult3(requestCode, resultCode, data);
//        onResult4(requestCode, resultCode, data);
//        onResult5(requestCode, resultCode, data);
        onResult6(requestCode, resultCode, data);
//        onResult9(requestCode, resultCode, data);

    }


    private void onResult9(int i9, int i10, @Nullable Intent intent) {
        Uri data;
        if (intent == null || (data = intent.getData()) == null) {
            return;
        }
        try {
            Cursor query = getContentResolver().query(data, new String[]{"data1", "display_name"}, null, null, null);
            if (query != null && query.moveToFirst()) {
                String string = query.getString(0);
                String string2 = query.getString(1);

                setData(string, string2);
                if (!query.isClosed()) {
                    query.close();
                }
            }
        } catch (IllegalArgumentException e9) {
            e9.printStackTrace();
        } catch (SecurityException unused) {
        }

    }

    /**
     * 线上
     */
    @SuppressLint("Range")
    private void onResult0(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "requestCode:" + requestCode + "resultCode:" + resultCode);
        if (RESULT_OK == resultCode) {
            Activity activity = this;
            Uri uri = data.getData();
            String contractPhone = null;
            String contactName = null;
            ContentResolver contentResolver = activity.getContentResolver();
            Cursor cursor = null;
            if (uri != null) {
                cursor = contentResolver.query(uri, null, null, null, null);
            }
            while (cursor.moveToNext()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contractPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            cursor.close();
            if (contractPhone != null) {
                contractPhone = contractPhone.replaceAll("-", "");
                contractPhone = contractPhone.replaceAll(" ", "");
            }
            setData(contractPhone, contactName);
        }
    }

    @SuppressLint("Range")
    private void onResult1(int requestCode, int resultCode, @Nullable Intent data) {
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Uri contactUri = data.getData();
//
        Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
        String contractPhone = null;
        String contactName = null;
        if (cursor != null && cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            contractPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            setData(contractPhone, contactName);
        }
    }

    @SuppressLint("Range")
    private void onResult2(int requestCode, int resultCode, @Nullable Intent data) {

        /**
         * 需要权限
         */
        Map<String, String> contactMap = new HashMap<>();
        ContentResolver reContentResolverol = getContentResolver();
        Uri contactData = data.getData();
        @SuppressWarnings("deprecation") Cursor cursor = managedQuery(contactData, null, null, null, null);
        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            contactMap.put("name", username);
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);

            while (phone.moveToNext()) {
                String mobile = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (!TextUtils.isEmpty(mobile)) {
                    contactMap.put("mobile", mobile.replace(" ", ""));
//                    contactMap.put("mobile", mobile.replaceAll("[^\\d]+", ""));

                }
            }
        }

        setData(contactMap.get("mobile"), contactMap.get("name"));
    }


    @SuppressLint("Range")
    private void onResult3(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "requestCode:" + requestCode + "resultCode:" + resultCode);
        if (RESULT_OK == resultCode) {
            String contactName = "";
            String contractPhone = "";
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    Cursor cursor = getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}, null, null, null);
                    while (cursor.moveToNext()) {
                        String number = cursor.getString(0);
                        String phoneNum = number.replaceAll("-", "");
                        contractPhone = phoneNum;
                        contactName = cursor.getString(1);

                    }
                    cursor.close();
                }
            }

            setData(contractPhone, contactName);
        }
    }

    @SuppressLint("Range")
    private void onResult4(int requestCode, int resultCode, @Nullable Intent data) {
        /**
         * 需要权限
         */
        Log.d(TAG, "requestCode:" + requestCode + "resultCode:" + resultCode);
        if (RESULT_OK == resultCode) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            //得到ContentResolver对象
            ContentResolver cr = getContentResolver();
            //取得电话本中开始一项的光标
            Cursor cursor = cr.query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //取得电话号码
                String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
                if (phone != null) {
                    phone.moveToFirst();
                    String phoneNum = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    setData(phoneNum, username);
                }
                phone.close();
                cursor.close();
            }
        }
    }


    @SuppressLint("Range")
    private void onResult5(int requestCode, int resultCode, @Nullable Intent data) {

        Log.d(TAG, "requestCode:" + requestCode + "resultCode:" + resultCode);
        if (RESULT_OK == resultCode) {
            if (data == null) {
                return;
            }
            Cursor query = getContentResolver().query(data.getData(), new String[]{"data1", "display_name"}, null, null, null);
            String str2 = "", str = "";
            if (query != null) {
                if (query.moveToFirst()) {
                    str = query.getString(query.getColumnIndex("data1"));
                    str2 = query.getString(query.getColumnIndex("display_name"));
                } else {
                    str = "";
                }
                query.close();
            } else {
                str = "";
            }
            setData(str, str2);
        }
    }

    @SuppressLint("Range")
    private void onResult6(int requestCode, int resultCode, @Nullable Intent data) {
        /**
         * 官方
         * 选择特定联系人数据
         * 如需让用户选择某一条具体的联系人信息，如电话号码、电子邮件地址或其他数据类型，请使用 ACTION_PICK 操作，并将 MIME 类型指定为下列其中一个内容类型（如 CommonDataKinds.Phone.CONTENT_TYPE），以获取联系人的电话号码。
         *
         * 如果您只需要检索一种类型的联系人数据，则将此方法与来自 ContactsContract.CommonDataKinds 类的 CONTENT_TYPE 配合使用要比使用 Contacts.CONTENT_TYPE 更高效（如上一部分所示），因为结果可让您直接访问所需数据，无需对联系人提供程序执行更复杂的查询。
         *
         * 传送至您的 onActivityResult() 回调的结果 Intent 包含指向所选联系人数据的 content: URI。响应会为您的应用授予该联系人数据的临时读取权限，即使您的应用不具备 READ_CONTACTS 权限也没有关系。
         */
        Log.d(TAG, "requestCode:" + requestCode + "resultCode:" + resultCode);
        if (RESULT_OK == resultCode) {
            if (data == null) {
                return;
            }
            if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
                // Get the URI and query the content provider for the phone number
                Uri contactUri = data.getData();
                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                // If the cursor returned is valid, get the phone number
                if (cursor != null && cursor.moveToFirst()) {
                    int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String number = cursor.getString(numberIndex);
                    if (TextUtils.isEmpty(number)) {
                        number = number.replaceAll(" ", "");
                    }
                    int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    String display_name = cursor.getString(nameIndex);
                    setData(number, display_name);

                } else {


                    Toast.makeText(this, "变成手动输入方式", Toast.LENGTH_SHORT).show();
                    /**
                     * 触发：
                     * 表明该手机不支持无权限选择，需要适配处理如下：
                     * 1.需要把联系人选择方式，变成手动输入方式
                     * 2.主动显示光标、光标显示在该组联系人，第一个输入框最后显示
                     * 3.主动拉起键盘
                     */
                }
            }
        }
    }

    private void resetData() {
        setData("", "");
    }

    private void setData(String phone, String name) {
        binding.txtResult.setText(phone + ":" + name);
    }
}
