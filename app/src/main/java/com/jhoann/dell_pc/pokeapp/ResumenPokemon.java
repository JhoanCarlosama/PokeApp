package com.jhoann.dell_pc.pokeapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jhoann.dell_pc.pokeapp.models.Pokemon;
import com.jhoann.dell_pc.pokeapp.pokeapi.PokeapiService;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.x;

public class ResumenPokemon extends AppCompatActivity {

    private TextView tv_nombrePokemon, tv_habilidad, tv_tipo, tv_peso, tv_altura;
    private Retrofit retrofit;

    private ArrayList<Pokemon> dataset;
    private Context context;
    private static final String TAG = "POKEDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_pokemon);
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

    public void obtenerDatosPokemon(final int id) {
        PokeapiService service = retrofit.create(PokeapiService.class);
        final Call<Pokemon> pokemon = service.obtenerPokemon(id);

        pokemon.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {

                if (response.isSuccessful()) {
                    Pokemon pokemon = response.body();
                    tv_nombrePokemon.setText(pokemon.getName());
                    tv_habilidad.setText("Habilidad: " + pokemon.getAbilities().get(0).getAbility().getName());
                    tv_tipo.setText("Tipo: " + pokemon.getTypes().get(0).getType().getName());
                    String dato1 = pokemon.getHeight();
                    Double inDouble = Double.parseDouble(dato1);
                    Double altura = ((inDouble * 10)/100);
                    tv_altura.setText("Altura: " + altura + " m");
                    String dato2 = pokemon.getWeight().toString();
                    Double inDouble2 = Double.parseDouble(dato2);
                    Double peso = ((inDouble2 * 10)/100);
                    tv_peso.setText("Peso: " + peso + " kg");

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

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Pokemon p = dataset.get(position);
        //para descargar la imagen usando glide
        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/" + p.getNumber() + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoPokemon);
        Toast.makeText(this, p.getNumber(), Toast.LENGTH_LONG);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoPokemon;
        public ViewHolder (View itemView){
            super(itemView);

            fotoPokemon = (ImageView) itemView.findViewById(R.id.fotoPokemon);
        }

    }

}