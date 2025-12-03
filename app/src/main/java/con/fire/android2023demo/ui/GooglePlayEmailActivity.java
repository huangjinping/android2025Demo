package con.fire.android2023demo.ui;


//import static con.fire.android2023demo.ui.GoogleOpActivity.RC_SIGN_IN;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import con.fire.android2023demo.databinding.ActivityGoogleplayemailBinding;

public class GooglePlayEmailActivity extends AppCompatActivity {

    ActivityGoogleplayemailBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGoogleplayemailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btbEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        init();
    }

    private void init() {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .requestId()
//                .requestIdToken(getString(R.string.google_server_client_id))
//                .requestProfile()
//                .build();
//// Build a GoogleSignInClient with the options specified by gso.
//         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void startAction() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("TAG", "handleSignInResult: " + account.zad());
//            checkLogin("3",null,account);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
