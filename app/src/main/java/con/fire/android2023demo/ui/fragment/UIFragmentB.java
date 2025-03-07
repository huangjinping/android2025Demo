package con.fire.android2023demo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import con.fire.android2023demo.databinding.FragmentaUiBinding;
import con.fire.android2023demo.databinding.FragmentbUiBinding;

public class UIFragmentB extends BaseUIFragment {


    FragmentbUiBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentbUiBinding.inflate(inflater);
        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "dd", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}
