package com.demo.mvp.appdemo.utils;

import com.demo.mvp.appdemo.data.models.Pokemon;

import java.util.List;

public interface PokemonCriteria {
    List<Pokemon> match(List<Pokemon> pokemons);
}
