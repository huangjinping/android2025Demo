package con.fire.android2023demo.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.HashMap;
import java.util.Map;

import con.fire.android2023demo.R;

public class ContractTestActivity extends AppCompatActivity {
    public static int REQUEST_CONTRACT = 1002;
    TextView textView;
    String[] permissionArr = {
            Manifest.permission.READ_CONTACTS};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractest);
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ontest();
            }
        });
    }

    private void ontest() {
        ActivityCompat.requestPermissions(ContractTestActivity.this, permissionArr, 101);
//        onContract();
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        Uri contactUri = data.getData();
//
        Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
        String contractPhone = null;
        String contactName = null;
        if (cursor != null && cursor.moveToFirst()) {
//            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
//            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            contractPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            textView.setText(contactName + "===" + contractPhone);

        }

//        if (requestCode == REQUEST_CONTRACT && resultCode == RESULT_OK) {
//            Uri uri = data.getData();
//            String contractPhone = null;
//            String contactName = null;
//            ContentResolver contentResolver = getContentResolver();
//            Cursor cursor = null;
//            if (uri != null) {
//                cursor = contentResolver.query(uri, null, null, null, null);
//            }
//            while (cursor.moveToNext()) {
//                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                contractPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//            }
//            cursor.close();
//            if (contractPhone != null) {
//                contractPhone = contractPhone.replaceAll("-", "");
//                contractPhone = contractPhone.replaceAll(" ", "");
//            }
//            textView.setText(contactName + "===" + contractPhone);
//        }
//        Map<String, String> contactMap = getContactMap(data);
//        Gson gson = new Gson();
//
//        Log.d("mPad", gson.toJson(contactMap));


    }

    public void onContract() {
//        JSONObject result = new JSONObject();
//        result.put("permission", 1);
//        callContractback.invoke(result);

        try {
//            startActivityForResult(new Intent(
//                    Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CONTRACT);

//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
//            startActivityForResult(intent, REQUEST_CONTRACT);

//            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
//            startActivityForResult(intent, 1000);


            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, REQUEST_CONTRACT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("Range")
    private Map<String, String> getContactMap(Intent data) {
        Map<String, String> contactMap = new HashMap<>();
        ContentResolver reContentResolverol = getContentResolver();
        Uri contactData = data.getData();
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(contactData, null, null, null, null);
        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            contactMap.put("name", username);
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);

            while (phone.moveToNext()) {
                String mobile = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (!TextUtils.isEmpty(mobile)) {
                    contactMap.put("mobile", mobile.replace(" ", ""));
//                    contactMap.put("mobile", mobile.replaceAll("[^\\d]+", ""));

                }
            }
        }
        return contactMap;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermission(this, permissions)) {
            onContract();
        }
    }

    public boolean checkPermission(Context context, String[] perm) {
        for (String item : perm) {
            if (ActivityCompat.checkSelfPermission(context,
                    item) != PackageManager.PERMISSION_GRANTED) {

                return false;
            }
        }
        return true;
    }
}

