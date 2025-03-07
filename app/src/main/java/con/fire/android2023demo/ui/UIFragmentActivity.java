package con.fire.android2023demo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import con.fire.android2023demo.R;
import con.fire.android2023demo.databinding.ActivityUifragmentBinding;
import con.fire.android2023demo.ui.fragment.UIFragmentA;
import con.fire.android2023demo.ui.fragment.UIFragmentB;

public class UIFragmentActivity extends AppCompatActivity {

    ActivityUifragmentBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUifragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btn01.setOnClickListener(v -> setFragment1());
        binding.btn02.setOnClickListener(v -> setFragment2());
    }

    private void setFragment1() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_container, new UIFragmentA()).commitAllowingStateLoss();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setFragment2();
            }
        }, 5000);

    }

    private void setFragment2() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_container, new UIFragmentB()).commitAllowingStateLoss();
    }
}
