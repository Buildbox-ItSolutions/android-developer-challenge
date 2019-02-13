package com.example.searchmovies.adpters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.searchmovies.R;
import com.example.searchmovies.activitys.Movie_Activity;
import com.example.searchmovies.entitys.Search;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Search> mData;
    public RecyclerViewAdapter(Context mContext, List<Search> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
    myViewHolder.tv_movie_tittle.setText(mData.get(i).getTitle());
    myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(mContext, Movie_Activity.class);
            intent.putExtra("ID",mData.get(i).getImdbID());
            intent.putExtra("Title",mData.get(i).getTitle());
            intent.putExtra("Type",mData.get(i).getType());
            intent.putExtra("Year",mData.get(i).getYear());
            intent.putExtra("ImageURL",mData.get(i).getPoster());
            mContext.startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_movie_tittle;

        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_movie_tittle = itemView.findViewById(R.id.movie_tittle_id);
            cardView = itemView.findViewById(R.id.cardview_id);
        }
    }


}
