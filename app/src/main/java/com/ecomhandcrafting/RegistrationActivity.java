package com.ecomhandcrafting;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.ecomhandcrafting.HomePage.HomePage;
import com.ecomhandcrafting.model.User;
import com.ecomhandcrafting.model.UserApi;
import com.ecomhandcrafting.model.UserRequest;
import com.ecomhandcrafting.model.UserResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegistrationActivity extends AppCompatActivity {

    EditText et_email, et_password, et_confirm_password;
    Button btn_signup;
    TextView tv_verifyotp, tv_login;
    final Context context = this;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        tv_verifyotp = findViewById(R.id.tv_verify_otp);
        tv_login = findViewById(R.id.tv_login);
        btn_signup = findViewById(R.id.btn_signup);

        btn_signup.setEnabled(false);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btn_signup.setEnabled(true);
//                if(et_email.getText().toString().isEmpty()){
//                    et_email.setError ("Please provide an email ");
//                    et_email.requestFocus ();
//                } else if (et_email.getText ().toString ().equals (emailPattern)) {
//                    et_email.setError ("Please enter a valid email ");
//                    et_email.requestFocus ();
//                } else if (et_password.getText ().toString ().isEmpty()) {
//                    et_password.setError ("Please provide a password");
//                    et_password.requestFocus ();
//                } else if(et_confirm_password.getText().toString().isEmpty()) {
//                    et_confirm_password.setError ("Please confirm your password");
//                    et_confirm_password.requestFocus ();
//                } else if(!et_confirm_password.getText().toString().equals(et_password.getText().toString())){
//                    et_confirm_password.setError ("Please provide the same password");
//                    et_confirm_password.requestFocus ();
//                } else {
                    verifyEmailStatus();
                //}
            }
        });

        tv_verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

               firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(context, "Verification Link Successfully Sent", Toast.LENGTH_LONG).show();
                                                btn_signup.setEnabled(true);
                                            } else {
                                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void verifyEmailStatus() {
        //for checking of verify status
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String emailStatus = String.valueOf(user.isEmailVerified());
        FirebaseAuth.getInstance().getCurrentUser()
                .reload()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, emailStatus, Toast.LENGTH_LONG).show();
                    }
                });

        if (emailStatus == "true") {
            //sucess of verification
            //Toast.makeText(context, "Email Is Verified, Proceed Further", Toast.LENGTH_SHORT).show();
            progressDialog = new ProgressDialog (RegistrationActivity.this);
            progressDialog.setMax (100);
            progressDialog.setIndeterminate (true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage ("Signing up..");
            progressDialog.setInverseBackgroundForced (true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            registerUser();
        } else {
            //failure of verification
            Toast.makeText(context, "Verify Your Email First", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser(){

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

        UserApi registerApi = retrofit.create (UserApi.class);

        final String userName = et_email.getText ().toString ();
        String passWord = et_password.getText ().toString ();

        UserRequest newUserRequest = new UserRequest(userName, passWord, "customer");

        Call<UserResponse> call = registerApi.registerUser (newUserRequest);

        call.enqueue (new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                if(!response.isSuccessful ()) {
//                    progressDialog.cancel();
//                    Toast.makeText (getApplicationContext (), "Please Enter Valid Credentials :" + response.code (), Toast.LENGTH_LONG).show ();
//                    return;
//                }
                UserResponse message = response.body ();

                if(!(message.getSatusCode() == "200")) {
                    // Toast.makeText (MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show ();
                    startActivity(new Intent(getApplicationContext(), HomePage.class));

                    Log.d("MainActivity", String.valueOf(message));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.cancel ();
                Toast.makeText (getApplicationContext (), t.getMessage (), Toast.LENGTH_LONG).show ();
            }
        });
    }
}