package com.demo.mvp.appdemo.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.demo.mvp.appdemo.R;
import com.demo.mvp.appdemo.data.models.Pokemon;
import com.demo.mvp.appdemo.data.models.PokemonResponse;
import com.demo.mvp.appdemo.data.network.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "POKEDEX";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private int offset = 0;
    private final int MAX = 20;
    private boolean readyToLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycler_view);
        adapter = new PokemonAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (readyToLoad) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, "Llegamos al final.");

                            readyToLoad = false;
                            offset += MAX;
                            fetchData(offset);
                        }
                    }
                }
            }
        });

        // Access to API
        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fetchData(offset);
    }

    private void fetchData(int offset) {
        ApiService service = retrofit.create(ApiService.class);
        Call<PokemonResponse> responseCall = service.fetchPokemons(MAX, offset);

        responseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                readyToLoad = true;
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    ArrayList<Pokemon> pokemons = response.body().getResults();

                    adapter.addPokemonList(pokemons);
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                readyToLoad = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
}
