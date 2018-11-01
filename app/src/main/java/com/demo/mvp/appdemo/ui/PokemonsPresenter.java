package com.demo.mvp.appdemo.ui;

import com.demo.mvp.appdemo.PokemonsMvp;
import com.demo.mvp.appdemo.data.datasource.PokemonsRepository;
import com.demo.mvp.appdemo.data.models.Pokemon;
import com.demo.mvp.appdemo.utils.PagingPokemonCriteria;
import com.demo.mvp.appdemo.utils.PokemonCriteria;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class PokemonsPresenter  implements PokemonsMvp.Presenter {
    private final PokemonsRepository mPokemonsRepository;
    private final PokemonsMvp.View mPokemonsView;

    public static final int PRODUCTS_LIMIT = 20;

    private boolean isFirstLoad = true;
    private int mCurrentPage = 1;

    public PokemonsPresenter(PokemonsRepository pokemonsRepository,
                             PokemonsMvp.View pokemonsView) {
        mPokemonsRepository = checkNotNull(pokemonsRepository);
        mPokemonsView = checkNotNull(pokemonsView);
    }

    @Override
    public void loadPokemons(final boolean reload) {
        final boolean reallyReload = reload || isFirstLoad;

        if (reallyReload) {
            mPokemonsView.showLoadingState(true);
            mPokemonsRepository.refreshPokemons();
            mCurrentPage = 1;
        } else {
            mPokemonsView.showLoadMoreIndicator(true);
            mCurrentPage++;
        }

        // Ahora, preparamos el criterio de paginación
        PokemonCriteria criteria = new PagingPokemonCriteria(mCurrentPage, PRODUCTS_LIMIT);

        mPokemonsRepository.getPokemons(
                new PokemonsRepository.GetPokemonsCallback() {
                    @Override
                    public void onPokemonsLoaded(List<Pokemon> pokemons) {
                        mPokemonsView.showLoadingState(false);
                        processPokemons(pokemons, reallyReload);

                        // Ahora si, ya no es el primer carga
                        isFirstLoad = false;
                    }

                    @Override
                    public void onDataNotAvailable(String error) {
                        mPokemonsView.showLoadingState(false);
                        mPokemonsView.showLoadMoreIndicator(false);
                        mPokemonsView.showPokemonsError(error);
                    }
                },
                criteria);

    }

    private void processPokemons(List<Pokemon> pokemons, boolean reload) {
        if (pokemons.isEmpty()) {
            if (reload) {
                mPokemonsView.showEmptyState();
            } else {
                mPokemonsView.showLoadMoreIndicator(false);
            }
            mPokemonsView.allowMoreData(false);
        } else {
            if (reload) {
                mPokemonsView.showPokemons(pokemons);
            } else {
                mPokemonsView.showLoadMoreIndicator(false);
                mPokemonsView.showPokemonsPage(pokemons);
            }

            mPokemonsView.allowMoreData(true);
        }
    }
}
