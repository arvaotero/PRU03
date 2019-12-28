package aom.pokeapi.service.rest;

import android.content.Context;

import org.json.JSONObject;

import aom.pokeapi.constants.AppRestConstants;
import aom.pokeapi.model.Pokemon;
import aom.pokeapi.model.rest.ObjectListPokemon;
import aom.pokeapi.model.rest.ObjectResponse;
import aom.pokeapi.util.Logger;

/**
 * Created by Alvaro Otero Martin.
 * Copyright 2019 Alvaro Otero Martin. All rights reserved.
 */
public class RestManagerPokemon {

    private static final String TAG = RestManagerPokemon.class.getSimpleName();

    // METHOD
    private static final String REST_WS_NAME = "pokemon/";

    private static final String REST_URL_LIST = REST_WS_NAME + "";
    private static final String REST_URL_POKEMON = REST_WS_NAME + "%s";

    public static ObjectResponse getList(Context context) {

        ObjectResponse result = new ObjectResponse(AppRestConstants.ERROR_SERVER, ObjectResponse.getSimpleErrorResult());

        try {
            String responseString = RestManager.getRequest(context, REST_URL_LIST);
            JSONObject json = new JSONObject(responseString);

            if(json.length() > 0){

                ObjectListPokemon obj = new ObjectListPokemon(json);
                result.setError(obj.getCount());
                result.setObject(obj);
            }

        } catch (Exception e) {
            Logger.Error(TAG, "Error ws getList: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static ObjectResponse getList(String url) {

        ObjectResponse result = new ObjectResponse(AppRestConstants.ERROR_SERVER, ObjectResponse.getSimpleErrorResult());

        try {
            String responseString = RestManager.getRequest(url);
            JSONObject json = new JSONObject(responseString);

            if(json.length() > 0){

                ObjectListPokemon obj = new ObjectListPokemon(json);
                result.setError(obj.getCount());
                result.setObject(obj);
            }

        } catch (Exception e) {
            Logger.Error(TAG, "Error ws getList: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static ObjectResponse getItemByName(Context context, String name) {

        ObjectResponse result = new ObjectResponse(AppRestConstants.ERROR_SERVER, ObjectResponse.getSimpleErrorResult());

        try {
            String responseString = RestManager.getRequest(context, String.format(REST_URL_POKEMON, name));
            JSONObject json = new JSONObject(responseString);

            if(json != null){
                result.setError(0);
                result.setObject(new Pokemon(json));
            }

        } catch (Exception e) {
            Logger.Error(TAG, "Error ws getItem: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    public static ObjectResponse getItem(String url) {

        ObjectResponse result = new ObjectResponse(AppRestConstants.ERROR_SERVER, ObjectResponse.getSimpleErrorResult());

        try {
            String responseString = RestManager.getRequest(url);
            JSONObject json = new JSONObject(responseString);

            if(json != null){
                result.setError(0);
                result.setObject(new Pokemon(json));
            }

        } catch (Exception e) {
            Logger.Error(TAG, "Error ws getItem: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


}
