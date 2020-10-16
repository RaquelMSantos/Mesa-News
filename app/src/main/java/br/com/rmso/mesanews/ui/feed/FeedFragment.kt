package br.com.rmso.mesanews.ui.feed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.rmso.mesanews.utils.LiveDataResult
import br.com.rmso.mesanews.model.New
import br.com.rmso.mesanews.R
import br.com.rmso.mesanews.network.ApiService
import br.com.rmso.mesanews.network.NewsApi
import br.com.rmso.mesanews.repository.remote.feed.FeedDataSource
import br.com.rmso.mesanews.repository.remote.feed.FeedUseCase
import br.com.rmso.mesanews.ui.DetailsActivity
import br.com.rmso.mesanews.ui.MainActivity
import br.com.rmso.mesanews.ui.NewAdapter
import br.com.rmso.mesanews.ui.filter.FilterViewModel
import br.com.rmso.mesanews.utils.onClickListener
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.item_new.btn_favorite
import kotlinx.coroutines.Dispatchers


class FeedFragment : Fragment(), onClickListener {

    private var newAdapter: NewAdapter? = null
    private var highlightsAdapter: NewAdapter? = null
    private var highlightsList = ArrayList<New>()
    private var newList = ArrayList<New>()
    private lateinit var viewModel: FeedViewModel
    private lateinit var token: String
    private var currentPage = 1
    private var perPage = 20

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        token = (activity as MainActivity?)?.loadToken().toString()

        initViewModel()
        observerViewModelHighlight()
        observerViewModelNew()


        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    private fun observerViewModelHighlight() {
        viewModel.highlightLiveData.observe(viewLifecycleOwner, Observer<LiveDataResult<MutableList<New>>>{
            when(it?.status) {
                LiveDataResult.STATUS.ERROR -> {
                    Toast.makeText(context, it.error.toString(), Toast.LENGTH_LONG).show()
                }

                LiveDataResult.STATUS.SUCCESS -> {
                    highlightsList.addAll(it.data as ArrayList<New>)
                    loadRecyclerView()
                    visibilityView(false, progress_bar_highlights)
                }

                LiveDataResult.STATUS.LOADING -> {
                    visibilityView(true, progress_bar_highlights)
                }
            }
        })
    }

    private fun observerViewModelNew() {
        viewModel.newLiveData.observe(viewLifecycleOwner, Observer<LiveDataResult<MutableList<New>>>{
            when(it?.status) {
                LiveDataResult.STATUS.ERROR -> {

                }

                LiveDataResult.STATUS.SUCCESS -> {
                    loadRecyclerView()
                    newList.addAll(it.data as ArrayList<New>)
                    visibilityView(false, progress_bar_news)
                }

                LiveDataResult.STATUS.LOADING -> {
                    visibilityView(true, progress_bar_news)
                }
            }
        })
    }

    private fun loadRecyclerView() {
        highlightsAdapter = NewAdapter(highlightsList, this)
        rv_highlights.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rv_highlights.adapter = highlightsAdapter

        newAdapter = NewAdapter(newList, this)
        rv_news.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv_news.adapter = newAdapter

    }
    
    private fun initViewModel() {
        val viewModelFactory = FeedViewModelFactory(
            Dispatchers.Main,
            Dispatchers.IO,
            FeedUseCase(FeedDataSource(ApiService.createService(NewsApi::class.java)))
        )

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FeedViewModel::class.java)

        viewModel.getHighlights(token)
        viewModel.getNews(token, currentPage, perPage)
    }

    private fun visibilityView(
        status: Boolean,
        progressBar: ProgressBar
    ) {
        if(status) {
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.getHighlights(token).cancel()
        viewModel.getNews(token, currentPage, perPage).cancel()
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