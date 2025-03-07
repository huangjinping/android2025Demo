package con.fire.android2023demo.ui.fragment;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import con.fire.android2023demo.R;

//https://www.codenong.com/cs108881206/
public class DialogFragment1 extends DialogFragment {

    private TextView tvTest;

    private int id;

    public DialogFragment1() {
        // Required empty public constructor
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return
     */
    private static int[] getScreenSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    public void show(@NonNull FragmentManager manager, @Nullable String tag, int id) {
        super.show(manager, tag);
        this.id = id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getContext() != null) {
            Dialog dialog = new Dialog(getContext(), getTheme());
            Window window = dialog.getWindow();
            if (window != null) {
                window.setDimAmount(0.5f);
                window.setType(WindowManager.LayoutParams.TYPE_APPLICATION);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                if (id == 2) {
                    window.setType(WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW);
                }
                if (id == 3) {
                    window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
                }
            }
            return dialog;
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        initView(view);
        initAnim();
        return view;
    }

    private void initAnim() {
        //加入动画
        if (getContext() != null && getDialog() != null && getDialog().getWindow() != null) {
            ObjectAnimator.ofFloat(getDialog().getWindow().getDecorView(), "translationX", getScreenSize(getContext())[0], 0).setDuration(1000).start();
        }
    }

    private void initView(View view) {
        tvTest = view.findViewById(R.id.tv_test);
        tvTest.setText(String.valueOf(id));
        if (id == 2) {
            tvTest.setBackgroundColor(Color.RED);
        }

        if (id == 3) {
            tvTest.setBackgroundColor(Color.YELLOW);
        }
    }

}
