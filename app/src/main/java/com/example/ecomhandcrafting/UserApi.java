package com.example.ecomhandcrafting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {
    //login with fingerprint string
    @POST("user")
    Call<User> validateUser(@Query ("username") String username, @Query ("password") String password);

}
