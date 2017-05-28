package com.jhoann.dell_pc.pokeapp.models;

import java.util.ArrayList;

/**
 * Created by DDELL-PC on 19/05/2017.
 */

public class Pokemon {

    private Integer number;
    private Integer id;
    private String name;
    private String url;
    private String weight;
    private String height;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private ArrayList<Habilidad> abilities;
    private ArrayList<Tipo> types;

    public ArrayList<Habilidad> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<Habilidad> abilities) {
        this.abilities = abilities;
    }

    public ArrayList<Tipo> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Tipo> types) {
        this.types = types;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNumber() {
        String[] urlPartes = url.split("/");
        //para obtener la ultima parte de la url
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
