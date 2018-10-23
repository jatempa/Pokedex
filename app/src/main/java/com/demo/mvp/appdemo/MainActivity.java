package com.demo.mvp.appdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.demo.mvp.appdemo.models.Pokemon;
import com.demo.mvp.appdemo.models.PokemonResponse;
import com.demo.mvp.appdemo.network.ApiService;

import java.util.ArrayList;

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

        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        final PokemonAdapter adapter = new PokemonAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

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
                assert response.body() != null;
                ArrayList<Pokemon> pokemons = response.body().getResults();

                adapter.addPokemonList(pokemons);
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.e(TAG, "ERROR");
            }
        });
    }
}
