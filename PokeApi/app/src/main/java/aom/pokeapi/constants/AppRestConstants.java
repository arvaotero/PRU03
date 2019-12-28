package aom.pokeapi.constants;

import android.os.Parcelable;

/**
 * Created by Alvaro Otero Martin.
 * Copyright 2019 Alvaro Otero Martin. All rights reserved.
 */
public class AppRestConstants {

    // Timeouts
    public static final int REST_TIMEOUT = 30000;

    // Error server
    public static final int ERROR_SERVER = -1;

    //REST Params


    public static final String PARAMS_COUNT = "count";
    public static final String PARAMS_PREVIOUS = "previous";
    public static final String PARAMS_NEXT = "next";
    public static final String PARAMS_RESULTS = "results";


    /**************************************
     *  COMMON
     ***************************************/
    public static final String PARAMS_ID = "id";
    public static final String PARAMS_NAME = "name";
    public static final String PARAMS_URL = "url";

    /**************************************
     *  POKEMON
     ***************************************/
    public static final String PARAMS_HEIGHT = "height";
    public static final String PARAMS_WEIGHT = "weight";

    //List items
    public static final String PARAMS_ABILITIES = "abilities";
    public static final String PARAMS_SPRITES = "sprites";
    public static final String PARAMS_TYPES = "types";
    public static final String PARAMS_MOVES = "moves";

    public static final String PARAMS_ABILITy = "ability";
    public static final String PARAMS_TYPE = "type";
    public static final String PARAMS_MOVE = "move";

    /**************************************
     *  SPRITES
     ***************************************/
    public static final String PARAMS_FRONT_DEFAULT = "front_default";
    public static final String PARAMS_FRONT_SHINY = "front_shiny";
    public static final String PARAMS_FRONT_FEMALE = "front_female";
    public static final String PARAMS_FRONT_SHINY_FEMALE = "front_shiny_female";
    public static final String PARAMS_BACK_DEFAULT = "back_default";
    public static final String PARAMS_BACK_SHINY = "back_shiny";
    public static final String PARAMS_BACK_FEMALE = "back_female";
    public static final String PARAMS_BACK_SHINY_FEMALE = "back_shiny_female";

}
