package con.fire.android2023demo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.UUID;

import con.fire.android2023demo.databinding.FragmentRepaycomplaintdialogBinding;

public class RepayComplaintDialogFragment extends AppCompatDialogFragment {
    
    FragmentRepaycomplaintdialogBinding binding;


    private static RepayComplaintDialogFragment repayComplaintDialogFragment = null;
    public static RepayComplaintDialogFragment newInstance(String url) {
        if (repayComplaintDialogFragment == null) {
            repayComplaintDialogFragment = new RepayComplaintDialogFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("url", url);
        repayComplaintDialogFragment.setArguments(bundle);
        return repayComplaintDialogFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentRepaycomplaintdialogBinding.inflate(inflater);
        binding.button11.setText(UUID.randomUUID().toString() + "");
        return binding.getRoot();
    }
}
