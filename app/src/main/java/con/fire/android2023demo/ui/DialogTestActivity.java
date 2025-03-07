package con.fire.android2023demo.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import con.fire.android2023demo.R;
import con.fire.android2023demo.databinding.ActivityDialogtestBinding;
import con.fire.android2023demo.ui.fragment.A162Fragment;
import con.fire.android2023demo.ui.fragment.A306Fragment;
import con.fire.android2023demo.ui.fragment.CommonChooseDialogFragment;
import con.fire.android2023demo.ui.fragment.DialogFragment1;
import con.fire.android2023demo.ui.fragment.RepayComplaintDialogFragment;
import con.fire.android2023demo.utils.LoadingDialogUtils;
import con.fire.android2023demo.utils.LogUtils;
import con.fire.android2023demo.vm.DialogViewModel;
import con.fire.android2023demo.widget.ProgressBarDialog;

public class DialogTestActivity extends AppCompatActivity {
    protected CommonChooseDialogFragment networkSettingDialogFragment;
    ActivityDialogtestBinding binding;
    LoadingDialogUtils dialogUtils = new LoadingDialogUtils();


    DialogViewModel mViewModel;
    private int inx = 0;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogtestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mViewModel = new ViewModelProvider(this).get(DialogViewModel.class);

        LogUtils.logS("111", UUID.randomUUID().toString());
//        networkSettingDialogFragment = CommonChooseDialogFragment.newInstance();
//        networkSettingDialogFragment = null;

        binding.buttonT0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                creatTestOOOFm();
            }
        });

        binding.buttonT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openzIndexV2();
            }
        });
        binding.btn195.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show195();
            }
        });

        binding.btn154.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show154();
            }
        });
        binding.btn201.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show201();
            }
        });

        binding.btn162.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show162();
            }
        });

        mViewModel.dataLog.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btn162.setText("" + s);
            }
        });

        binding.btn306.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show306();


                new  Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        show162();
                    }
                },3300);
            }
        });
    }

    private void show195() {
//        this.finish();
        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {

                dialogUtils.showLoadingDialog(DialogTestActivity.this, "ddddd");
            }
        }, 3000);
    }

    public void creatTestOOOFm() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    try {
                        String mise = "creatTestOOOFm";
                        List data = new ArrayList();

                        Log.d("okhttp", "==========" + System.currentTimeMillis());
                        while (true) {
                            /**
                             * json采集
                             */
                            mise += mise + "cllllllllllllllllll";
                            data.add("cllllllllllllllllll" + mise + System.currentTimeMillis() + "cc");
                            Gson gson = new Gson();
                            Log.d("okhttp", gson.toJson(data));
                        }
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }


//
//    public void showNetworkSettingDialogFragment() {
//        try {
//            dismissNetworkSettingDialogFragment();
//            if (networkSettingDialogFragment == null) {
//                networkSettingDialogFragment = CommonChooseDialogFragment.newInstance();
//                networkSettingDialogFragment.show(getSupportFragmentManager(), getClass().getName() + "NetworkSettingdialogfragment");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void dismissNetworkSettingDialogFragment() {
//        try {
//            if (networkSettingDialogFragment != null) {
//                networkSettingDialogFragment.dismiss();
//                networkSettingDialogFragment = null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void openzIndexV2() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                showNetworkSettingDialogFragment();
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                showNetworkSettingDialogFragment();
            }
        }, 4000);
    }

    public void showNetworkSettingDialogFragment() {
        try {
            if (networkSettingDialogFragment != null) {

                networkSettingDialogFragment.show(getSupportFragmentManager(), getClass().getName() + "NetworkSettingdialogfragment");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openzIndex() {
        DialogFragment1 dialogFragment1 = new DialogFragment1();
        dialogFragment1.show(getSupportFragmentManager(), "22222", 3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogFragment1 dialogFragment1 = new DialogFragment1();
                dialogFragment1.show(getSupportFragmentManager(), "111", 2);

            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogFragment1 dialogFragment1 = new DialogFragment1();
                dialogFragment1.show(getSupportFragmentManager(), "333", 1);

            }
        }, 4000);
    }

    private void show154() {
        RepayComplaintDialogFragment dialogFragment1 = RepayComplaintDialogFragment.newInstance("2");
        dialogFragment1.show(getSupportFragmentManager(), "3333");
        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {
                RepayComplaintDialogFragment dialogFragment1 = RepayComplaintDialogFragment.newInstance("2");
                dialogFragment1.show(getSupportFragmentManager(), "3333");
            }
        }, 3000);
    }

    private void show201() {

        ProgressBarDialog progressBarDialog = new ProgressBarDialog(this);

        progressBarDialog.show();
        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    progressBarDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("okhttos", "=============" + e.getMessage());

                }
            }
        }, 8000);
    }


    private void show306() {

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_container, new A306Fragment());
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void show162() {

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_container, new A162Fragment());
        fragmentTransaction.commitAllowingStateLoss();
        countdown();

        new Handler() {
        }.postDelayed(new Runnable() {
            @Override
            public void run() {
//                FragmentManager supportFragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.layout_container, new BaseUIFragment());
//                fragmentTransaction.commit();
            }
        }, 5000);


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


    }

    private void countdown() {
        Log.d("okhttp2", "========2222");

        try {
            countDownTimer = new CountDownTimer(10 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int value = (int) (millisUntilFinished / 1000);
                    Log.d("okhttp2", "========" + value);

                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mViewModel.dataLog.setValue("" + value);


                }

                @Override
                public void onFinish() {
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            countDownTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
