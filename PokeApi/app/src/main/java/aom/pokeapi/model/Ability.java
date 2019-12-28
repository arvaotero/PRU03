package aom.pokeapi.model;

import org.json.JSONArray;
import org.json.JSONObject;

import aom.pokeapi.constants.AppRestConstants;
import aom.pokeapi.util.JSONUtil;
import aom.pokeapi.util.Logger;

/**
 * Created by Alvaro Otero Martin.
 * Copyright 2019 Alvaro Otero Martin. All rights reserved.
 */
public class Ability {

    private static String TAG = Ability.class.toString();

    /************************************************
     * ATTRIBUTES
     ***********************************************/
    private String name;
    private String url;

    /************************************************
     * JSON
     ***********************************************/
    public Ability(JSONObject json) {
        try {

            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_NAME)) {
                name = (json.getString(AppRestConstants.PARAMS_NAME));
            }
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_URL)) {
                url = (json.getString(AppRestConstants.PARAMS_URL));
            }
        } catch (Exception e) {
            Logger.Error(TAG, "Error parsing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /************************************************
     * GETTER / SETTER
     ***********************************************/
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
}
