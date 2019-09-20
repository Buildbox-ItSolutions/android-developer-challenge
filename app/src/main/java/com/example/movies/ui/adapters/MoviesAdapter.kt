package com.example.movies.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.data.Movie
import com.example.movies.R
import kotlinx.android.synthetic.main.item_movie.view.*
import com.example.movies.ui.DownloadImageWithURLTask
import com.example.movies.ui.fragments.MovieDetails


class MoviesViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bindView(item: Movie){
    with(view){
        var downloadImage = DownloadImageWithURLTask(iv_movie)
        if (item.getPoster() != "N/A")
        downloadImage.execute(item.getPoster())
        else
        downloadImage.execute("https://t3.ftcdn.net/jpg/01/09/49/08/240_F_109490811_fDzc0dpSLS0TmoNu4WP34aozBcI6FKZl.jpg")
        tv_movie.text = item.getTitle()
        tv_type.text = item.getType()


    }
    }

}
class MoviesAdapter(val data: MutableList<Movie> = mutableListOf()): RecyclerView.Adapter<MoviesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bindView(data[position])
        holder.view.setOnClickListener() {
            val bundle = Bundle()
            bundle.putString("imdbid_recived", data[position].getImdbid())
            val movieDetails =  MovieDetails()
            movieDetails.arguments = bundle
            val activity = it.context as AppCompatActivity
            val transaction : FragmentTransaction
            transaction = activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_content, movieDetails)
            transaction.addToBackStack(null).commit()


        }



    }

    fun add(item: List<Movie>){
        data.clear()
        data.addAll(item)
        notifyDataSetChanged()
    }

    fun add(item: Movie){
        data.add(item)
        notifyDataSetChanged()
    }

    fun remove (item: Movie){
        data.remove(item)
        notifyDataSetChanged()
    }

}