package android_challenge.com.moviesdatabase.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import org.json.JSONException;
import org.json.JSONObject;
import android_challenge.com.moviesdatabase.Interface.ResultsMovie;
import android_challenge.com.moviesdatabase.R;
import android_challenge.com.moviesdatabase.WebRequests.CustomVolleyRequestQueue;
import android_challenge.com.moviesdatabase.WebRequests.MovieDetailsRequest;

public class MovieDetails extends AppCompatActivity {

    private String imdbID;

    private TextView title;
    private TextView type;
    private TextView year;
    private TextView released;
    private TextView rated;
    private TextView runtime;
    private TextView director;
    private TextView genre;
    private TextView actors;
    private TextView plot;
    private TextView awards;
    private TextView language;
    private TextView country;
    private TextView metascore;
    private TextView imdbrating;
    private TextView imdbvotes;
    private TextView writer;
    private NetworkImageView mNetworkImageView;
    private ImageLoader mImageLoader;

    ResultsMovie initResultsmovieCallback1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_detail);

        title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            imdbID = null;
        } else {
            imdbID = extras.getString("ID");
        }

        type = (TextView) findViewById(R.id.type_movie);
        year = (TextView) findViewById(R.id.year_movie);
        released = (TextView) findViewById(R.id.released_movie);
        rated = (TextView) findViewById(R.id.rated_movie);
        runtime = (TextView) findViewById(R.id.runtime_movie);
        director = (TextView) findViewById(R.id.director_movie);
        genre = (TextView) findViewById(R.id.genre_movie);
        actors = (TextView) findViewById(R.id.actors_movie);
        plot = (TextView) findViewById(R.id.plot_movie);
        awards = (TextView) findViewById(R.id.awards_movie);
        language = (TextView) findViewById(R.id.language_movie);
        country = (TextView) findViewById(R.id.country_movie);
        metascore = (TextView) findViewById(R.id.metascore_movie);
        imdbrating = (TextView) findViewById(R.id.imdbrating_movie);
        imdbvotes = (TextView) findViewById(R.id.imdbvotes_movie);
        writer = (TextView) findViewById(R.id.writer_movie);
        mNetworkImageView = (NetworkImageView)findViewById(R.id.poster_movie);
        makeRequest();
    }

    public void makeRequest() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            initResultsVolleyArrayCallback();
            MovieDetailsRequest moviedetails = new MovieDetailsRequest(initResultsmovieCallback1);
            moviedetails.searchByID(this, imdbID);
        } else {
                new AlertDialog.Builder(MovieDetails.this)
                        .setTitle("Sem Internet!")
                        .setMessage("Não tem nenhuma conexão de rede disponível!")
                        .setPositiveButton(R.string.box_msg_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }


    void initResultsVolleyArrayCallback() {

        initResultsmovieCallback1 = new ResultsMovie() {

            @Override
            public void notifySuccess(JSONObject movieInformations) {
                try {
                    title.setText(movieInformations.getString("Title"));
                    type.setText(movieInformations.getString("Type"));
                    year.setText(movieInformations.getString("Year"));
                    released.setText(movieInformations.getString("Released"));
                    rated.setText(movieInformations.getString("Rated"));
                    runtime.setText(movieInformations.getString("Runtime"));
                    director.setText(movieInformations.getString("Director"));
                    genre.setText(movieInformations.getString("Genre"));
                    actors.setText(movieInformations.getString("Actors"));
                    plot.setText(movieInformations.getString("Plot"));
                    awards.setText(movieInformations.getString("Awards"));
                    language.setText(movieInformations.getString("Language"));
                    country.setText(movieInformations.getString("Country"));
                    metascore.setText(movieInformations.getString("Metascore"));
                    imdbrating.setText(movieInformations.getString("imdbRating"));
                    imdbvotes.setText(movieInformations.getString("imdbVotes"));
                    writer.setText(movieInformations.getString("Writer"));
                    mImageLoader = CustomVolleyRequestQueue.getInstance(MovieDetails.this).getImageLoader();
                    mImageLoader.get(movieInformations.getString("Poster"), ImageLoader.getImageListener(mNetworkImageView,
                            R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
                    mNetworkImageView.setImageUrl(movieInformations.getString("Poster"), mImageLoader);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(String error) {

            }
        };
    }
}
