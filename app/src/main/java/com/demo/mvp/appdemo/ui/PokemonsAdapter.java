package com.demo.mvp.appdemo.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.mvp.appdemo.R;
import com.demo.mvp.appdemo.data.models.Pokemon;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class PokemonsAdapter extends RecyclerView.Adapter<PokemonsAdapter.ViewHolder> {

    private List<Pokemon> mPokemons;
    private PokemonItemListener mItemListener;

    public PokemonsAdapter(List<Pokemon> pokemons, PokemonItemListener itemListener) {
        setList(pokemons);
        mItemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_pokemon, parent, false);

        return  new ViewHolder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            Pokemon p = mPokemons.get(position);
            holder.name.setText(p.getName());

            holder.name.setText(p.getName());

            Glide.with(holder.itemView.getContext())
                    .load("https://pokeapi.co/media/sprites/pokemon/" + p.getNumber() + ".png")
                    .into(holder.image);
        }
    }

    public void replaceData(List<Pokemon> pokemons) {
        setList(pokemons);
        notifyDataSetChanged();
    }

    private void setList(List<Pokemon> notes) {
        mPokemons = checkNotNull(notes);
    }

    public void addData(List<Pokemon> pokemons) {
        mPokemons.addAll(pokemons);
    }

    @Override
    public int getItemCount() {
        return getDataItemCount();
    }

    public Pokemon getItem(int position) {
        return mPokemons.get(position);
    }

    public int getDataItemCount() {
        return mPokemons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;

        private PokemonItemListener mItemListener;

        public ViewHolder(View itemView, PokemonItemListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Pokemon pokemon = getItem(position);
            mItemListener.onPokemonClick(pokemon);
        }
    }

    public interface PokemonItemListener {
        void onPokemonClick(Pokemon clickedNote);
    }
}
