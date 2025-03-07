package con.fire.android2023demo.ui.ap162;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import con.fire.android2023demo.databinding.FragmentA162Binding;
import con.fire.android2023demo.ui.fragment.DialogFragment1;

public class Tab2Fragment extends Fragment {

    FragmentA162Binding binding;

//    DialogViewModel dialogViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentA162Binding.inflate(inflater);
        binding.txtTips.setText("Tab2Fragment");

        binding.txtTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "========", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isAdded()) {
                            Log.d("okhtt22", "==============1");

                            addChange();
                        } else {
                            Log.d("okhtt22", "==============2");

                        }

                    }
                }, 5000);
            }
        });

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

    private void addChange() {

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.banner);
//        binding.imgIcon.setImageBitmap(bitmap);

        DialogFragment1 dialogFragment1 = new DialogFragment1();
        dialogFragment1.show(getChildFragmentManager(), "333", 1);
    }
}
