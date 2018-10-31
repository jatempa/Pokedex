package com.demo.mvp.appdemo.data.datasource.memory;

import com.demo.mvp.appdemo.data.models.Pokemon;
import com.demo.mvp.appdemo.utils.PokemonCriteria;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Implementación concreta de la fuente de datos en memoria
 */
public class MemoryPokemonsDataSource implements IMemoryPokemonsDataSource {
    private static HashMap<Integer, Pokemon> mCachedPokemons;

    @Override
    public List<Pokemon> find(PokemonCriteria criteria) {

        ArrayList<Pokemon> pokemons =
                Lists.newArrayList(mCachedPokemons.values());
        return criteria.match(pokemons);
    }

    @Override
    public void save(Pokemon pokemon) {
        if (mCachedPokemons == null) {
            mCachedPokemons = new LinkedHashMap<>();
        }
        mCachedPokemons.put(pokemon.getNumber(), pokemon);
    }


    @Override
    public void deleteAll() {
        if (mCachedPokemons == null) {
            mCachedPokemons = new LinkedHashMap<>();
        }
        mCachedPokemons.clear();
    }

    @Override
    public boolean mapIsNull() {
        return mCachedPokemons == null;
    }
}
