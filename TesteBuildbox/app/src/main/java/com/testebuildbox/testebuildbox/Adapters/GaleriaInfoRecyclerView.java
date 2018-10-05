package com.testebuildbox.testebuildbox.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.testebuildbox.testebuildbox.Activityes.GaleriaInfoActivityDetails;
import com.testebuildbox.testebuildbox.R;
import com.testebuildbox.testebuildbox.model.GaleriaInfo;
import com.testebuildbox.testebuildbox.model.User;

import java.util.List;

/**
 * Created by fjesus on 05/10/2018.
 */

public class GaleriaInfoRecyclerView extends RecyclerView.Adapter<GaleriaInfoRecyclerView.ViewHolder> {

    private List<GaleriaInfo> galeriaInfos;
    private Activity activity;

    public GaleriaInfoRecyclerView(Activity activity, List<GaleriaInfo> galeriaInfos){
        this.galeriaInfos = galeriaInfos;
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.descricao);
        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public GaleriaInfoRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.linha_timeline, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GaleriaInfoRecyclerView.ViewHolder holder, int position) {

        final GaleriaInfo galeriaInfo = galeriaInfos.get(position);

        holder.textView.setText(galeriaInfo.getDescription());
        Picasso.get()
                .load(galeriaInfo.getUrls().getThumb())
                .resize(1000, 600)
                .into(holder.imageView);

        //ClickListener em ImgageView, redireciona para a GaleriaInfoActivityDetails e exibe os dados que estão sendo passados pelo intent
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, GaleriaInfoActivityDetails.class);
                User user = galeriaInfo.getUser();
                intent.putExtra("UserInfo", user);
                activity.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return galeriaInfos.size();
    }


}
