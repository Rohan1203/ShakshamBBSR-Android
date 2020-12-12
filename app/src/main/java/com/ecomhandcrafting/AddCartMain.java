package com.ecomhandcrafting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Preeti-098
 */
public class AddCartMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void sendmessage (View view)
    {
        Intent intent= new Intent(this , AddCartMain2.class);
        startActivity(intent);
    }
}
