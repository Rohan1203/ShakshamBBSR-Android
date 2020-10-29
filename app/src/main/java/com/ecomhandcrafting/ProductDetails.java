package com.ecomhandcrafting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecomhandcrafting.HomePage.HomePage;
import com.ecomhandcrafting.model.Imag;
import com.ecomhandcrafting.model.UserApi;
import com.ecomhandcrafting.model.UserRequest;
import com.ecomhandcrafting.model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetails extends AppCompatActivity {

    String fileName = "download.jpg";
    private static final String TAG = "Product Image";
    ImageView productImage;
    boolean isImageFitToScreen;
    TextView shgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productImage = findViewById(R.id.imv_product_image);
        shgName = findViewById(R.id.tv_product_shgname);


        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

    }

    private void getImage(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl (getString(R.string.endpoint))
                .addConverterFactory (GsonConverterFactory.create())
                .build ();

        UserApi registerApi = retrofit.create (UserApi.class);

        Call<Imag> call = registerApi.getImage ();
//        Log.d("ProductDetails", fileName+"================");

        call.enqueue (new Callback<Imag>() {
            @Override
            public void onResponse(Call<Imag> call, Response<Imag> response) {
                Imag message = response.body ();

//                AlertDialog alertDialog = new Builder(ProductDetails.this).create();
//                alertDialog.setMessage(message.getPicByte());
//                alertDialog.show();

                Log.d("ProductDetails", "=============Working============" + message.getPicByte());
                byte[] imageBytes = Base64.decode(message.getPicByte(), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                productImage.setImageBitmap(decodedImage);

            }

            @Override
            public void onFailure(Call<Imag> call, Throwable t) {
                Toast.makeText (getApplicationContext (), t.getMessage (), Toast.LENGTH_LONG).show ();
            }
        });
    }
}