package aom.pokeapi.model.rest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aom.pokeapi.constants.AppRestConstants;
import aom.pokeapi.model.Pokemon;
import aom.pokeapi.util.JSONUtil;
import aom.pokeapi.util.Logger;

public class ObjectListPokemon {

    private static String TAG = ObjectListPokemon.class.toString();

    /************************************************
     * ATTRIBUTES
     ***********************************************/

    private int count;
    private String next;
    private String previous;

    private List<Pokemon> results = new ArrayList<>();

    /************************************************
     * JSON
     ***********************************************/
    public ObjectListPokemon(JSONObject json) {
        try {
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_COUNT)) {
                count = (json.getInt(AppRestConstants.PARAMS_COUNT));
            }
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_NEXT)) {
                next = (json.getString(AppRestConstants.PARAMS_NEXT));
            }
            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_PREVIOUS)) {
                previous = (json.getString(AppRestConstants.PARAMS_PREVIOUS));
            }



            if (JSONUtil.ExistsKey(json, AppRestConstants.PARAMS_RESULTS)) {

                JSONArray jsonArray = json.getJSONArray(AppRestConstants.PARAMS_RESULTS);

                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    results.add(new Pokemon(jsonObject));
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
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Pokemon> getResults() {
        return results;
    }

    public void setResults(List<Pokemon> results) {
        this.results = results;
    }
}
