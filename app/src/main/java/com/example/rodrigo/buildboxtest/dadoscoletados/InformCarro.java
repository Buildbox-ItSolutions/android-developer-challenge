package com.example.rodrigo.buildboxtest.dadoscoletados;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodrigo.buildboxtest.MainActivity;
import com.example.rodrigo.buildboxtest.R;
import com.squareup.picasso.Picasso;



public class InformCarro extends AppCompatActivity {


    ImageView imagem;
    TextView marca,motor,ano,nome;
    String imagemcarro,nomecarro,marcacarro,motorcarro,anocarro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform_carro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent email= new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_TEXT, imagemcarro +"\n\n"+ nomecarro +"\n\n"+ marcacarro +"\n\n"+ motorcarro +"\n\n"+ anocarro);
                email.setType("message/rfc822");
               startActivity(Intent.createChooser(email,"Escolha seu aplicativo de email"));
            }
        });
        //Seta criada para voltar para Activity anterior
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //View criadas
        imagem= (ImageView)findViewById(R.id.idImagemInfor);
        nome=   (TextView)findViewById(R.id.idDadosInfor);
        marca=  (TextView)findViewById(R.id.idDadosInfor2);
        motor=  (TextView)findViewById(R.id.idDadosInfor3);
        ano=    (TextView)findViewById(R.id.idDadosInfor4);

        //Variaveis criadas e recebendo as informações da puExtra da Intent da classe Main
          imagemcarro= getIntent().getStringExtra("Imagemcarro");
          nomecarro= getIntent().getStringExtra("Nomecarro");
          marcacarro= getIntent().getStringExtra("Marcacarro");
          motorcarro= getIntent().getStringExtra("Motorcarro");
          anocarro= getIntent().getStringExtra("Anocarro");


        //Picasso tem a função para tratar imagens
        Picasso.with(this).load(imagemcarro).resize(500,400).into(imagem);
        nome.setText("Veiculo: " +nomecarro);
        marca.setText("Marca: " +marcacarro);
        motor.setText("Motorização: " +motorcarro);
        ano.setText("Ano do veiculo: " +anocarro);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android).
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finish();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

}
