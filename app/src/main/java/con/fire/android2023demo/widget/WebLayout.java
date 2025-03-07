package con.fire.android2023demo.widget;

import android.app.Activity;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.just.agentweb.IWebLayout;

/**
 * Created by cenxiaozhong on 2017/7/1.
 * source code  https://github.com/Justson/AgentWeb
 */

public class WebLayout implements IWebLayout {

    private final LinearLayout mTwinklingRefreshLayout;
    private Activity mActivity;
    private WebView mWebView = null;

    public WebLayout(Activity activity) {
        this.mActivity = activity;

        mTwinklingRefreshLayout = new LinearLayout(activity);
        mWebView = new WebView(activity);


        mTwinklingRefreshLayout.addView(mWebView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        mTwinklingRefreshLayout = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.fragment_twk_web, null);
//        mWebView = (WebView) mTwinklingRefreshLayout.findViewById(R.id.cutwebview);


    }



    @NonNull
    @Override
    public ViewGroup getLayout() {
        return mTwinklingRefreshLayout;
    }

    @Nullable
    @Override
    public WebView getWebView() {
        return mWebView;
    }


}
