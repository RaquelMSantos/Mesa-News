package br.com.rmso.mesanews.ui.filter

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.rmso.mesanews.R
import br.com.rmso.mesanews.model.New
import br.com.rmso.mesanews.ui.DetailsActivity
import br.com.rmso.mesanews.ui.NewAdapter
import br.com.rmso.mesanews.utils.onClickListener
import kotlinx.android.synthetic.main.filter_fragment.*

class FilterFragment : Fragment(), onClickListener {

    private var newAdapter: NewAdapter? = null
    private var newList = ArrayList<New>()
    private lateinit var filterViewModel: FilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filter_fragment, container, false)
    }

    private fun initViewModel() {
        filterViewModel = ViewModelProviders.of(this)
            .get(FilterViewModel::class.java)

        filterViewModel.allNews.observe(viewLifecycleOwner, Observer {news ->
            loadRecyclerView()
            newList.clear()
            news?.let {
                newList.addAll(it)
                newAdapter?.setNews(newList)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    private fun loadRecyclerView() {
        newAdapter = NewAdapter(newList, this)
        rv_favorites.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv_favorites.adapter = newAdapter
    }

    override fun onClickCard(position: Int, list: ArrayList<New>) {
        val intent = Intent(this.context, DetailsActivity::class.java)
        val newActual = list[position]

        intent.putExtra("title", newActual.title)
        intent.putExtra("description", newActual.description)
        intent.putExtra("url", newActual.url)
        intent.putExtra("image_url", newActual.image_url)

        startActivity(intent)
    }

    override fun onClickShare(position: Int, newList: ArrayList<New>) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, newList[position].url)
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, newList[position].title)
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}