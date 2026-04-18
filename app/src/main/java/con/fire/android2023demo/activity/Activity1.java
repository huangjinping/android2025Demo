package con.fire.android2023demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import con.fire.android2023demo.R;

public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demospa);
        Button bttton = findViewById(R.id.btn_tton);
        bttton.setText("Activity1");

//        FirebaseAnalytics instance = FirebaseAnalytics.getInstance(context);
        

//        instance.setUserId("");
//        instance.logEvent(com.google.firebase.analytics.FirebaseAnalytics.Event.
//                ADD_SHIPPING_INFO, new Bundle());


        bttton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity1.this, Activity2.class));

            }
        });
    }
}
