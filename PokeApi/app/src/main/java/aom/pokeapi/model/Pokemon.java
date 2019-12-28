package aom.pokeapi.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aom.pokeapi.constants.AppRestConstants;
import aom.pokeapi.util.JSONUtil;
import aom.pokeapi.util.Logger;

/**
 * Created by Alvaro Otero Martin.
 * Copyright 2019 Alvaro Otero Martin. All rights reserved.
 */
public class Pokemon {

    private static String TAG = Pokemon.class.toString();

    /************************************************
     * ATTRIBUTES
     ***********************************************/
    private int id;
    private String name;
    private String url;
    private int height;
    private int weight;

    private Sprite sprite;
    private List<Ability> abilities = new ArrayList<>();
    private List<Type> types = new ArrayList<>();
    private List<Move> moves = new ArrayList<>();

    /************************************************
     * JSON
     ***********************************************/
    public Pokemon(JSONObject json) {
        try {
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_ID)) {
                id = (json.getInt(AppRestConstants.PARAMS_ID));
            }else{
                id = -1;
            }

            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_NAME)) {
                name = (json.getString(AppRestConstants.PARAMS_NAME));
            }

            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_HEIGHT)) {
                height = (json.getInt(AppRestConstants.PARAMS_HEIGHT));
            }
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_WEIGHT)) {
                weight = (json.getInt(AppRestConstants.PARAMS_WEIGHT));
            }

            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_SPRITES)) {
                sprite = (new Sprite(json.getJSONObject(AppRestConstants.PARAMS_SPRITES)));
            }

            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_URL)) {
                //Si existe URL estamos en el caso de lista de pokemon
                url = (json.getString(AppRestConstants.PARAMS_URL));

                //Obtenemos el ID a partir de la URL
                String[] arrayId = url.split("/");
                if(arrayId.length > 0){
                    id =  Integer.parseInt(arrayId[arrayId.length-1]);
                }
            }

            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_ABILITIES)) {

                JSONArray jsonArray = json.getJSONArray(AppRestConstants.PARAMS_ABILITIES);

                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    abilities.add(new Ability(jsonObject.getJSONObject(AppRestConstants.PARAMS_ABILITy)));
                }
            }


            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_TYPES)) {

                JSONArray jsonArray = json.getJSONArray(AppRestConstants.PARAMS_TYPES);

                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    types.add(new Type(jsonObject.getJSONObject(AppRestConstants.PARAMS_TYPE)));
                }
            }

            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_MOVES)) {

                JSONArray jsonArray = json.getJSONArray(AppRestConstants.PARAMS_MOVES);

                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    moves.add(new Move(jsonObject.getJSONObject(AppRestConstants.PARAMS_MOVE)));
                }
            }
        } catch (Exception e) {
            Logger.Error(TAG, "Error parsing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /************************************************
     * GETTER / SETTER
     ***********************************************/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    /************************************************
     * EQUALS / HASCODE
     ***********************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pokemon pokemon = (Pokemon) o;

        return name != null ? name.equals(pokemon.name) : pokemon.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
