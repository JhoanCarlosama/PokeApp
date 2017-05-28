package com.jhoann.dell_pc.pokeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jhoann.dell_pc.pokeapp.models.Pokemon;
import com.jhoann.dell_pc.pokeapp.pokeapi.PokeapiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResumenPokemon extends AppCompatActivity {

    private TextView tv_nombrePokemon, tv_habilidad, tv_tipo, tv_peso, tv_altura, tv_id;
    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_pokemon);
        tv_id = (TextView)findViewById(R.id.tv_id);
        tv_nombrePokemon = (TextView) findViewById(R.id.tv_nombrePokemon);
        tv_habilidad = (TextView) findViewById(R.id.tv_habilidad);
        tv_tipo = (TextView) findViewById(R.id.tv_tipo);
        tv_altura = (TextView) findViewById(R.id.tv_altura);
        tv_peso = (TextView) findViewById(R.id.tv_peso);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int elId = (int) bundle.get("id");
                obtenerDatosPokemon(elId);
        }

    }

    public void obtenerDatosPokemon(int id) {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<Pokemon> pokemon = service.obtenerPokemon(id);

        pokemon.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()) {
                    Pokemon pokemon = response.body();
                    tv_id.setText(pokemon.getId().toString());
                    tv_nombrePokemon.setText(pokemon.getName());
                    tv_habilidad.setText(pokemon.getAbilities().get(0).getAbility().getName());
                    tv_tipo.setText(pokemon.getTypes().get(0).getType().getName());
                    tv_altura.setText(pokemon.getHeight().toString());
                    tv_peso.setText(pokemon.getWeight().toString());

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
}