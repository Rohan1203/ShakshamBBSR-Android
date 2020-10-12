package com.ecomhandcrafting;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.ecomhandcrafting.HomePage.HomePage;

import com.ecomhandcrafting.model.User;
import com.ecomhandcrafting.model.UserApi;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    int RC_SIGN_IN = 0;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;
    EditText et_username, et_password;
    TextView sign_up;
    Button btn_login;
    TextView textViewTimer;
    ProgressBar progressBar;
    int attempt_counter = 3 ;
    public int counter;
    static final int PERMISION_READ_STATE = 123;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    private int loginAttempts = 3;
    String verifiedEmail;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        et_username = findViewById (R.id.et_username);
        et_password = findViewById (R.id.et_password);
        btn_login = findViewById (R.id.btn_login);
        sign_up = findViewById (R.id.tv_signup);
        textViewTimer = findViewById (R.id.tv_timer);

        progressBar = findViewById (R.id.progressBarMainActivity);

        signInButton = findViewById(R.id.sign_in_button);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        btn_login.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                if(et_username.getText().toString().isEmpty()){
                    et_username.setError ("Please provide an email ");
                    et_username.requestFocus ();
                } else if (et_username.getText ().toString ().equals (emailPattern)) {
                    et_username.setError("Please enter a valid email ");
                    et_username.requestFocus();
                } else if(et_password.getText().toString().isEmpty()){
                    et_password.setError("Please provide a password");
                    et_password.requestFocus();
                } else {
                    progressDialog = new ProgressDialog (MainActivity.this);
                    progressDialog.setMax (100);
                    progressDialog.setIndeterminate (true);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage ("Loging you in...");
                    progressDialog.setInverseBackgroundForced (true);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    validateUser();
//                Intent intent = new Intent (getApplicationContext (), HomePage.class);
//                startActivity (intent);
                }
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
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
            // Signed in successfully, show authenticated UI.
//            Intent intent = new Intent(MainActivity.this, ValidateUsernameMiddleware.class);
//            intent.putExtra("email", account.getEmail());
            startActivity(new Intent(getApplicationContext(), HomePage.class));

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            startActivity(new Intent(MainActivity.this, HomePage.class));
        }
        super.onStart();
    }

    private void validateUser() {
//        OkHttpClient okHttpClient = new OkHttpClient ().newBuilder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
//                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl (getString(R.string.endpoint))
                .addConverterFactory (GsonConverterFactory.create())
//                .client(okHttpClient)
                .build ();

        UserApi loginApi = retrofit.create (UserApi.class);

        final String userName = et_username.getText ().toString ();
        String passWord = et_password.getText ().toString ();

        Call<User> call = loginApi.validateUser (userName, passWord);

        call.enqueue (new Callback<User> () {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(!response.isSuccessful ()) {
                    progressDialog.cancel();
                    Toast.makeText (getApplicationContext (), "Please Enter Valid Credentials :" + response.code (), Toast.LENGTH_LONG).show ();
                    return;
                }
                User message = response.body ();
                Toast.makeText(MainActivity.this, message.getToken(), Toast.LENGTH_SHORT).show();
                if(!(message.getToken() == null)) {
                    // Toast.makeText (MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show ();
                    Intent intent = new Intent (getApplicationContext (), HomePage.class);
                    intent.putExtra ("username", userName);
                    intent.putExtra ("token", message.getToken ());
                    startActivity (intent);
                    Log.d("MainActivity", message.getToken ());
                }
                else if(message.getPassword () == null){
                    progressDialog.cancel();
                    attempt_counter--;
                    textViewTimer.setTextColor (Color.RED);
                    textViewTimer.setText(String.valueOf("Attempt left : " + attempt_counter));

                    if (attempt_counter == 0) {
                        btn_login.setEnabled(false);
                        counter = 30;
                        new CountDownTimer(30000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                btn_login.setEnabled(false);
                                textViewTimer.setTextColor (Color.rgb (255,157,0));
                                textViewTimer.setText(("You can login in - " + counter));
                                counter--;
                            }

                            public void onFinish() {
                                btn_login.setEnabled(true);
                                attempt_counter = 3;
                                textViewTimer.setTextColor (Color.BLACK);
                                textViewTimer.setText("You Can Login Now");
                            }
                        }.start();
                    }
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.cancel ();
                Toast.makeText (getApplicationContext (), "Server not responding", Toast.LENGTH_LONG).show ();
            }
        });
    }
}
