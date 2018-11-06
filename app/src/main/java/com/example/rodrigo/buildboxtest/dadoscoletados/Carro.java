package com.example.rodrigo.buildboxtest.dadoscoletados;

public class Carro {

    private String name;
    private String marca;
    private String  motor;
    private String ano;
    private String imagem;

    //construtor vazio
    //public Carro() {}
    //Construtor todos parametros
   /* public Carro(String nome, String marca, float motor, int ano, String imagem) {
        this.nome = nome;
        this.marca = marca;
        this.motor = motor;
        this.ano = ano;
        this.imagem = imagem;
    }*/

    public String getName() {
        return name;
    }

    public void setNome(String name) {
        this.name = name;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}

