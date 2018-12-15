package android_challenge.com.moviesdatabase.WebRequests;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import android_challenge.com.moviesdatabase.Pojo.Movies;
import android_challenge.com.moviesdatabase.R;

/**
 * Created by Fabio_2 on 09/12/2018.
 */

public class SearchMovies {

    private String _url = "http://www.omdbapi.com/?";
    private String _api_key = "58b7ee65";

    public SearchMovies() {

    }

    public void getMovies(final Context context, final String title, final int page, final List<Movies> moviesList,
                          final RecyclerView.Adapter adapter, final TextView notfound) {

        String url = "s=" + title + "&page=" + String.valueOf(page) + "&apikey=" + this._api_key;
        this._url = this._url +  url;


        final ProgressDialog dialog;
        if(page == 1){
            dialog = new ProgressDialog(context);
            dialog.setMessage("Buscando Filmes...");
        } else {
            dialog = new ProgressDialog(context, R.style.MyThemeDialog);
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        final JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, this._url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();
                        try {

                            if (!response.toString().contains("Search"))
                                notfound.setText("Nenhum filme encontrado!");
                            else {
                                JSONArray jsonArray = response.getJSONArray("Search");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String title = jsonObject.getString("Title");
                                    int year = Integer.parseInt(jsonObject.getString("Year"));
                                    String imdbID = jsonObject.getString("imdbID");
                                    String type = jsonObject.getString("Type");
                                    String poster = jsonObject.getString("Poster");
                                    Movies movies = new Movies(title, year, imdbID, type, poster);
                                    moviesList.add(movies);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }catch (Exception e){

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
                                    getMovies(context, title, page, moviesList, adapter, notfound);
                                }
                            })
                            .setNegativeButton(R.string.canceled, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
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