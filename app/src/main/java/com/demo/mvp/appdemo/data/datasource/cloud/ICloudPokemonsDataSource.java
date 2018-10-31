package com.demo.mvp.appdemo.data.datasource.cloud;

import com.demo.mvp.appdemo.data.models.Pokemon;
import com.demo.mvp.appdemo.utils.PokemonCriteria;

import java.util.List;

/**
 * Interfaz de comunicación con el repositorio para la fuente de datos remota
 */
public interface ICloudPokemonsDataSource {
    interface PokemonServiceCallback {
        void onLoaded(List<Pokemon> pokemons);
        void onError(String error);
    }

    void getPokemons(PokemonServiceCallback callback, PokemonCriteria criteria);

}