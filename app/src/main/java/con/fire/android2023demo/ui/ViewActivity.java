package con.fire.android2023demo.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.PopupWindowCompat;

import con.fire.android2023demo.R;
import con.fire.android2023demo.databinding.ActivityViewBinding;
import con.fire.android2023demo.utils.AutoTextViewAdapter;
import con.fire.android2023demo.utils.EmailAutoTokenizer;
import con.fire.android2023demo.utils.SemicolonTokenizer;
import con.fire.android2023demo.widget.DemoPopWindow;

public class ViewActivity extends AppCompatActivity {


    ActivityViewBinding binding;

    private final String TAG = "okht2tp";


    private static final String[] data = new String[]{"outlook.com", "gmail.com", "164.com", "qq.com", "126.com"};
    private static final String[] AUTO_EMAILS = {"@163.com", "@sina.com", "@qq.com", "@126.com", "@gmail.com", "@apple.com"};
    private String[] email_sufixs = new String[]{"@qq.com", "@163.com", "@126.com", "@gmail.com", "@sina.com", "@hotmail.com", "@yahoo.cn", "@sohu.com", "@foxmail.com", "@139.com", "@yeah.net", "@vip.qq.com", "@vip.sina.com"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.button5.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "" + binding.button5.getWidth());
//                showPop();
            }
        });


        setMulAutoView();
        setAutoView();
        setsoos();
        InputFilter mInputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().contains("\n")) {
                    return source.toString().replace("\n", "");
                }
                return source;
            }
        };
        binding.etdText.setFilters(new InputFilter[]{mInputFilter});//这里的editText.getFilters()[0]是为了保留上面的xml设置的LengthFilter同时有效

    }

    private void setsoos() {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, email_sufixs);


        binding.autoviewemal.setAdapter(adapter);
        binding.autoviewemal.setTokenizer(new EmailAutoTokenizer());

        binding.autoviewemal2.setAdapter(adapter);
        binding.autoviewemal2.setTokenizer(new EmailAutoTokenizer());


        binding.autoviewemal3.setAdapter(adapter);
        binding.autoviewemal3.setTokenizer(new EmailAutoTokenizer());


    }

    private AutoTextViewAdapter adapter;

    private void setAutoView() {
        adapter = new AutoTextViewAdapter(this);
        binding.autoview.setAdapter(adapter);
        binding.autoview.setThreshold(1);//输入1个字符时就开始检测，默认为2个
        binding.autoview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                Log.d("SSSS", "===========111" + input);

                adapter.mList.clear();
                autoAddEmails(input);
                adapter.notifyDataSetChanged();
                binding.autoview.showDropDown();
            }
        });//监听autoview的变化

    }

    private void autoAddEmails(String input) {
        System.out.println("input-->" + input);
        String autoEmail = "";
        if (input.length() > 0) {
            for (int i = 0; i < AUTO_EMAILS.length; ++i) {
                if (input.contains("@")) {//包含“@”则开始过滤
                    String filter = input.substring(input.indexOf("@") + 1, input.length());//获取过滤器，即根据输入“@”之后的内容过滤出符合条件的邮箱
                    System.out.println("filter-->" + filter);
                    if (AUTO_EMAILS[i].contains(filter)) {//符合过滤条件
                        autoEmail = input.substring(0, input.indexOf("@")) + AUTO_EMAILS[i];//用户输入“@”之前的内容加上自动填充的内容即为最后的结果
                        adapter.mList.add(autoEmail);
                    }
                } else {
                    autoEmail = input + AUTO_EMAILS[i];
                    adapter.mList.add(autoEmail);
                }
            }
        }


    }

    private void setMulAutoView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text, data);
        binding.edmila.setAdapter(adapter);
        binding.edmila.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.edmila.setTokenizer(new SemicolonTokenizer('@'));

//        binding.edmila.showDropDown();
    }

    private void showPop() {
        DemoPopWindow window = new DemoPopWindow(this);
        PopupWindowCompat.showAsDropDown(window, binding.button5, 0, 0, Gravity.START);

    }


}
