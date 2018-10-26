package com.demo.mvp.appdemo.data.network;

import com.demo.mvp.appdemo.data.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("pokemon")
    Call<PokemonResponse> fetchPokemons(@Query("limit") int limit, @Query("offset") int offset);
}
