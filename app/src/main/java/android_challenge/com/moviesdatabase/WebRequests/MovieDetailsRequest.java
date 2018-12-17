package android_challenge.com.moviesdatabase.WebRequests;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import android_challenge.com.moviesdatabase.Interface.ResultsMovie;
import android_challenge.com.moviesdatabase.R;

/**
 * Created by Fabio_2 on 12/12/2018.
 */

public class MovieDetailsRequest {

    private String _url = "http://www.omdbapi.com/?";
    private String _api_key = "58b7ee65";

    public MovieDetailsRequest(){

    }

    private ResultsMovie mResultsMovieCallBack = null;

    public MovieDetailsRequest(ResultsMovie mResultsMovieCallBack1){
        this.mResultsMovieCallBack = mResultsMovieCallBack1;
    }

    public void searchByID(final Context context, final String id) {

        String url = "";
        url = _url + "i=" + id + "&apikey=" + _api_key;

        final ProgressDialog dialog;

        dialog = new ProgressDialog(context);
        dialog.setMessage("Por favor aguarde...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();


        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();
                        try {

                            if (mResultsMovieCallBack != null)
                                mResultsMovieCallBack.notifySuccess(response);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

                if (error.getClass().equals(TimeoutError.class)) {
                    dialog.dismiss();
                    new AlertDialog.Builder(context)
                            .setTitle("Problema na conexão!")
                            .setMessage("Internet instável! Favor tentar novamente!")
                            .setPositiveButton(R.string.msg_no_internet, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    searchByID(context, id);
                                }
                            })
                            .setNegativeButton(R.string.canceled, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mResultsMovieCallBack != null) {
                                        mResultsMovieCallBack.notifyError("return");
                                    }
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        int socketTimeout = 10000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 1, 1);
        req.setRetryPolicy(policy);
        Volley.newRequestQueue(context).add(req);
    }
}
