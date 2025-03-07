package con.fire.android2023demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import con.fire.android2023demo.people.gms.auth.GoogleAccountCredential;

public class PeopleApi {
    static final String TAG = "PeopleApi";

    private static final String APPLICATION_NAME = "Google People API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(PeopleServiceScopes.CONTACTS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "./credentials.json";
    public Activity activity;

    public PeopleApi(Activity activity) {
        this.activity = activity;
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
//        InputStream in = PeopleApi.class.getResourceAsStream(CREDENTIALS_FILE_PATH);


        InputStream in = activity.getAssets().open("credentials.json");

        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        File tokenDir = new File(activity.getExternalCacheDir().getAbsolutePath() + "/" + TOKENS_DIRECTORY_PATH);
        if (!tokenDir.exists()) {
            tokenDir.mkdirs();
        }
        Log.d(TAG, "==========tokenDir=======1" + tokenDir.getAbsolutePath());


        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES).setDataStoreFactory(new FileDataStoreFactory(tokenDir)).setAccessType("offline").build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }


    public void startLocal() {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        Log.d(TAG, "=================1");

        try {
            GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(activity, Collections.singleton(TasksScopes.TASKS));

//            SharedPreferences settings = activity.getPreferences(Context.MODE_PRIVATE);
//            credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            // Tasks client
//            Tasks service = new Tasks.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName("Google-TasksAndroidSample/1.0").build();

            PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
            // Request 10 connections.
            ListConnectionsResponse response = service.people().connections().list("people/me").setPageSize(10).setPersonFields("names,emailAddresses,phoneNumbers").execute();
            Log.d(TAG, "=================2");

            // Print display name of connections if available.
            List<Person> connections = response.getConnections();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "=================3===="+e.getMessage());


        }
    }

    public void start() {

        try {

            Log.d(TAG, "=================1");

            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();

            // Request 10 connections.
            ListConnectionsResponse response = service.people().connections().list("people/me").setPageSize(10).setPersonFields("names,emailAddresses,phoneNumbers").execute();
            Log.d(TAG, "=================2");

            // Print display name of connections if available.
            List<Person> connections = response.getConnections();
            if (connections != null && connections.size() > 0) {
                for (Person person : connections) {

                    Gson gson = new Gson();
                    System.out.println("Person: " + gson.toJson(person));

                    List<Name> names = person.getNames();
                    if (names != null && names.size() > 0) {
                        System.out.println("Name: " + person.getNames().get(0).getDisplayName());
                    } else {
                        System.out.println("No names available for connection.");
                    }
                }
            } else {
                Log.d(TAG, "=================4");

                System.out.println("No connections found.");
            }
        } catch (Exception e) {
            Log.d(TAG, "=================3" + e.getMessage());

            e.printStackTrace();
        }
    }


}
