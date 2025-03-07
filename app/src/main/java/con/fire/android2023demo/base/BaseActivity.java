package con.fire.android2023demo.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.App;

public class BaseActivity extends AppCompatActivity {

    public App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        app = App.getAppContext();
        super.onCreate(savedInstanceState);

    }
}
