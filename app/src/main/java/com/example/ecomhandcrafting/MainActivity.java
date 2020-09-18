package com.example.ecomhandcrafting;

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
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText et_username, et_password;
    TextView signup_link;
    Button btn_login;
    TextView textViewTimer;
    ProgressBar progressBar;

    ProgressDialog progressDialog;

    int attempt_counter = 3 ;
    public int counter;

    static final int PERMISION_READ_STATE = 123;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    private int loginAttempts = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        et_username = findViewById (R.id.et_username);
        et_password = findViewById (R.id.et_password);
        btn_login = findViewById (R.id.btn_login);
        signup_link = findViewById (R.id.tv_signup);
        textViewTimer = findViewById (R.id.tv_timer);
        progressBar = findViewById (R.id.progressBarMainActivity);





        btn_login.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                validateUser();
//                Intent intent = new Intent (getApplicationContext (), RatingActivity.class);
//                startActivity (intent);

            }
        });

    }

    private void validateUser() {
        OkHttpClient okHttpClient = new OkHttpClient ().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl ("http://localhost:8080/user")
                .addConverterFactory (GsonConverterFactory.create())
                .client(okHttpClient)
                .build ();

        UserApi loginApi = retrofit.create (UserApi.class);

        final String userName = et_username.getText ().toString ();
        String passWord = et_password.getText ().toString ();

        Call<User> call = loginApi.validateUser (userName, passWord);

        call.enqueue (new Callback<User> () {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

//                if(!response.isSuccessful ()) {
//                    Toast.makeText (getApplicationContext (), "Please Enter Valid Credentials :" + response.code (), Toast.LENGTH_LONG).show ();
//                    return;
//                }
                User message = response.body ();


                String passWord = et_username.getText ().toString ();
                String username = et_password.getText ().toString ();


                if(passWord.equals (message.getPassword ()) && username.equals (message.getUsername ())) {
                    // Toast.makeText (MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show ();

                    Intent intent = new Intent (getApplicationContext (), RatingActivity.class);
                    intent.putExtra ("username", userName);
                    intent.putExtra ("token", message.getToken ());
                    startActivity (intent);

                    Log.d("MainActivity", message.getToken ());
                }
                else if(message.getPassword () == null){

                    attempt_counter--;
                    progressDialog.cancel ();
                    textViewTimer.setTextColor (Color.RED);
                    textViewTimer.setText(String.valueOf("Attempt left : " + attempt_counter));

                    if (attempt_counter == 0) {
                        btn_login.setEnabled(false);
                        counter = 30;
                        new CountDownTimer (30000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                btn_login.setEnabled(false);
                                textViewTimer.setTextColor (Color.rgb (255,157,0));
                                textViewTimer.setText(("You can login in - " + "00:"+ counter));
                                counter--;
                            }

                            public void onFinish() {
                                btn_login.setEnabled(true);
                                attempt_counter = 3;
                                textViewTimer.setTextColor (Color.rgb (50,205,50));
                                textViewTimer.setText("You Can Login Now");
                            }
                        }.start();
                    }
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.cancel ();
                Toast.makeText (getApplicationContext (), t.getMessage (), Toast.LENGTH_LONG).show ();
            }
        });
    }
}
