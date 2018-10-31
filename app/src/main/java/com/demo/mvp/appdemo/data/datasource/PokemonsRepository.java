package com.demo.mvp.appdemo.data.datasource;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.demo.mvp.appdemo.data.datasource.cloud.ICloudPokemonsDataSource;
import com.demo.mvp.appdemo.data.datasource.memory.IMemoryPokemonsDataSource;
import com.demo.mvp.appdemo.data.models.Pokemon;
import com.demo.mvp.appdemo.utils.PokemonCriteria;

import java.util.List;

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
    public void getPokemons(GetPokemonsCallback callback, final PokemonCriteria criteria) {
        if (true) {
            getPokemonsFromServer(callback, criteria);
        } else {
            List<Pokemon> pokemons = mMemoryPokemonsDataSource.find(criteria);
            if (pokemons.size() > 0) {
                callback.onPokemonsLoaded(pokemons);
            } else {
                getPokemonsFromServer(callback, criteria);
            }
        }
    }

    private void getPokemonsFromMemory(GetPokemonsCallback callback,
                                       PokemonCriteria criteria) {

        callback.onPokemonsLoaded(mMemoryPokemonsDataSource.find(criteria));
    }

    private void getPokemonsFromServer(final GetPokemonsCallback callback,
                                       final PokemonCriteria criteria) {

        if (!isOnline()) {
            callback.onDataNotAvailable("No hay conexión de red.");
            return;
        }

        mCloudPokemonsDataSource.getPokemons(
                new ICloudPokemonsDataSource.PokemonServiceCallback() {
                    @Override
                    public void onLoaded(List<Pokemon> pokemons) {
                        refreshMemoryDataSource(pokemons);
                        getPokemonsFromMemory(callback, criteria);
                    }

                    @Override
                    public void onError(String error) {
                        callback.onDataNotAvailable(error);
                    }
                },
                null);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    private void refreshMemoryDataSource(List<Pokemon> pokemons) {
        mMemoryPokemonsDataSource.deleteAll();
        for (Pokemon pokemon : pokemons) {
            mMemoryPokemonsDataSource.save(pokemon);
        }
        mReload = false;
    }

    @Override
    public void refreshPokemons() {
        mReload = true;
    }
}
