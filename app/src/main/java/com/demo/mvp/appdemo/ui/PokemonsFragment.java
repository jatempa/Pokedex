package com.demo.mvp.appdemo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private PokemonsPresenter mPokemonsPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mEmptyView;
    private PokemonsAdapter.PokemonItemListener mItemListener = new PokemonsAdapter.PokemonItemListener() {
        @Override
        public void onPokemonClick(Pokemon clickedPokemon) {
        }
    };

    public static PokemonsFragment newInstance() {
        return new PokemonsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPokemonsAdapter = new PokemonsAdapter(new ArrayList<Pokemon>(0), mItemListener);
        mPokemonsPresenter = new PokemonsPresenter(
                DependencyProvider.providePokemonsRepository(getActivity()),
                this);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pokemons_fragment, container, false);
        // Referencias UI
        mPokemonsList = (RecyclerView) root.findViewById(R.id.my_recycler_view);
        mEmptyView = root.findViewById(R.id.noProducts);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);

        // Setup
        setUpProductsList();
        setUptRefreshLayout();
        return root;
    }

    private void setUpProductsList() {
        mPokemonsList.setAdapter(mPokemonsAdapter);
        mPokemonsList.setHasFixedSize(true);
    }

    private void setUptRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            mPokemonsPresenter.loadPokemons(false);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void showPokemons(List<Pokemon> pokemons) {
        mPokemonsAdapter.replaceData(pokemons);
        mPokemonsList.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingState(final boolean show) {
        if (getView() == null) {
            return;
        }
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(show);
            }
        });
    }

    @Override
    public void showEmptyState() {
        mPokemonsList.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPokemonsError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showPokemonsPage(List<Pokemon> pokemons) {
        mPokemonsAdapter.addData(pokemons);
    }

    @Override
    public void showLoadMoreIndicator(boolean show) {

    }

    @Override
    public void allowMoreData(boolean show) {

    }
}
