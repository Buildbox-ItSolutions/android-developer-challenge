package com.example.rodrigo.buildboxtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.rodrigo.buildboxtest.dadoscoletados.Carro;
import com.example.rodrigo.buildboxtest.dadoscoletados.CarroInfoService;
import com.example.rodrigo.buildboxtest.modelos.AdapterDados;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Montando a estrutura da API Retrofit
        Retrofit retrofit = new Retrofit.Builder() //Instanciando Retrofit
                .baseUrl(CarroInfoService.BASE_URL)// BaseURL onde passa os dados que seram manipulados na API Retrofit, vem atraves de um link Rest
                .addConverterFactory(GsonConverterFactory.create())//Conversor "GsonConverterFactory" utilizado para tratar as informações no aplicativo
                .build();

        //Instanciar classe onde esta Url base da Rest que contem as informaçoes que seram tratadas
        CarroInfoService service = retrofit.create(CarroInfoService.class);
        //Lista recebendo os dados já com os informações Rest do link presente na classe CarroInfoservice "Url Base"
        Call<List<Carro>> carros = service.listaCarros();

        //Estrutura RecyclerView.
        recycler= (RecyclerView) findViewById(R.id.recyclerId);
        //Posiciona os itens no formato de grade.
        GridLayoutManager GridLayout = new GridLayoutManager(MainActivity.this, 2);
        recycler.setLayoutManager(GridLayout);

        //Função faz a chamada assíncrona da Lista
        carros.enqueue(new Callback<List<Carro>>() {
            //Função onResponse se encarrega o estado do servidor Rest, caso esteja ok a lista rebe os dados
            @Override
            public void onResponse(Call<List<Carro>> call, Response<List<Carro>> response) {
                //Caso esteja ok, será exibido código 200 significa que servidor Rest esta em bom estado de comunicação
                int statusCode = response.code();

                //Variavel tipo lista recebendo a estrutura(body) da base Rest, já com a comunicação testata na função onResonse
                List<Carro> infcarros = response.body();

                //Adapter uma classe instanciada usada para coletar os dados(Rest) da função onResponse, onde será
                //feita a manipulção dos dados pela estrutura Recycler
                AdapterDados adapter = new AdapterDados(getApplicationContext(), infcarros);
                recycler.setAdapter(adapter);

                Log.d("Meus Carros", "onResponse: " + statusCode);

                //Logs coletados informando interação da classe Carro com os dados coletados do serviço Rest
                for (Carro c : infcarros) {
                    Log.i("Meus Carros", c.getName() + "--" + c.getMarca() + "--" + c.getMotor() + "--" + c.getAno() + "--" + c.getImagem());
                }
            }
            //Caso algo de errado, cominução do serviço Rest não se estabeleça
            @Override
            public void onFailure(Call<List<Carro>> call, Throwable t) {
                Toast.makeText(getApplication(), "Check sua conexão com a internet: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
