package com.example.user.playvideos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface
{
    @GET("/media.json?print=pretty")
    Call<List<UserData>> getAllData();
}
