package br.com.codigozeroum.androiddeveloperchallenge.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.codigozeroum.androiddeveloperchallenge.R
import br.com.codigozeroum.androiddeveloperchallenge.models.Result
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_grid_view.view.*

class PosterAdapter(context: Context, var posterList: List<Result>) : BaseAdapter() {

    var context: Context? = context

    override fun getItem(position: Int): Result {
        return posterList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return posterList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val poster = this.posterList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        var posterCardView = inflator.inflate(R.layout.item_grid_view, null)

        if(poster.Poster == "N/A"){
            Picasso.get().load(R.drawable.image_not_found).resize(800, 1000).centerCrop().into(posterCardView.ivImagePoster)
        } else{
            Picasso.get().load(poster.Poster).resize(800, 1000).centerCrop().into(posterCardView.ivImagePoster)
        }

        posterCardView.tvYear.text = poster.Year
        posterCardView.tvTitle.text = poster.Title

        return posterCardView
    }

}