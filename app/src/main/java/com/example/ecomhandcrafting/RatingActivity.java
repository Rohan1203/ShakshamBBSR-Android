package com.example.ecomhandcrafting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class RatingActivity extends AppCompatActivity {
    RatingBar ratingbar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_rating);

        ratingbar = (RatingBar) findViewById (R.id.rb);
        button = (Button) findViewById (R.id.btn);
        //Performing action on Button Click
        button.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                String rating = String.valueOf (ratingbar.getRating ());
                Toast.makeText (getApplicationContext (), rating, Toast.LENGTH_LONG).show ();
            }
        });
    }
}
