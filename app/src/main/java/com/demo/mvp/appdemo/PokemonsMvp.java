package com.demo.mvp.appdemo;

import com.demo.mvp.appdemo.data.models.Pokemon;

import java.util.List;

public interface PokemonsMvp {
    interface View {
        void showPokemons(List<Pokemon> pokemons);
        void showPokemonsError(String msg);
    }

    interface Presenter {
        void loadPokemons(boolean reload);
    }
}
