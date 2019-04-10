package br.com.codigozeroum.androiddeveloperchallenge

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import br.com.codigozeroum.androiddeveloperchallenge.adapters.PosterAdapter
import br.com.codigozeroum.androiddeveloperchallenge.models.Result
import br.com.codigozeroum.androiddeveloperchallenge.models.SearchObjectResponse
import br.com.codigozeroum.androiddeveloperchallenge.services.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val apikey = "7a4c5cac"
    var posterAdapter: PosterAdapter? = null
    private var rbSelectedText: String = "Título"
    var searchResult: List<Result>? = null
    var pages = 0
    var pageAtual = 0

    var tituloPerquisa = ""
    var anoPesquisa = ""
    var tipoPesquisa = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = "Home - Pesquisa"

        radioGroup.setOnCheckedChangeListener{ _, checkedId ->

            rbSelectedText = findViewById<RadioButton>(checkedId).text.toString()

            when(rbSelectedText){
                "Título" -> {
                    llTitulo.visibility = View.VISIBLE
                    llTituloTipo.visibility = View.GONE
                    llTituloAno.visibility = View.GONE
                }
                "Título+Tipo" -> {
                    llTitulo.visibility = View.GONE
                    llTituloTipo.visibility = View.VISIBLE
                    llTituloAno.visibility = View.GONE
                }
                "Título+Ano" -> {
                    llTitulo.visibility = View.GONE
                    llTituloTipo.visibility = View.GONE
                    llTituloAno.visibility = View.VISIBLE
                }
            }
        }

        btn_Buscar.setOnClickListener {v: View ->



            if(this.isNetworkAvailable()){

                this.showOrDismissLoading()

                val imm = this@MainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)

                when(rbSelectedText){
                    "Título" -> this.searchTitulo(etT_titulo.text.toString(), 1)
                    "Título+Tipo" -> this.searchTituloTipo(etTT_titulo.text.toString(), spnTT_tipo.selectedItem.toString(), 1)
                    "Título+Ano" -> this.searchTituloAno(etTA_titulo.text.toString(), etTA_ano.text.toString(), 1)
                }
            }else{
                Toast.makeText(this@MainActivity, "Favor Verificar sua Conexão de INTERNET", Toast.LENGTH_LONG).show()
            }

        }

        btn_next.setOnClickListener { v: View ->

            if(this.isNetworkAvailable()){
                when(rbSelectedText){
                    "Título" -> this.searchTitulo(tituloPerquisa, (++ pageAtual) )
                    "Título+Tipo" -> this.searchTituloTipo(tituloPerquisa, tipoPesquisa, (++ pageAtual))
                    "Título+Ano" -> this.searchTituloAno(tituloPerquisa, anoPesquisa, (++ pageAtual))
                }
            }else{
                Toast.makeText(this@MainActivity, "Favor Verificar sua Conexão de INTERNET", Toast.LENGTH_LONG).show()
            }

        }

        btn_previous.setOnClickListener { v: View ->

            if(this.isNetworkAvailable()){
                when(rbSelectedText){
                    "Título" -> this.searchTitulo(tituloPerquisa, (-- pageAtual) )
                    "Título+Tipo" -> this.searchTituloTipo(tituloPerquisa, tipoPesquisa, (-- pageAtual))
                    "Título+Ano" -> this.searchTituloAno(tituloPerquisa, anoPesquisa, (-- pageAtual))
                }
            }else{
                Toast.makeText(this@MainActivity, "Favor Verificar sua Conexão de INTERNET", Toast.LENGTH_LONG).show()
            }
        }

        btn_inicio.setOnClickListener { v: View ->

            if(this.isNetworkAvailable()){
                when(rbSelectedText){
                    "Título" -> this.searchTitulo(tituloPerquisa, 1)
                    "Título+Tipo" -> this.searchTituloTipo(tituloPerquisa, tipoPesquisa, 1)
                    "Título+Ano" -> this.searchTituloAno(tituloPerquisa, anoPesquisa, 1)
                }
            }else{
                Toast.makeText(this@MainActivity, "Favor Verificar sua Conexão de INTERNET", Toast.LENGTH_LONG).show()
            }
        }

        gridview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            if(this.isNetworkAvailable()){
                val imdbID = posterAdapter!!.getItem(position).imdbID

                val intent = Intent(this, ItemDetails::class.java)
                intent.putExtra("imdbID", imdbID)
                startActivity(intent)

            }else{
                Toast.makeText(this@MainActivity, "Favor Verificar sua Conexão de INTERNET", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            (networkInfo?.isConnected != null)
        } else false
    }

    fun searchTitulo(titulo: String, page: Int){

        if(titulo == ""){
            Toast.makeText(this@MainActivity, "Necessário Inserir um Valor para Busca", Toast.LENGTH_LONG).show()
            return
        }

        tituloPerquisa = titulo
        pageAtual = page
        val call = RetrofitInitializer().searchTitleService().searchMoviesTitle(titulo, page, apikey)
        this.showResults(call, page)
    }

    fun searchTituloTipo(titulo: String, tipo: String, page: Int){

        if(titulo == ""){
            Toast.makeText(this@MainActivity, "Necessário Inserir um Valor para Busca", Toast.LENGTH_LONG).show()
            return
        }

        tituloPerquisa = titulo
        tipoPesquisa = tipo
        pageAtual = page
        val call = RetrofitInitializer().searchTitleTypeService().searchMoviesTitleType(titulo, tipo, page, apikey)
        this.showResults(call, page)
    }

    fun searchTituloAno(titulo: String, ano: String, page: Int){

        if(titulo == "" || ano == ""){
            Toast.makeText(this@MainActivity, "Necessário Inserir um Valor em todos os campos para Busca", Toast.LENGTH_LONG).show()
            return
        }

        tituloPerquisa = titulo
        anoPesquisa = ano
        pageAtual = page
        val call = RetrofitInitializer().searchTitleYearService().searchMoviesTitleYear(titulo, ano.toInt(), page, apikey)
        this.showResults(call, page)
    }

    fun showResults(call: Call<SearchObjectResponse>, page: Int){

        btn_previous.visibility = if(pageAtual == 1) View.INVISIBLE else View.VISIBLE
        btn_inicio.visibility = btn_previous.visibility

            call.enqueue(object : Callback<SearchObjectResponse?> {

            override fun onResponse(call: Call<SearchObjectResponse?>?, response: Response<SearchObjectResponse?>?) {

                if(response?.body()?.Response?.toBoolean()!!){

                    searchResult = response?.body()?.Search
                    var totalResults = response?.body()?.totalResults?.toInt()

                    if(searchResult != null){

                        tvResultsCount.text = "Resultado:  $totalResults  itens"

                        pages = if(totalResults != null && totalResults > 10) totalResults /10 + 1 else 1

                        btn_next.visibility = if(pages > 1 && pageAtual < pages) View.VISIBLE else View.INVISIBLE

                        tvPagination.visibility = View.VISIBLE
                        tvPagination.text = "Página  $pageAtual de $pages"

                        this@MainActivity.showOrDismissLoading()

                        posterAdapter = PosterAdapter(this@MainActivity, searchResult!!)
                        gridview.adapter =  posterAdapter
                    }

                }else{
                    this@MainActivity.showOrDismissLoading()
                    Toast.makeText(this@MainActivity, response.body()?.Error , Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<SearchObjectResponse?>?, t: Throwable?) {
                tvPagination.visibility = View.GONE
                btn_next.visibility = View.GONE
                btn_previous.visibility = View.GONE
                btn_inicio.visibility = View.GONE
                this@MainActivity.showOrDismissLoading()
                Log.e("onFailure error", t?.message)
            }
        })
    }

    fun showOrDismissLoading(){
        val visibility = if (progressBar.visibility == View.GONE) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }
}














