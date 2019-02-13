package com.example.searchmovies.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.searchmovies.GetMoviePlotService;
import com.example.searchmovies.R;
import com.example.searchmovies.entitys.Search;

import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Movie_Activity extends AppCompatActivity {
    private TextView movietitle, movietype,movieyear,movieplot;
    private Retrofit retrofitPlot;
            ImageView movieposter;

    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageWithURLTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

   //
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_);
        Intent intent = getIntent();
        //recuperando a url do poster
        String url = intent.getExtras().getString("ImageURL");

        String Title = intent.getExtras().getString("Title");
        String Year = intent.getExtras().getString("Year");
        String Type = intent.getExtras().getString("Type");
        String ID = intent.getExtras().getString("ID");
        retrofitPlot = new Retrofit.Builder().baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        GetMoviePlotService getMoviePlotService = retrofitPlot.create(GetMoviePlotService.class);

        Call<Search> call = getMoviePlotService.getMoviePlot(ID,"d2e11186");

        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                if (response.isSuccessful()
                ) {
                    Search search = response.body();
                    movieplot.setText(search.getPlot());

                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {

            }
        });

        movietitle =findViewById(R.id.movie_tittledetail_id);
        movietype = findViewById(R.id.movie_typedetail_id);
        movieyear = findViewById(R.id.movie_yeardetail_id);
        movieplot = findViewById(R.id.movie_plotdetail_id);
        movieposter = findViewById(R.id.movie_poster_id);
        DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(movieposter);
        downloadTask.execute(url);
        movietitle.setText(Title);
        movietype.setText(Type);
        movieyear.setText(Year);
    }




}
