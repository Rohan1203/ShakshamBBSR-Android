package com.ecomhandcrafting.model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    //login with fingerprint string
    @POST("user")
    Call<User> validateUser(@Query ("username") String username, @Query ("password") String password);

    @GET("getUsername")
    Call<User> getUsername(@Path("email") String email);

    @GET("test")
    Call<String> tokenCall( @Header("Authorization") String auth);

    @POST("register")
    Call<UserResponse> registerUser(@Body UserRequest userRequest);

    @GET("get/download.jpg")
    Call<Imag> getImage();
}
