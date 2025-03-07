package con.fire.android2023demo.ui;

import android.app.PictureInPictureParams;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Rational;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.databinding.ActivitySnippetsBinding;

public class SnippetsActivity extends AppCompatActivity {


    ActivitySnippetsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySnippetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SnippetsActivity.this, "画中画设备", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    minimize();
                }
            }
        });
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private void minimize() {
        enterPictureInPictureMode(updatePictureInPictureParams());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private PictureInPictureParams updatePictureInPictureParams() {
        Rational aspectRatio = new Rational(binding.layoutSnippets.getWidth(), binding.layoutSnippets.getHeight());
        Rect visibleRect = new Rect();
        binding.layoutSnippets.getGlobalVisibleRect(visibleRect);
        PictureInPictureParams params = new PictureInPictureParams.Builder().setAspectRatio(aspectRatio).setSourceRectHint(visibleRect).build();
        setPictureInPictureParams(params);
        return params;
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            //Hide the full-screen
            binding.layoutSnippets.setVisibility(View.VISIBLE);

        } else {
            //Restore  the  full-screen ui
            binding.layoutSnippets.setVisibility(View.INVISIBLE);

        }
    }


    @Override
    protected void onUserLeaveHint() {
//        super.onUserLeaveHint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            minimize();
        }

    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            minimize();
        }
    }
}
