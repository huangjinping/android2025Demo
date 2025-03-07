package con.fire.android2023demo.ui.ap162;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import con.fire.android2023demo.databinding.ActivityViewpager2Binding;

public class ViewPager2Activity extends AppCompatActivity {
    ActivityViewpager2Binding binding;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewpager2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentList.add(new Tab0Fragment());
        fragmentList.add(new Tab1Fragment());
        fragmentList.add(new Tab2Fragment());

        initPage();


    }

    private void initPage() {
        binding.viewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
//        binding.viewPager2.setUserInputEnabled(false);
        binding.viewPager2.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), this.getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        });
        binding.viewPager2.post(() -> binding.viewPager2.setCurrentItem(0));

    }
}
