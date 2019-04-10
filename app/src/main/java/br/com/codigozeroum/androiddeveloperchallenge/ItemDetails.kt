package br.com.codigozeroum.androiddeveloperchallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.codigozeroum.androiddeveloperchallenge.models.ResultIMDbId
import br.com.codigozeroum.androiddeveloperchallenge.services.RetrofitInitializer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemDetails : AppCompatActivity() {

    private val apikey = "7a4c5cac"
    private val plot = "full"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        val actionBar = supportActionBar
        actionBar!!.title = "Detalhes"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val imdbID = intent.getStringExtra("imdbID")

        val call = RetrofitInitializer().searchMovieIMDbIdService().searchMovieIMDbId(imdbID, plot, apikey)

        call.enqueue(object : Callback<ResultIMDbId?> {

            override fun onResponse(call: Call<ResultIMDbId?>?, response: Response<ResultIMDbId?>?) {

                if (response?.body()?.Response?.toBoolean()!!) {

                    val result:ResultIMDbId = response.body().let{ it!! }
                    this@ItemDetails.setDetailsValues(result)

                } else {
                    Toast.makeText(this@ItemDetails, response.body()?.Error, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResultIMDbId?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })

    }

    fun setDetailsValues(result: ResultIMDbId){

        if(result.Poster == "N/A"){
            Picasso.get().load(R.drawable.image_not_found).resize(800, 1000).centerCrop().into(ivPoster)
        } else{
            Picasso.get().load(result.Poster).resize(800, 1000).centerCrop().into(ivPoster)
        }
        rtbRating.rating = (result.imdbRating.toFloat()) / 2
        tvRating.text = "Rating IMDb: ${result.imdbRating}"
        tvTotalVotes.text = "Total de Votos: ${result.imdbVotes}"
        tvTitle.text = result.Title
        tvRuntime.text = result.Runtime
        tvProduction.text = result.Production
        tvDirector.text = result.Director
        tvGenre.text = result.Genre

        tvAnoReleased.text = "${result.Year} / ${result.Released}"
        tvLanguageCountry.text = "${result.Language} / ${result.Country}"
        tvWriter.text = result.Writer
        tvActors.text = result.Actors
        tvPlot.text = result.Plot
        tvRated.text = result.Rated

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
