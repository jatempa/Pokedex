package com.demo.mvp.appdemo.ui;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.mvp.appdemo.R;
import com.demo.mvp.appdemo.data.models.Pokemon;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class PokemonsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DataLoading {

    private List<Pokemon> mPokemons;
    private PokemonItemListener mItemListener;

    private final static int TYPE_POKEMON = 1;
    private final static int TYPE_LOADING_MORE = 2;

    private boolean mLoading = false;
    private boolean mMoreData = false;

    public PokemonsAdapter(List<Pokemon> pokemons, PokemonItemListener itemListener) {
        setList(pokemons);
        mItemListener = itemListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0) {
            return TYPE_POKEMON;
        }
        return TYPE_LOADING_MORE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;

        if (viewType == TYPE_LOADING_MORE) {
            view = inflater.inflate(R.layout.item_loading_footer, parent, false);
            return new LoadingMoreHolder(view);
        }

        view = inflater.inflate(R.layout.item_pokemon, parent, false);

        return  new PokemonsHolder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_POKEMON:
                Pokemon p = mPokemons.get(position);
                PokemonsHolder pokemonHolder = (PokemonsHolder) holder;
                pokemonHolder.name.setText(p.getName());

                Glide.with(pokemonHolder.itemView.getContext())
                        .load("https://pokeapi.co/media/sprites/pokemon/" + p.getNumber() + ".png")
                        .into(pokemonHolder.image);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHolder((LoadingMoreHolder) holder, position);
                break;
        }
    }

    private void bindLoadingViewHolder(LoadingMoreHolder viewHolder, int position) {
        viewHolder.progress.setVisibility((position > 0 && mLoading && mMoreData)
                ? View.VISIBLE : View.INVISIBLE);
    }

    public void replaceData(List<Pokemon> pokemons) {
        setList(pokemons);
        notifyDataSetChanged();
    }

    private void setList(List<Pokemon> pokemons) {
        mPokemons = checkNotNull(pokemons);
    }

    public void addData(List<Pokemon> pokemons) {
        mPokemons.addAll(pokemons);
    }

    @Override
    public int getItemCount() {
        return getDataItemCount() + (mLoading ? 1 : 0);
    }

    public Pokemon getItem(int position) {
        return mPokemons.get(position);
    }

    public void dataStartedLoading() {
        if (mLoading) return;
        mLoading = true;
        Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                notifyItemInserted(getLoadingMoreItemPosition());
            }
        });

    }

    public void dataFinishedLoading() {
        if (!mLoading) return;
        final int loadingPos = getLoadingMoreItemPosition();
        mLoading = false;
        Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                notifyItemRemoved(loadingPos);
            }
        });

    }

    public void setMoreData(boolean more) {
        mMoreData = more;
    }


    private int getLoadingMoreItemPosition() {
        return mLoading ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    public int getDataItemCount() {
        return mPokemons.size();
    }


    @Override
    public boolean isLoadingData() {
        return mLoading;
    }

    @Override
    public boolean isThereMoreData() {
        return mMoreData;
    }

    public class PokemonsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;

        private PokemonItemListener mItemListener;

        public PokemonsHolder(View itemView, PokemonItemListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mItemListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Pokemon pokemon = getItem(position);
            mItemListener.onPokemonClick(pokemon);
        }
    }

    private class LoadingMoreHolder extends RecyclerView.ViewHolder {
        public ProgressBar progress;

        public LoadingMoreHolder(View view) {
            super(view);
            progress = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }

    public interface PokemonItemListener {
        void onPokemonClick(Pokemon clickedPokemon);
    }
}
