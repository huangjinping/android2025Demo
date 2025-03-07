package con.fire.android2023demo.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.databinding.ActivityWebBinding;
import con.fire.android2023demo.databinding.ActivityWebview2Binding;

//https://mp.weixin.qq.com/s/r4GFZBJTranjqGzABbvQqw
public class WebView2Activity extends AppCompatActivity {

    private ActivityWebview2Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebview2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
