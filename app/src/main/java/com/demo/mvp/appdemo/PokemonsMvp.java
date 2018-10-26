package com.demo.mvp.appdemo;

import com.demo.mvp.appdemo.models.Pokemon;

import java.util.List;

public interface PokemonsMvp {
    interface View {
        void showPokemons(List<Pokemon> products);
        void showLoadingState(boolean show);
        void showEmptyState();
        void showPokemonsError(String msg);
        void showPokemonsPage(List<Pokemon> products);
        void showLoadMoreIndicator(boolean show);
        void allowMoreData(boolean show);
    }

    interface Presenter {
        void loadPokemons(boolean reload);
    }
}
