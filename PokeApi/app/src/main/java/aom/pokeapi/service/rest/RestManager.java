package aom.pokeapi.service.rest;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import aom.pokeapi.R;
import aom.pokeapi.constants.AppRestConstants;
import aom.pokeapi.util.Logger;

/**
 * Created by Alvaro Otero Martin.
 * Copyright 2019 Wemobile Development. All rights reserved.
 */
public class RestManager {

    private static final String TAG = RestManager.class.getSimpleName();

    /**
     * Standard GET Request
     *
     * @param method     REST Method
     * @return String with Rest Result
     * @throws Exception
     */
    public static String getRequest(Context context, String method) throws Exception {
        try {
            String url = String.format("%s/%s", context.getString(R.string.URL_REST_SERVER), method);
            return getRequest(url);
        } catch (OutOfMemoryError out) {
            Logger.Error(TAG + RestManager.class.toString(),"consultaString OutOfMemoryError: " + out.getMessage());
            out.printStackTrace();
        }

        return null;
    }

    /**
     * Standard GET Request
     *
     * @return String with Rest Result
     * @throws Exception ,TimeoutException
     */
    public static String getRequest(String path) throws Exception {
        try {
            Logger.Debug(TAG, "[getRequest] Request: " + path);

            URL url = new URL(path.replace(" ", "%20"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(AppRestConstants.REST_TIMEOUT);
            conn.setReadTimeout(AppRestConstants.REST_TIMEOUT);
            conn.connect();

            try {
                Logger.Debug(TAG, "[getRequest] GET Request: " + path);
                Logger.Debug(TAG,"[getRequest] Response Code: " + conn.getResponseCode());

                InputStream in = new BufferedInputStream(conn.getInputStream());
                String respStr = readInputStream(in);

                Logger.Debug(TAG, "[getRequest] Response from get: " + respStr);

                return respStr;
            } catch (Exception ex) {
                Logger.Error(TAG + RestManager.class.toString(), "Error: " + ex.getMessage());
                ex.printStackTrace();
                throw ex;
            }
        } catch (OutOfMemoryError out) {
            Logger.Error(TAG + RestManager.class.toString(),
                    "consultaString OutOfMemoryError: " + out.getMessage());
            out.printStackTrace();
        }

        return null;
    }

    public static String readInputStream(InputStream in){
        String respStr = "";
        try {
            byte[] contents = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(contents)) != -1) {
                respStr += new String(contents, 0, bytesRead);
            }

        } catch (Exception e) {
            respStr = "";
            Logger.Error(TAG, "[postRequest] Error during conversion to String");
            e.printStackTrace();
        }
        return respStr;
    }
}
