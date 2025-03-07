package con.fire.android2023demo.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.meituan.android.walle.ChannelInfo;
//import com.meituan.android.walle.WalleChannelReader;

import java.util.Map;

import con.fire.android2023demo.databinding.ActivityReferrerBinding;

public class ReferrerActivity extends AppCompatActivity {
    ActivityReferrerBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReferrerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getmeiChannel();
    }

    private void getmeiChannel() {
//        ChannelInfo channelInfo = WalleChannelReader.getChannelInfo(this.getApplicationContext());
//        if (channelInfo != null) {
//            String channel = channelInfo.getChannel();
//            Map<String, String> extraInfo = channelInfo.getExtraInfo();
//            StringBuilder builder = new StringBuilder();
//            builder.append("channel:   " + channel);
//            if (extraInfo != null) {
//                for (Map.Entry entry : extraInfo.entrySet()) {
//                    builder.append("\n");
//                    builder.append(entry.getKey() + ":" + entry.getValue());
//                }
//            }
//
//            binding.textMeituanchannel.setText(builder.toString());
//        }
// 或者也可以直接根据key获取
//        String value = WalleChannelReader.get(context, "buildtime");
    }
}
