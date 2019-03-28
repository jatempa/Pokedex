package com.demo.mvp.appdemo.data.datasource;

import com.demo.mvp.appdemo.data.models.Pokemon;

import java.util.List;

/**
 * Repositorio de pokemons
 */
public interface IPokemonsRepository {
    interface GetPokemonsCallback {
        void onPokemonsLoaded(List<Pokemon> pokemons);
        void onDataNotAvailable(String error);
    }

    void getPokemons(GetPokemonsCallback callback);
}
