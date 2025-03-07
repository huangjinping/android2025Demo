package con.fire.android2023demo.ui.ap162;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import con.fire.android2023demo.databinding.FragmentA162Binding;

public class Tab0Fragment extends Fragment {

    FragmentA162Binding binding;

//    DialogViewModel dialogViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentA162Binding.inflate(inflater);
        binding.txtTips.setText("Tab0Fragment");

//        dialogViewModel = new ViewModelProvider(getActivity()).get(DialogViewModel.class);
//        dialogViewModel.dataLog.observe(getActivity(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                Log.d("okhttp2", "ssss========" + s);
//
//                binding.txtTips.setText("ss" + s);
//                if (s.equals("3")) {
//                    dialogViewModel.dataLog2.observe(null, new Observer<String>() {
//                        @Override
//                        public void onChanged(String s) {
//
//                        }
//                    });
//                }
//
//            }
//        });

//
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    sleep(7000);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        binding.txtTips.setText("ddd");
//                    }
//                });
//            }
//        }.start();


        return binding.getRoot();


    }
}
