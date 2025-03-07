package con.fire.android2023demo.ui.fragment;

import androidx.fragment.app.Fragment;

import con.fire.android2023demo.widget.ProgressBarDialog;

public class BaseUIFragment extends Fragment {
    ProgressBarDialog mDialog;

    public void showLoading() {

        try {
            if (mDialog == null) {
                mDialog = new ProgressBarDialog(getActivity());
                mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void onDismissLoading() {

        try {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
