package com.demo.mvp.appdemo.data.network;

import com.demo.mvp.appdemo.data.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("pokemon?offset=0&limit=150")
    Call<PokemonResponse> fetchPokemons();
}
