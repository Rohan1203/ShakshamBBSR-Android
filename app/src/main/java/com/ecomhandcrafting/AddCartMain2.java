package com.ecomhandcrafting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCartMain2 extends AppCompatActivity {
    private TextView textViewResult;
    private EditText et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.AddCart2);
        Intent intent = getIntent();

        textViewResult=findViewById(R.id.text_view_result);
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.43.144:8080/shaksham/").addConverterFactory(GsonConverterFactory.create()).build();
        addCartApi addCartApi=retrofit.create(addCartApi.class);
        Call<List<Cart>> call=addCartApi.allProduct(int customerId);

        call.enqueue(new Callback<List<Cart>>() {
            @Override

            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Error Code: "+response.code());
                    return;
                }
                List<Cart> allproduct = response.body();
                for(Cart cart: allproduct) {
                  //  Cart b;
                    textViewResult.append("Id: " + cart.getIdCart() + "\n");
                    textViewResult.append("ProductId: " + cart.getProductId() + "\n\n");
                    textViewResult.append("CustomerId: " +cart.getCustomerId() + "\n\n\n");
                    textViewResult.append("Author: " + cart.getProductQuantity() + "\n\n\n");
                    textViewResult.append("Author: " + cart.getProductDetails() + "\n\n\n");
                    textViewResult.append("Author: " + cart.getDate() + "\n\n\n");
                }
            }
            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }


}



