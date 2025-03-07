package con.fire.android2023demo.ui;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import con.fire.android2023demo.R;
import con.fire.android2023demo.databinding.ActivityAudiomanagerBinding;

public class AudioManagerActivity extends AppCompatActivity {
    ActivityAudiomanagerBinding binding;
    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAudiomanagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mMediaPlayer = new MediaPlayer();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        initMp3();
        binding.btnPlay.setOnClickListener(view -> {
            try {
                mMediaPlayer.prepare();
                mMediaPlayer.start();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        binding.btnEar.setOnClickListener(view -> {
            mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            mAudioManager.stopBluetoothSco();
            mAudioManager.setBluetoothScoOn(false);
            mAudioManager.setSpeakerphoneOn(false);
        });
        binding.btnBlue.setOnClickListener(view -> {

        });
        binding.btnOut.setOnClickListener(view -> {
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            mAudioManager.setSpeakerphoneOn(true);
        });
        binding.btnTing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAudioManager.setSpeakerphoneOn(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                } else {
                    mAudioManager.setMode(AudioManager.MODE_IN_CALL);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    private void initMp3() {
        try {
            mMediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.output));

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
