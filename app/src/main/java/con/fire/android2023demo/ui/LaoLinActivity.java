package con.fire.android2023demo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.databinding.ActivityLaolinBinding;
import con.fire.android2023demo.utils.lin.pzetpkg;

public class LaoLinActivity extends AppCompatActivity {

    private ActivityLaolinBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLaolinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func1();
            }
        });
    }

    private void func1() {

        Log.d("func1", "getIp" + getIp());


        Log.d("laolin", pzetpkg.etol("g9RYxazAOc7 + LpoUZT1SGeOdUWFyD2MH0N4l2w2CN + J4NuVoPEg + BPeOxCwW1xFEXs7fzraxbsw8InBKzlLvHaWHxTnNF70L2K0WTsr8Z9V3oggDPHPOFTuU95bl4YXfxoJR6aejFJaZjSEKBeb7mloIP7acTSbHJquUYhK8J9DnGIEyB5t5RfcY7CX7D1Y //8w0pnxXC+Fbc/1mnb8qztKQok8E3HQ62H/Pzy8P34GcsZX5gjumGQd8NrMK5s1qBohZJWeEkDx208ShkJ8YGJCMFdQvFY8ETq0rtIJRWOaC6TV5I14FcESqpfIgdecS6k09JK+AsbLjkt+w6b/jHUGouC6WobQFrb/8Tw7q2d++PJ53QGfbrg1iROrn0dHjDSKYpvuIvKfpmMp+mUeGU33e5axMwL2MnnGZFCQvMiy1VB8Th9Q3pXAHv0WLwkKZiaCV9MGRsBR+jRt1/RpRzCro0slXJT+UMK9+DoN6k7rB3omGwRDxvPqr3h/cCUSNyd0hLRA9exCivQtNl7qvRVx+0k7Cde0pjHFszOcCB8UJVjKnrzgoIQItFbQwbspcfcpd4VRjL4VHN9RoDYoPJtwfekkwA/I9gFX3NJ52wsfZJyULIWV7ZSGc4CpHSpRv"));
        Log.d("laolin", pzetpkg.etol("sVTMgY/XwQFvgzi/s6saNhaMgGbGiPYzRXmM49Huve8="));
        Log.d("laolin", pzetpkg.zppskyagv("application/json; charset=utf-8"));
    }


    private String getIp() {
        String ip = null;

        return TextUtils.isEmpty(ip) ? "" : ip;
    }
}
