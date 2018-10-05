package com.testebuildbox.testebuildbox.Activityes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.testebuildbox.testebuildbox.Adapters.GaleriaInfoRecyclerView;
import com.testebuildbox.testebuildbox.R;
import com.testebuildbox.testebuildbox.api.UnsplashService;
import com.testebuildbox.testebuildbox.model.GaleriaInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recGaleriaInfo;
    private GaleriaInfoRecyclerView galeriaInfoRecyclerView;
    private ProgressBar progres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lista de Imagens");


        //Maapeia o componente RecyclerView
        recGaleriaInfo = findViewById(R.id.recGaleriaInfo);
        recGaleriaInfo.setLayoutManager(new GridLayoutManager(this, 2));
        progres = findViewById(R.id.progres);


        //Cria a implementação do retorift
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UnsplashService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        UnsplashService service = retrofit.create(UnsplashService.class);
        Call<List<GaleriaInfo>> galeriaInfoCall = service.fotosAleatoris();

        galeriaInfoCall.enqueue(new Callback<List<GaleriaInfo>>() {
            @Override
            public void onResponse(Call<List<GaleriaInfo>> call, Response<List<GaleriaInfo>> response) {
                if(response.isSuccessful()){
                    List<GaleriaInfo> galeriaInfos = response.body();
                    galeriaInfoRecyclerView = new GaleriaInfoRecyclerView(MainActivity.this, galeriaInfos);
                    recGaleriaInfo.setAdapter(galeriaInfoRecyclerView);
                    progres.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<GaleriaInfo>> call, Throwable t) {
                Log.e("erro", t.getMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
