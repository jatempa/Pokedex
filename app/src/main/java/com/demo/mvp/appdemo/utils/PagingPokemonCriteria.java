package com.demo.mvp.appdemo.utils;

import com.demo.mvp.appdemo.data.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Criterio para determinar los pokemons que pertenecen a una página específica
 */
public class PagingPokemonCriteria implements PokemonCriteria {

    private final int mPage;
    private final int mLimit;

    public PagingPokemonCriteria(int page, int limit) {
        mPage = page;
        mLimit = limit;
    }

    @Override
    public List<Pokemon> match(List<Pokemon> pokemons) {
        List<Pokemon> criteriaPokemons = new ArrayList<>();
        int a, b, size, numPages;

        // Sanidad
        if (mLimit <= 0 || mPage <= 0) {
            return criteriaPokemons;
        }

        size = pokemons.size();

        // ¿La página es más grande que el contenido?
        if (size < mLimit && mPage == 1) {
            return pokemons;
        }

        numPages = size / mLimit;


        if (mPage > numPages) {
            return criteriaPokemons;
        }


        a = (mPage - 1) * mLimit;

        // ¿Llegamos al final?
        if (a == size) {
            return criteriaPokemons;
        }

        b = a + mLimit;

        criteriaPokemons = pokemons.subList(a, b);

        return criteriaPokemons;

    }
}
