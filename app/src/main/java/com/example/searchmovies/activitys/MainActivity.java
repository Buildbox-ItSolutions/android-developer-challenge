package com.example.searchmovies.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.searchmovies.GetMovieDataService;
import com.example.searchmovies.R;
import com.example.searchmovies.adpters.RecyclerViewAdapter;
import com.example.searchmovies.api.OmdbInstance;
import com.example.searchmovies.entitys.Search;
import com.example.searchmovies.entitys.SearchMovieEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String movieName;
    TextView fieldText;
    Button buttonSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSearch = findViewById(R.id.button_search_id);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovie();
            }
        });

    }
    //gera array de filmes
    private void generateMovieList(List<Search> MovieEntityArrayList) {
        RecyclerView myrc = findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,MovieEntityArrayList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        myrc.setLayoutManager(layoutManager);
        myrc.setAdapter(myAdapter);
    }

    public void searchMovie(){
        fieldText = findViewById(R.id.field_search_id);
        movieName = fieldText.getText()+"";

        //esconder teclado
        ((InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(fieldText.getWindowToken(), 0);

        //API
        GetMovieDataService service = OmdbInstance.getOmdbInstance().create(GetMovieDataService.class);
        Call<SearchMovieEntity> call = service.getMovieData(movieName,"d2e11186");
        call.enqueue(new Callback<SearchMovieEntity>() {
            @Override
            public void onResponse(Call<SearchMovieEntity> call, Response<SearchMovieEntity> response)
            {
                if (response.body().getSearch() == null)
                    Toast.makeText(MainActivity.this, "Movie not found or result list is too long", Toast.LENGTH_SHORT).show();
                else
                generateMovieList(response.body().getSearch());
            }

            @Override
            public void onFailure(Call<SearchMovieEntity> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }



}
