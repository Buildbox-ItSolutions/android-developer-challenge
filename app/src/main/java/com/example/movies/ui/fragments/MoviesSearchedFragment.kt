package com.example.movies.ui.fragments


import android.os.Bundle
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

class MoviesSearchedFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = MovieFragment()
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
        val title_recived = arguments!!.getString("title_recived")
        recycler_view.adapter = moviesAdapter
        recycler_view.layoutManager = GridLayoutManager(activity, 2)
        moviesViewModel.loadMovies(title_recived,"d2e11186", "movie")
        moviesViewModel.getMovies().observe(this, Observer {data ->
            data?.let {
                if (it.isEmpty())
                    Toast.makeText(activity,"Empty List!!", Toast.LENGTH_LONG).show()
                else
                    moviesAdapter.add(it)
            }
        })
    }

}
