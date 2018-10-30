package com.demo.mvp.appdemo.data.datasource.memory;

import com.demo.mvp.appdemo.data.models.Pokemon;

import java.util.List;

public interface IMemoryPokemonsDataSource {
    List<Pokemon> find();
    void save(Pokemon pokemon);
    void deleteAll();
    boolean mapIsNull();
}
