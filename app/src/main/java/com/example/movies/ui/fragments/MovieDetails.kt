package com.example.movies.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.example.movies.R
import com.example.movies.ui.DownloadImageWithURLTask
import com.example.movies.viewmodels.MoviesViewModel
import kotlinx.android.synthetic.main.movie_details_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetails : Fragment() {



    companion object {
        fun newInstance() = MovieDetails()
    }


    private val moviesViewModel: MoviesViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setTitle("")

        val id_recived = arguments!!.getString("imdbid_recived")
        moviesViewModel.loadMovieDetail(id_recived, "d2e11186")
        moviesViewModel.getMovieDetail().observe(this, Observer {

            tv_title_detail.setText(it.title)
            tv_movie_plot_detail.setText(it.plot)
            tv_type_detail.setText(it.type)
            tv_year_detail.setText(it.year)
            var downloadImage = DownloadImageWithURLTask(iv_movie_detail)
            if (it.poster != "N/A")
                downloadImage.execute(it.poster)
            else
                downloadImage.execute("https://t3.ftcdn.net/jpg/01/09/49/08/240_F_109490811_fDzc0dpSLS0TmoNu4WP34aozBcI6FKZl.jpg")

        })

    }




}
