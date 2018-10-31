package com.demo.mvp.appdemo.data.datasource.cloud;

import com.demo.mvp.appdemo.data.models.PokemonResponse;
import com.demo.mvp.appdemo.data.network.ApiService;
import com.demo.mvp.appdemo.utils.PokemonCriteria;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fuente de datos relacionada al servidor remoto
 */
public class CloudPokemonsDataSource implements ICloudPokemonsDataSource {
    public static final String BASE_URL = "https://pokeapi.co/api/v2/";

    private final Retrofit mRetrofit;
    private final ApiService mRestService;

    public CloudPokemonsDataSource() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRestService = mRetrofit.create(ApiService.class);
    }

    @Override
    public void getPokemons(final PokemonServiceCallback callback, PokemonCriteria criteria) {
        Call<PokemonResponse> call = mRestService.fetchPokemons();

        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                // ¿LLegaron los pokemons sanos y salvos?
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    callback.onLoaded(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
