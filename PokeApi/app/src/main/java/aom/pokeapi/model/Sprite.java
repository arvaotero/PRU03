package aom.pokeapi.model;

import org.json.JSONObject;

import aom.pokeapi.constants.AppRestConstants;
import aom.pokeapi.util.JSONUtil;
import aom.pokeapi.util.Logger;

/**
 * Created by Alvaro Otero Martin.
 * Copyright 2019 Alvaro Otero Martin. All rights reserved.
 */
public class Sprite {

    private static String TAG = Sprite.class.toString();


    /************************************************
     * ATTRIBUTES
     ***********************************************/
    private String frontDefault;
    private String frontShiny;
    private String frontFemale;
    private String frontShinyfemale;
    private String backDefault;
    private String backShiny;
    private String backFemale;
    private String backShinyFemale;


    /************************************************
     * JSON
     ***********************************************/
    public Sprite(JSONObject json) {
        try {

            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_FRONT_DEFAULT)) {
                frontDefault = (json.getString(AppRestConstants.PARAMS_FRONT_DEFAULT));
            }
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_FRONT_SHINY)) {
                frontShiny = (json.getString(AppRestConstants.PARAMS_FRONT_SHINY));
            }
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_FRONT_FEMALE)) {
                frontFemale = (json.getString(AppRestConstants.PARAMS_FRONT_FEMALE));
            }
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_FRONT_SHINY_FEMALE)) {
                frontShinyfemale = (json.getString(AppRestConstants.PARAMS_FRONT_SHINY_FEMALE));
            }
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_BACK_DEFAULT)) {
                backDefault = (json.getString(AppRestConstants.PARAMS_BACK_DEFAULT));
            }
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_BACK_SHINY)) {
                backShiny = (json.getString(AppRestConstants.PARAMS_BACK_SHINY));
            }
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_BACK_FEMALE)) {
                backFemale = (json.getString(AppRestConstants.PARAMS_BACK_FEMALE));
            }
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_BACK_SHINY_FEMALE)) {
                backShinyFemale = (json.getString(AppRestConstants.PARAMS_BACK_SHINY_FEMALE));
            }

        } catch (Exception e) {
            Logger.Error(TAG, "Error parsing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /************************************************
     * GETTER / SETTER
     ***********************************************/
    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontShiny() {
        return frontShiny;
    }

    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }

    public String getFrontFemale() {
        return frontFemale;
    }

    public void setFrontFemale(String frontFemale) {
        this.frontFemale = frontFemale;
    }

    public String getFrontShinyfemale() {
        return frontShinyfemale;
    }

    public void setFrontShinyfemale(String frontShinyfemale) {
        this.frontShinyfemale = frontShinyfemale;
    }

    public String getBackDefault() {
        return backDefault;
    }

    public void setBackDefault(String backDefault) {
        this.backDefault = backDefault;
    }

    public String getBackShiny() {
        return backShiny;
    }

    public void setBackShiny(String backShiny) {
        this.backShiny = backShiny;
    }

    public String getBackFemale() {
        return backFemale;
    }

    public void setBackFemale(String backFemale) {
        this.backFemale = backFemale;
    }

    public String getBackShinyFemale() {
        return backShinyFemale;
    }

    public void setBackShinyFemale(String backShinyFemale) {
        this.backShinyFemale = backShinyFemale;
    }
}
