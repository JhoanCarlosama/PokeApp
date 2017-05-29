package com.jhoann.dell_pc.pokeapp.pokeapi;

import com.jhoann.dell_pc.pokeapp.models.Pokemon;
import com.jhoann.dell_pc.pokeapp.models.PokemonRespuesta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by DDELL-PC on 22/05/2017.
 */

public interface PokeapiService {
    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}/")
    Call<Pokemon> obtenerPokemon(@Path("id") int id);
}
