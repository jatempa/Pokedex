package com.demo.mvp.appdemo.ui;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.mvp.appdemo.R;

public class MainActivity extends AppCompatActivity {
    private Fragment mProductsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias UI
        mProductsFragment = getSupportFragmentManager().findFragmentById(R.id.pokemons_container);

        // Setup
        setUpPokemonsFragment();
    }

    private void setUpPokemonsFragment() {
        if (mProductsFragment == null) {
            mProductsFragment = PokemonsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.pokemons_container, mProductsFragment)
                    .commit();
        }
    }
}
