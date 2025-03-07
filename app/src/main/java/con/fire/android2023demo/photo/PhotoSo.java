package con.fire.android2023demo.photo;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class PhotoSo {
    public PhotoCallback callback;
    protected AppCompatActivity activity;



    public PhotoSo(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallback(PhotoCallback callback) {
        this.callback = callback;
    }

    public abstract void take_photo();

    public abstract void take_Album();

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);


}
