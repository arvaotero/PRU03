package aom.pokeapi.util.asynctask;

import aom.pokeapi.model.rest.ObjectResponse;

public interface CGIAsynTaskPostExecute {
    void success(ObjectResponse result);
    void error(ObjectResponse result);
    void actionFinally();
}

