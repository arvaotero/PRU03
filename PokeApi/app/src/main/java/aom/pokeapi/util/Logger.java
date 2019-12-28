package aom.pokeapi.util;

import android.util.Log;

/**
 * Created by Alvaro Otero Martin.
 * Copyright 2019 Alvaro Otero Martin. All rights reserved.
 */
public class Logger {
    public static String GENERAL_TAG = "PokeApi";

    /**
     * Show debug message log
     * @param tag to find
     * @param message to show
     */
    public static void Debug(String tag, String message){
        System.out.println("DEBUG [" + GENERAL_TAG + "] " + "[" + tag + "] ***" + message);
        Log.d(GENERAL_TAG, "[" + tag + "] ***" + message);
    }

    /**
     * Show error message log
     * @param tag to find
     * @param message to show
     */
    public static void Error(String tag, String message){
        System.out.println("ERROR [" + GENERAL_TAG + "] " + "[" + tag + "] ***" + message);
        Log.e(GENERAL_TAG, "[" + tag + "] " + message);
    }

    /**
     * Show info message log
     * @param tag to find
     * @param message to show
     */
    public static void Info(String tag, String message){
        Log.i(GENERAL_TAG, "[" + tag + "] " + message);
    }
}
