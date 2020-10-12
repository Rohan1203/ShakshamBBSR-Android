package com.ecomhandcrafting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.ecomhandcrafting.model.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RatingActivity extends AppCompatActivity {
    RatingBar ratingbar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_rating);

        ratingbar = (RatingBar) findViewById (R.id.rb);
        button = (Button) findViewById (R.id.btn);


        ratingbar.setRating(5);

        //Performing action on Button Click
        button.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                String rating = String.valueOf (ratingbar.getRating ());
                Toast.makeText (getApplicationContext (), rating, Toast.LENGTH_LONG).show ();

                tokenCall();


            }
        });
    }

    private void tokenCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl ("http://172.20.10.2:8080/")

                .addConverterFactory(ScalarsConverterFactory.create())
//                .client(okHttpClient)
                .build ();

        UserApi loginApi = retrofit.create (UserApi.class);

        Intent intent = getIntent();
        String token =  intent.getStringExtra("token");
        Log.d("MainActivity", "============" + token);
        Call<String> call = loginApi.tokenCall (token);

        call.enqueue (new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String message = response.body ();

                Toast.makeText(RatingActivity.this, message, Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                progressDialog.cancel ();
                Toast.makeText (getApplicationContext (), t.getLocalizedMessage (), Toast.LENGTH_LONG).show ();
            }
        });
    }
}
