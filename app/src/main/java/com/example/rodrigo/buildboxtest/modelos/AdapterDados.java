package com.example.rodrigo.buildboxtest.modelos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodrigo.buildboxtest.R;
import com.example.rodrigo.buildboxtest.dadoscoletados.Carro;
import com.example.rodrigo.buildboxtest.dadoscoletados.InformCarro;
import com.squareup.picasso.Picasso;


import java.util.List;

public class AdapterDados extends RecyclerView.Adapter<AdapterDados.ViewHolderDados> {

    List<Carro> listCarros;
    private Context context;

    //Função paramentro Adapter ira receber dados da intancia na classe MainActivity
    public AdapterDados(Context context, List<Carro> listCarros){
        this.context = context;
        this.listCarros = listCarros;

    }
    //Faz as interações com as View
    @Override
    public ViewHolderDados onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list,parent,false);

        return new ViewHolderDados(view);
    }
    //Usado pela Recycler como uma ponte gerando comunição entre uma View com a lista contendo os dados e assim ser exibidos
    @Override
    public void onBindViewHolder(final ViewHolderDados holder, final int position) {
        
        holder.name.setText(listCarros.get(position).getName());
        Picasso.with(context).load(listCarros.get(position).getImagem()).resize(200,200).into(holder.imagem);
        holder.imagem.setOnClickListener(new View.OnClickListener(){
            //Passando as informações para a outra Activity
            @Override
            public void onClick (View view){
                Intent detacarro = new Intent(context, InformCarro.class);
                detacarro.putExtra("Nomecarro",listCarros.get(position).getName());
                detacarro.putExtra("Marcacarro",listCarros.get(position).getMarca());
                detacarro.putExtra("Imagemcarro",listCarros.get(position).getImagem());
                detacarro.putExtra("Anocarro",listCarros.get(position).getAno());
                detacarro.putExtra("Motorcarro", listCarros.get(position).getMotor());
                detacarro.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detacarro);
            }
        });
    }
    //Tamanho da lista
    @Override
    public int getItemCount() {

        return listCarros.size();
    }
    //Has View que serão manipuladas para exibição das informção
    public class ViewHolderDados extends RecyclerView.ViewHolder {

        ImageView imagem;
        TextView name;

        public ViewHolderDados(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.idDados);
            imagem = (ImageView) itemView.findViewById(R.id.idImagem);
        }
    }
}
