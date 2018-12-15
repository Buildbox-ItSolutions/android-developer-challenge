package android_challenge.com.moviesdatabase.Interface;

import org.json.JSONObject;

/**
 * Created by Fabio_2 on 12/12/2018.
 */

public interface ResultsMovie {

    void notifySuccess(JSONObject movieInformations);
    void notifyError(String error);

}
