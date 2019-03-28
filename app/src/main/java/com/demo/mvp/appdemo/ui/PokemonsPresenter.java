package com.demo.mvp.appdemo.ui;

import com.demo.mvp.appdemo.PokemonsMvp;
import com.demo.mvp.appdemo.data.datasource.PokemonsRepository;
import com.demo.mvp.appdemo.data.models.Pokemon;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class PokemonsPresenter  implements PokemonsMvp.Presenter {
    private final PokemonsRepository mPokemonsRepository;
    private final PokemonsMvp.View mPokemonsView;

    public PokemonsPresenter(PokemonsRepository pokemonsRepository,
                             PokemonsMvp.View pokemonsView) {
        mPokemonsRepository = checkNotNull(pokemonsRepository);
        mPokemonsView = checkNotNull(pokemonsView);
    }

    @Override
    public void loadPokemons(final boolean reload) {
        mPokemonsRepository.getPokemons(
            new PokemonsRepository.GetPokemonsCallback() {
                @Override
                public void onPokemonsLoaded(List<Pokemon> pokemons) {
                    mPokemonsView.showPokemons(pokemons);
                }

                @Override
                public void onDataNotAvailable(String error) {
                    mPokemonsView.showPokemonsError(error);
                }
            }
        );
    }
}
