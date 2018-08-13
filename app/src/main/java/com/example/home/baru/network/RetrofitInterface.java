package com.example.home.baru.network;

import com.example.home.baru.models.Response;
import com.example.home.baru.models.User;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/auth")
    Call<Response> userLogin(@Body RequestBody body);

    @POST("/users")
    Call<Response> userRegister(@Body User user);

    @GET("/users/me")
    Call<Response> getProfile(@Query("access_token") String access_token);
}
