package aom.pokeapi.util;

import org.json.JSONObject;

/**
 * Created by Alvaro Otero Martin.
 * Copyright 2019 Alvaro Otero Martin. All rights reserved.
 */
public class JSONUtil {
    public static boolean ExistsKey(JSONObject json, String key){
        return json.has(key) && !json.isNull(key);
    }
}
