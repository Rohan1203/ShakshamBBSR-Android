package com.ecomhandcrafting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ecomhandcrafting.HomePage.HomePage;
import com.ecomhandcrafting.model.User;
import com.ecomhandcrafting.model.UserApi;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ValidateUsernameMiddleware extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    String verifiedEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_username_middlewaare);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(ValidateUsernameMiddleware.this);

//        Intent intent = getIntent();
//        intent.getStringExtra("email");
          //String email = acct.getEmail();

        //checking the email with our database
          //  checkUsername(email);

//            if(email.equals(verifiedEmail)) {
//                //startActivity(new Intent(getApplicationContext(), HomePage.class));
//            } else {
//                Toast.makeText(ValidateUsernameMiddleware.this, "New to Shaksham, Register", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
//            }
    }

    private void checkUsername(final String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl (getString(R.string.endpoint))
                .addConverterFactory (GsonConverterFactory.create())
                .build ();
        UserApi userApi = retrofit.create (UserApi.class);
        Call<User> call = userApi.getUsername (email);
        call.enqueue (new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User message = response.body ();
                verifiedEmail = message.getUsername();
                Toast.makeText(ValidateUsernameMiddleware.this, verifiedEmail, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText (getApplicationContext (), t.getMessage (), Toast.LENGTH_LONG).show ();
            }
        });
    }
}