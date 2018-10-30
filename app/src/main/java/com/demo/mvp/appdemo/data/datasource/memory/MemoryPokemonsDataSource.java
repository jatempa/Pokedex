package com.demo.mvp.appdemo.data.datasource.memory;

import com.demo.mvp.appdemo.data.models.Pokemon;

import java.util.List;

/**
 * Implementación concreta de la fuente de datos en memoria
 */
public class MemoryPokemonsDataSource implements IMemoryPokemonsDataSource {
    @Override
    public List<Pokemon> find() {
        return null;
    }

    @Override
    public void save(Pokemon pokemon) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean mapIsNull() {
        return false;
    }
}
