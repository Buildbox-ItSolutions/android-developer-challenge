package com.example.movies.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

import com.example.movies.R
import com.example.movies.ui.adapters.MoviesAdapter
import com.example.movies.viewmodels.MoviesViewModel
import kotlinx.android.synthetic.main.movie_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class SeriesFragment : Fragment() {

    companion object {
        fun newInstance() = SeriesFragment()
    }

    private val moviesViewModel: MoviesViewModel by viewModel()
    val moviesAdapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view.layoutManager = GridLayoutManager(activity, 2)
        moviesViewModel.loadMovies("super","d2e11186", "series")
        moviesViewModel.getMovies().observe(this, Observer {data ->
            data?.let {
                if (it.isEmpty())
                    Toast.makeText(activity,"Empty List!!", Toast.LENGTH_LONG).show()
                else

                    recycler_view.adapter = moviesAdapter
                    moviesAdapter.add(it)
            }
        })
    }

}
