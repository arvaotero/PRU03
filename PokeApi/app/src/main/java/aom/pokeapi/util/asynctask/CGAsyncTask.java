package aom.pokeapi.util.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import aom.pokeapi.R;
import aom.pokeapi.constants.AppRestConstants;
import aom.pokeapi.model.rest.ObjectResponse;
import aom.pokeapi.util.Logger;

public class CGAsyncTask extends AsyncTask<Void, Object, ObjectResponse> {

    private String TAG = "";
    private Context activity;

    private CGIAsynTaskPreExecute preExecute;
    private CGIAsynTaskDoInBackgroundExecute doInBacground;
    private CGIAsynTaskPostExecute postExecute;

    public CGAsyncTask(String TAG,
                       Context activity,
                       CGIAsynTaskPreExecute preExecute,
                       CGIAsynTaskDoInBackgroundExecute doInBacground,
                       CGIAsynTaskPostExecute postExecute){
        this.TAG = TAG;
        this.activity = activity;
        this.preExecute = preExecute;
        this.doInBacground = doInBacground;
        this.postExecute = postExecute;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        preExecute.action();
    }

    @Override
    protected ObjectResponse doInBackground(Void... params) {
        try {
            return doInBacground.action();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ObjectResponse(AppRestConstants.ERROR_SERVER, activity.getString(R.string.ws_error_server));
    }

    @Override
    protected void onPostExecute(ObjectResponse result) {
        super.onPostExecute(result);
        try {
            Logger.Debug(TAG, "result: " + result.getError() + ", " + result.getObject());


            if (result != null && result.getError() >= 0) {
                postExecute.success(result);
            } else {
                postExecute.error(result);
            }

        } catch (Exception e) {
            Logger.Error(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            postExecute.actionFinally();
        }



    }

}
