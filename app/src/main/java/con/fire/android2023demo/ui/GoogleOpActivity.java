package con.fire.android2023demo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
//import com.lzy.okgo.OkGo;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import con.fire.android2023demo.R;
import con.fire.android2023demo.people.gms.auth.GoogleAccountCredential;
import okhttp3.Response;

public class GoogleOpActivity extends AppCompatActivity {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String PREF_ACCOUNT_NAME = "accountName";
    // [END declare_auth]
    // [START declare_auth]
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googleop);
        button = findViewById(R.id.button);
//        Intent intent = getIntent();
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onNext();
//            }
//        });
//
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).
//                requestEmail().requestScopes(new Scope("https://www.googleapis.com/auth/contacts")).build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        // [END config_signin]
//
//        // [START initialize_auth]
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }

    }
    // [END onactivityresult]

    public void onGoogleLogin(View view) {
        requestAuth();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    // [END auth_with_google]

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [END signin]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    updateUI(null);
                }
            }
        });
    }

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(FirebaseUser user) {
        try {
            String name = user.getDisplayName();
            String email = user.getEmail();

            Log.d(TAG, "name====" + name);
            Log.d(TAG, "email====" + email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onNext() {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.ludo.king&hl=zh&gl=US&inline=true&auao=true&enifd=ANAkzTBcOEiiRA5dIG5xHYWiCHR7Vl15hudPR99QNyp5M69tjJj_fbsLpdWRF-msElvS96_72rcs7wTxltUQ7BJqvLL_-xjlFZQffO_QiKLJ8zWgoRwb_AI15l_wlOrYB9qpSw%3D%3D"));
        intent.setPackage("com.android.vending");
        startActivity(intent);
    }

    public void requestAuth() {
        Log.d("okhttp", "==========1===");


        String url = "https://accounts.google.com/o/oauth2/v2/auth?" + " scope=https://www.googleapis.com/auth/contacts" + " response_type=code&" + " state=security_token%3D138r5719ru3e1%26url%3Dhttps%3A%2F%2Foauth2.example.com%2Ftoken&" + " redirect_uri=com.example.app%3A/oauth2redirect&" + " client_id=client_id";

        try {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
//                        Response execute = OkGo.get("https://www.baidu.com").execute();
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

    public void onPeopleView(View view) {
        String TAG = "onPeopleView";
        try {
//            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//            final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//            GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(this, Collections.singleton(TasksScopes.TASKS));
//            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
//            credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
//            // Tasks client
//            Tasks service = new com.google.api.services.tasks.Tasks.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
//                    .setApplicationName("Google-TasksAndroidSample/1.0").build();
//



//            Log.d(TAG, "========0" + service.tasks());
//            PeopleService service = buildPeopleService();
//
//            Person profile = service.people().get("people/me").setPersonFields("names,emailAddresses").execute();
//
//            Logss.log(TAG, profile);


//            ListConnectionsResponse response = service.people().connections().list("people/me").setPageSize(10).setPersonFields("names,emailAddresses,phoneNumbers").execute();
//
//            List<Person> connections = response.getConnections();
//            if (connections != null && connections.size() > 0) {
//                for (Person person : connections) {
//
//                    Gson gson = new Gson();
//                    System.out.println("Person: " + gson.toJson(person));
//
//                    Log.d("onPeopleView", "Person: " + gson.toJson(person));
//
//
//                    List<Name> names = person.getNames();
//                    if (names != null && names.size() > 0) {
//                        System.out.println("Name: " + person.getNames().get(0).getDisplayName());
//                    } else {
//                        System.out.println("No names available for connection.");
//                    }
//                }
//            } else {
//                System.out.println("No connections found.");
//            }


            Log.d("onPeopleView", "========1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PeopleService buildPeopleService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(this, Collections.singleton("https://www.googleapis.com/auth/contacts"));
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        credential.setSelectedAccountName(settings.getString("accountName", "accountName"));


        // Tasks client
        return new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName("Google-TasksAndroidSample/1.0").build();

    }
}
