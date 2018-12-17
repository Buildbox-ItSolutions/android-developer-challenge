package android_challenge.com.moviesdatabase.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import org.json.JSONArray;
import java.util.List;
import android_challenge.com.moviesdatabase.Pojo.Movies;
import android_challenge.com.moviesdatabase.R;
import android_challenge.com.moviesdatabase.WebRequests.CustomVolleyRequestQueue;

/**
 * Created by Fabio_2 on 10/12/2018.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> implements Response.Listener<JSONArray>  {

    private List<Movies> moviesList;
    private Context context;
    private MovieListAdapter.OnDataSelected onDataSelected;
    private ImageLoader mImageLoader;


    public MovieListAdapter(Context context, MovieListAdapter.OnDataSelected onDataSelected, List<Movies> moviesList) {
        this.context = context;
        this.onDataSelected = onDataSelected;
        this.moviesList = moviesList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {
        Movies movies = moviesList.get(position);

        holder.textViewTitleMovie.setText(movies.getTitle());
        holder.textViewTypeMovie.setText(movies.getType());
        holder.textViewYearMovie.setText(String.valueOf(movies.getYear()));

        mImageLoader = CustomVolleyRequestQueue.getInstance(context).getImageLoader();
        mImageLoader.get(movies.getPoster(),ImageLoader.getImageListener(holder.mNetworkImageView,R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        holder.mNetworkImageView.setImageUrl(movies.getPoster(),mImageLoader);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    @Override
    public void onResponse(JSONArray response) {
    }

    public interface OnDataSelected {
        void onDataSelected(View view, int position);
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        public TextView textViewTitleMovie;
        public TextView textViewTypeMovie;
        public TextView textViewYearMovie;
        public NetworkImageView mNetworkImageView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    treatOnDataSelectedIfNecessary(v,getAdapterPosition());
                }
            });

            textViewTitleMovie = (TextView)view.findViewById(R.id.title_movie);
            textViewTypeMovie = (TextView) view.findViewById(R.id.type_movie);
            textViewYearMovie = (TextView)view.findViewById(R.id.year_movie);
            mNetworkImageView = (NetworkImageView)view.findViewById(R.id.poster_movie);

        }

        private void treatOnDataSelectedIfNecessary(View view, int position) {
            if(onDataSelected != null) {
                onDataSelected.onDataSelected(view, position);

            }
        }

    }
}
