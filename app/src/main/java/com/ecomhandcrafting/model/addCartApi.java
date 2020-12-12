package com.ecomhandcrafting.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Preeti-098
 *
 */
public interface addCartApi {
    @GET("Cart")
    Call<List<Cart>> allProduct(int customerId);

}
