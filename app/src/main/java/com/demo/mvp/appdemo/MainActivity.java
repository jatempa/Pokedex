package com.demo.mvp.appdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.demo.mvp.appdemo.models.PokemonResponse;
import com.demo.mvp.appdemo.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "APP_DEMO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Access to API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<PokemonResponse> res = service.fetchPokemons();

        res.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                Log.d(TAG, "SUCCESS " + response.body().getResults());
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.e(TAG, "ERROR");
            }
        });
    }
}
