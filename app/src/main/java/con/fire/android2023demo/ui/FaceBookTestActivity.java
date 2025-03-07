package con.fire.android2023demo.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

import con.fire.android2023demo.databinding.ActivityFacebooktestBinding;
import okhttp3.Response;

/**
 * https://developers.facebook.com/docs/pages-api/overview?locale=zh_CN
 */

public class FaceBookTestActivity extends AppCompatActivity {
    private static final String EMAIL = "email";

    ActivityFacebooktestBinding binding;
    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFacebooktestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(FaceBookTestActivity.this, "onSuccess  " + loginResult.toString(), Toast.LENGTH_SHORT).show();
                // App code
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(FaceBookTestActivity.this, "onCancel  ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(FaceBookTestActivity.this, "onError  " + exception.toString(), Toast.LENGTH_SHORT).show();

                // App code
            }
        });

        binding.loginButton.setPermissions(Arrays.asList(EMAIL));
//        binding.loginButton.setPermissions(Arrays.asList("pages_show_list"));


        binding.loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                Log.d("okhttp", "====initfacebookLogin====0==" + loginResult.getAccessToken().getToken());
                Log.d("okhttp", "====initfacebookLogin====1==" + loginResult.getAccessToken().getApplicationId());
                Log.d("okhttp", "====initfacebookLogin====2==" + loginResult.getAccessToken().getUserId());

                onfacebookResult(FaceBookTestActivity.this, loginResult);


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }


    private void onfacebookResult2(Context context, LoginResult loginResult) {
//        https://developers.facebook.com/docs/permissions/reference/
//        LoginManager.getInstance().logInWithReadPermissions(LancherAtivity.this, Arrays.asList("public_profile,email,user_birthday,user_gender"));


        LoginManager.getInstance().logInWithReadPermissions(FaceBookTestActivity.this, Arrays.asList("pages_show_list"));

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                if (response.getError() != null) {
                    // 处理错误
                    Log.d("okhttp", response.getError().getErrorType() + "error" + response.getError().getErrorMessage());
                    return;
                }
//                if (object == null) {
//                    mPresenter.loginThirdPlat(2, 			                  loginResult.getAccessToken().getUserId(), nation, information.email);
//                    return;
//                }

                StringBuilder builder = new StringBuilder();

                String id = object.optString("id");
                String name = object.optString("name");
                builder.append("\nname:" + name);

                String gender = object.optString("gender");
                builder.append("\ngender:" + gender);

                String email = object.optString("email");
                builder.append("\nemail:" + email);

                String locale = object.optString("locale");
                builder.append("\nlocale:" + locale);

                //获取用户头像
                JSONObject object_pic = object.optJSONObject("picture");
                builder.append("\nobject_pic:" + object_pic.toString());

                JSONObject object_data = object_pic.optJSONObject("data");
                builder.append("\nobject_data:" + object_data.toString());

                String photo = object_data.optString("url");
                Log.d("okhttp", "" + photo);
                builder.append("\nphoto:" + photo.toString());
//                Log.d("okhttp", "initfacebookLogin=====5" + builder.toString());

                Log.d("okhttp", "initfacebookLogin=====6" + object.toString());


                try {
                    // 获取用户的所有页面列表
                    JSONArray pages = object.getJSONArray("data");
                    for (int i = 0; i < pages.length(); i++) {
                        JSONObject page = pages.getJSONObject(i);
                        String pageId = page.getString("id");
                        // 这里可以获取到公共主页的ID
                        Log.d("FacebookPageId", pageId);
                    }
                } catch (Exception e) {
                    // 处理异常
                }


//                getFacebookUserPictureAsync(id, new FaceUserImgCallBack() {
//                    @Override
//                    public void onFailed(String msg) {
//                        LogUtil.v("fb", msg）
//                    }
//
//                    @Override
//                    public void onCompleted(String url) {
//                        LogUtil.v("fb", url);
//                    }
//                });
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,category,website");

        request.setParameters(parameters);
        request.executeAsync();
    }


    private void onfacebookResult(Context context, LoginResult loginResult) {
//        https://developers.facebook.com/docs/permissions/reference/
//        LoginManager.getInstance().logInWithReadPermissions(LancherAtivity.this, Arrays.asList("public_profile,email,user_birthday,user_gender"));


        LoginManager.getInstance().logInWithReadPermissions(FaceBookTestActivity.this, Arrays.asList("public_profile"));


        AccessToken accessToken = loginResult.getAccessToken();
        String token = accessToken.getToken();
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
//                if (object == null) {
//                    mPresenter.loginThirdPlat(2, 			                  loginResult.getAccessToken().getUserId(), nation, information.email);
//                    return;
//                }

                StringBuilder builder = new StringBuilder();


                String id = object.optString("id");

                String url = "https://graph.facebook.com/" + id + "/accounts?access_token=" + token;

                requestAuth(url);

                String name = object.optString("name");
                builder.append("\nname:" + name);

                String gender = object.optString("gender");
                builder.append("\ngender:" + gender);

                String email = object.optString("email");
                builder.append("\nemail:" + email);

                String locale = object.optString("locale");
                builder.append("\nlocale:" + locale);

                //获取用户头像
                JSONObject object_pic = object.optJSONObject("picture");
                builder.append("\nobject_pic:" + object_pic.toString());

                JSONObject object_data = object_pic.optJSONObject("data");
                builder.append("\nobject_data:" + object_data.toString());

                String photo = object_data.optString("url");
                Log.d("okhttp", "" + photo);
                builder.append("\nphoto:" + photo.toString());
//                Log.d("okhttp", "initfacebookLogin=====5" + builder.toString());

                Log.d("okhttp", "initfacebookLogin=====6" + object.toString());


                try {
                    // 获取用户的所有页面列表
                    JSONArray pages = object.getJSONArray("data");
                    for (int i = 0; i < pages.length(); i++) {
                        JSONObject page = pages.getJSONObject(i);
                        String pageId = page.getString("id");
                        // 这里可以获取到公共主页的ID
                        Log.d("FacebookPageId", pageId);
                    }
                } catch (Exception e) {
                    // 处理异常
                }


//                getFacebookUserPictureAsync(id, new FaceUserImgCallBack() {
//                    @Override
//                    public void onFailed(String msg) {
//                        LogUtil.v("fb", msg）
//                    }
//
//                    @Override
//                    public void onCompleted(String url) {
//                        LogUtil.v("fb", url);
//                    }
//                });
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,link,gender,birthday,picture,locale,updated_time,timezone,age_range,first_name,last_name");

        request.setParameters(parameters);
        request.executeAsync();
    }


    public void requestAuth(String url) {
        Log.d("okhttp", "========requestAuth==1===");

        Log.d("okhttp", "" + url);

        try {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
//                        Response execute = OkGo.get(url).execute();
//
//                        Log.d("okhttp", execute.body().string());

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//import com.facebook.AccessToken;
//        import com.facebook.GraphRequest;
//        import com.facebook.GraphResponse;
//
//// 获取当前用户的访问令牌
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//
//// 构建请求来获取用户的公共主页信息
//        GraphRequest request = GraphRequest.newMeRequest(
//        accessToken,
//        new GraphRequest.GraphJSONObjectCallback() {
//@Override
//public void onCompleted(JSONObject object, GraphResponse response) {
//        // 确保请求成功
//        if (response.getError() != null) {
//        // 处理错误
//        return;
//        }
//
//        try {
//        // 获取用户的所有页面列表
//        JSONArray pages = object.getJSONArray("data");
//        for (int i = 0; i < pages.length(); i++) {
//        JSONObject page = pages.getJSONObject(i);
//        String pageId = page.getString("id");
//        // 这里可以获取到公共主页的ID
//        Log.d("FacebookPageId", pageId);
//        }
//        } catch (JSONException e) {
//        // 处理异常
//        }
//        }
//        });
//
//// 设置请求参数，只获取公共主页
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,category,website");
//        request.setParameters(parameters);
//
//// 执行请求
//        request.executeAsync();

