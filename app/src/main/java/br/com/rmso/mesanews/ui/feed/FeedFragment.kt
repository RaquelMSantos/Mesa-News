package br.com.rmso.mesanews.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.rmso.mesanews.LiveDataResult
import br.com.rmso.mesanews.New
import br.com.rmso.mesanews.R
import br.com.rmso.mesanews.network.ApiService
import br.com.rmso.mesanews.network.NewsApi
import br.com.rmso.mesanews.repository.remote.feed.FeedDataSource
import br.com.rmso.mesanews.repository.remote.feed.FeedUseCase
import br.com.rmso.mesanews.ui.MainActivity
import br.com.rmso.mesanews.ui.NewAdapter
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay


class FeedFragment : Fragment() {

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
                    visibilityView(false)
                }

                LiveDataResult.STATUS.LOADING -> {
                    visibilityView(true)
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
                    visibilityView(false)
                }

                LiveDataResult.STATUS.LOADING -> {
                    visibilityView(true)
                }
            }
        })
    }

    private fun loadRecyclerView() {
        highlightsAdapter = NewAdapter(highlightsList)
        rv_highlights.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rv_highlights.adapter = highlightsAdapter

        newAdapter = NewAdapter(newList)
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

    private fun visibilityView(status: Boolean) {
        if(status) {
            progress_bar_feed.visibility = View.VISIBLE
        }else{
            progress_bar_feed.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.getHighlights(token).cancel()
        viewModel.getNews(token, currentPage, perPage).cancel()
    }
}