package com.demo.mvp.appdemo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.mvp.appdemo.PokemonsMvp;
import com.demo.mvp.appdemo.data.models.Pokemon;
import com.demo.mvp.appdemo.R;
import com.demo.mvp.appdemo.di.DependencyProvider;

import java.util.ArrayList;
import java.util.List;

public class PokemonsFragment extends Fragment implements PokemonsMvp.View {
    private RecyclerView mPokemonsList;
    private PokemonsAdapter mPokemonsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mEmptyView;
    private PokemonsAdapter.PokemonItemListener mItemListener = new PokemonsAdapter.PokemonItemListener() {
        @Override
        public void onPokemonClick(Pokemon pokemon) {
            // Aquí lanzarías la pantalla de detalle del pokemon
            Toast.makeText(getContext(), "Pokemon: " + pokemon, Toast.LENGTH_SHORT).show();
        }
    };

    private PokemonsPresenter mPokemonsPresenter;

    public PokemonsFragment() {
        // Required empty public constructor
    }

    public static PokemonsFragment newInstance() {
        return new PokemonsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPokemonsAdapter = new PokemonsAdapter(
            new ArrayList<Pokemon>(0), mItemListener
        );
        mPokemonsPresenter = new PokemonsPresenter(
            DependencyProvider.providePokemonsRepository(getActivity()), this
        );

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pokemons_fragment, container, false);
        // Referencias UI
        mPokemonsList = (RecyclerView) root.findViewById(R.id.pokemons_list);
        mEmptyView = root.findViewById(R.id.noPokemons);

        // Setup
        mPokemonsList.setAdapter(mPokemonsAdapter);
        mPokemonsList.setHasFixedSize(true);

        final  GridLayoutManager layoutManager =
                ( GridLayoutManager) mPokemonsList.getLayoutManager();

        mPokemonsList.setLayoutManager(layoutManager);
        mPokemonsPresenter.loadPokemons(true);

        return root;
    }

    @Override
    public void showPokemons(List<Pokemon> pokemons) {
        mPokemonsAdapter.replaceData(pokemons);
        hideList(false);
    }

    private void hideList(boolean hide) {
        mPokemonsList.setVisibility(hide ? View.GONE : View.VISIBLE);
        mEmptyView.setVisibility(hide ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showPokemonsError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG)
                .show();
    }
}
