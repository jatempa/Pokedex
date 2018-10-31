package com.demo.mvp.appdemo.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.demo.mvp.appdemo.data.datasource.PokemonsRepository;
import com.demo.mvp.appdemo.data.datasource.cloud.CloudPokemonsDataSource;
import com.demo.mvp.appdemo.data.datasource.memory.MemoryPokemonsDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class DependencyProvider {
    private static Context mContext;
    private static MemoryPokemonsDataSource memorySource = null;
    private static CloudPokemonsDataSource cloudSource = null;
    private static PokemonsRepository mPokemonsRepository = null;

    private DependencyProvider() {
    }

    public static PokemonsRepository providePokemonsRepository(@NonNull Context context) {
        mContext = checkNotNull(context);
        if (mPokemonsRepository == null) {
            mPokemonsRepository = new PokemonsRepository(getMemorySource(),
                    getCloudSource(), context);
        }
        return mPokemonsRepository;
    }

    public static MemoryPokemonsDataSource getMemorySource() {
        if (memorySource == null) {
            memorySource = new MemoryPokemonsDataSource();
        }
        return memorySource;
    }

    public static CloudPokemonsDataSource getCloudSource() {
        if (cloudSource == null) {
            cloudSource = new CloudPokemonsDataSource();
        }
        return cloudSource;
    }
}
