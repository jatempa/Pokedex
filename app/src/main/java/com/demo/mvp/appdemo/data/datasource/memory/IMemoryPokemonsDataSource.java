package com.demo.mvp.appdemo.data.datasource.memory;

import com.demo.mvp.appdemo.data.models.Pokemon;
import com.demo.mvp.appdemo.utils.PokemonCriteria;

import java.util.List;

public interface IMemoryPokemonsDataSource {
    List<Pokemon> find(PokemonCriteria criteria);
    void save(Pokemon pokemon);
    void deleteAll();
    boolean mapIsNull();
}
