package com.testebuildbox.testebuildbox.Activityes;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.testebuildbox.testebuildbox.R;
import com.testebuildbox.testebuildbox.model.GaleriaInfo;
import com.testebuildbox.testebuildbox.model.User;

public class GaleriaInfoActivityDetails extends AppCompatActivity {

    private ImageView imgPerfil;
    private TextView nomeUser;
    private TextView local;
    private TextView instagram;
    private TextView youtube;
    private ImageView instagramIcon;
    private ImageView youtubeIcon;
    private ProgressBar progressPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_info_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Perfil");


        imgPerfil = findViewById(R.id.imgPerfil);
        nomeUser = findViewById(R.id.nomeUser);
        local = findViewById(R.id.local);
        imgPerfil = findViewById(R.id.imgPerfil);
        youtube = findViewById(R.id.youtube);
        instagram = findViewById(R.id.instagram);
        instagramIcon = findViewById(R.id.instagramIcon);
        youtubeIcon = findViewById(R.id.youtubeIcon);
        progressPerfil = findViewById(R.id.progressPerfil);


        Intent intent = getIntent();

        if(intent != null){
            //Recupera os dados que veio no Intent
           final User user = getIntent().getExtras().getParcelable("UserInfo");
            if(user != null){

                //Deixa invisivel os botões das redes sociais caso venha nulo
                if(user.getInstagram_username() == null){
                    instagram.setVisibility(View.GONE);
                    instagramIcon.setVisibility(View.GONE);
                }
                if(user.getPortfolio_url() == null){
                    youtube.setVisibility(View.GONE);
                    youtubeIcon.setVisibility(View.GONE);
                }

                //Insere a foto do pefil no imageView
                Picasso.get()
                        .load(user.getProfile_image().getLarge())
                        .resize(200, 200)
                        .centerCrop()
                        .into(imgPerfil);

                //Seta os dados nos respectivos componentes
                nomeUser.setText(user.getName());
                local.setText(user.getLocation());
                instagram.setText(user.getInstagram_username());
                youtube.setText("Portifolio");

                youtube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = user.getPortfolio_url();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });

                instagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = "https://www.instagram.com/"+user.getInstagram_username();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
            }
        }
        progressPerfil.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
