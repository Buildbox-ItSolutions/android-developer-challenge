package android_challenge.com.moviesdatabase.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import android_challenge.com.moviesdatabase.Adapter.MovieListAdapter;
import android_challenge.com.moviesdatabase.Pojo.Movies;
import android_challenge.com.moviesdatabase.R;
import android_challenge.com.moviesdatabase.WebRequests.SearchMovies;

public class MoviesList extends AppCompatActivity implements MovieListAdapter.OnDataSelected, SearchView.OnQueryTextListener {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private Button seemoremovies;
    private LinearLayoutManager linearLayoutManager;
    public List<Movies> moviesList = new ArrayList<>();
    private TextView notfound;
    private int page = 1;
    private int scrolled = 1;
    private int idscrooled;
    private LinearLayout linearLayout;
    private MenuItem item;
    private SearchView searchView;
    private String text;
    private String title = "Star";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        notfound = (TextView) findViewById(R.id.notfound);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_movie);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (moviesList.size() == linearLayoutManager.findLastCompletelyVisibleItemPosition() + scrolled) {
                    scrolled = -1;
                    idscrooled = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    seemoremovies = new Button(MoviesList.this);
                    seemoremovies.setText("Ver mais");
                    seemoremovies.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
                    seemoremovies.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    seemoremovies.setTextSize(16);
                    linearLayout = (LinearLayout) findViewById(R.id.see_more_products);
                    linearLayout.addView(seemoremovies);

                    seemoremovies.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            page = page + 1;

                            ConnectivityManager connMgr = (ConnectivityManager)
                                    getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                            if (networkInfo != null && networkInfo.isConnected()) {
                                SearchMovies searchMovies = new SearchMovies();
                                searchMovies.getMovies(MoviesList.this, title, page, moviesList, adapter, notfound);
                                linearLayout.removeView(seemoremovies);
                                scrolled = 1;
                            } else {
                                new AlertDialog.Builder(MoviesList.this)
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
                    });
                }
                if (idscrooled != 0) {
                    if (idscrooled != linearLayoutManager.findLastVisibleItemPosition()) {
                        linearLayout.removeView(seemoremovies);
                        scrolled = 1;
                    }
                }
            }
        });

        adapter = new MovieListAdapter(this, (MovieListAdapter.OnDataSelected) this, moviesList);
        recyclerView.setAdapter(adapter);

        makerequest();

    }
        public void makerequest() {

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                SearchMovies moviesRequests = new SearchMovies();
                moviesRequests.getMovies(MoviesList.this, this.title, this.page, moviesList, adapter, notfound);
            } else {
                new AlertDialog.Builder(MoviesList.this)
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


    @Override
    public void onDataSelected(View view, int position) {

        Movies selectedItem;
        selectedItem = moviesList.get(position);
        String imdbID = selectedItem.imdbID;

        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("ID", imdbID);
        startActivity(intent);

    }

    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search_menu, menu);

        item = menu.findItem(R.id.action_search);
        item.setVisible(true);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Pesquisar filme");
        searchView.setOnQueryTextListener(MoviesList.this);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItemsVisibility(menu, item, false);

            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setItemsVisibility(menu, item, true);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i=0; i<menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) item.setVisible(visible);
        }
    }

    public boolean onQueryTextChange(String newText) {
        text = newText;
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        try {
            moviesList = new ArrayList<>();
            adapter = new MovieListAdapter(this,(MovieListAdapter.OnDataSelected)this,moviesList);
            recyclerView.setAdapter(adapter);
            page = 1;
            title = text.toString().trim();
            notfound.setText("");

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                SearchMovies searchMovies = new SearchMovies();
                searchMovies.getMovies(MoviesList.this, title, page, moviesList, adapter, notfound);
                searchView.clearFocus();
            } else{
                new AlertDialog.Builder(MoviesList.this)
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
    }

}
