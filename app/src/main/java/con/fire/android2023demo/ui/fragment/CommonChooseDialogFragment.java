package con.fire.android2023demo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import con.fire.android2023demo.R;

public class CommonChooseDialogFragment extends DialogFragment {

    public static CommonChooseDialogFragment newInstance() {

        return new CommonChooseDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        return view;
    }
}
