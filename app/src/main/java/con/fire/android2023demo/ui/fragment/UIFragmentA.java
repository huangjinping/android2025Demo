package con.fire.android2023demo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import con.fire.android2023demo.databinding.FragmentaUiBinding;

public class UIFragmentA extends BaseUIFragment {


    FragmentaUiBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentaUiBinding.inflate(inflater);
        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "AA", Toast.LENGTH_SHORT).show();
                showLoading();
                startv1();
            }
        });
        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onDismissLoading();

    }

    private void startv1() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onDismissLoading();
            }
        }, 8000);
    }
}
