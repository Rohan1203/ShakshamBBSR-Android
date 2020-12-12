package com.ecomhandcrafting.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApi {

    @GET("allcategory")
    Call<List<Category>> getAll();
}
