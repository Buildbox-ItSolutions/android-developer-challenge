package com.example.rodrigo.buildboxtest.dadoscoletados;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CarroInfoService {

    //Variavel que recebe o link do serviço Rest para ser manipulado pelo App
    public static final String BASE_URL = "https://5bc3558ace72500013c2a646.mockapi.io/api/v1/";
    //Final do link usado para identificar o que será tratado ou manipulado
    @GET("carros")
    Call<List<Carro>> listaCarros();//Call chamada dos dados para a lista


}