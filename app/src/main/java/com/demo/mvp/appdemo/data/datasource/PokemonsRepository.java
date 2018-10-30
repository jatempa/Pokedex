package com.demo.mvp.appdemo.data.datasource;

import android.content.Context;

import com.demo.mvp.appdemo.data.datasource.cloud.ICloudPokemonsDataSource;
import com.demo.mvp.appdemo.data.datasource.memory.IMemoryPokemonsDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Repositorio de pokemons
 */
public class PokemonsRepository implements IPokemonsRepository {
    private final IMemoryPokemonsDataSource mMemoryPokemonsDataSource;
    private final ICloudPokemonsDataSource mCloudPokemonsDataSource;
    private final Context mContext;

    private boolean mReload;

    public PokemonsRepository(IMemoryPokemonsDataSource memoryDataSource,
                              ICloudPokemonsDataSource cloudDataSource,
                              Context context) {
        mMemoryPokemonsDataSource = checkNotNull(memoryDataSource);
        mCloudPokemonsDataSource = checkNotNull(cloudDataSource);

        mContext = checkNotNull(context);
    }

    @Override
    public void getPokemons(GetPokemonsCallback callback) {

    }

    @Override
    public void refreshPokemons() {

    }
}
