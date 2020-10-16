package br.com.rmso.mesanews.ui.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.rmso.mesanews.utils.LiveDataResult
import br.com.rmso.mesanews.model.New
import br.com.rmso.mesanews.model.Pagination
import br.com.rmso.mesanews.repository.remote.feed.FeedUseCase
import kotlinx.coroutines.*

class FeedViewModel(mainDispacher: CoroutineDispatcher,
                    ioDispatcher: CoroutineDispatcher,
                    private val feedUseCase: FeedUseCase
): ViewModel() {
    private val job = SupervisorJob()
    val highlightLiveData = MutableLiveData<LiveDataResult<MutableList<New>>>()
    val newLiveData = MutableLiveData<LiveDataResult<MutableList<New>>>()
    private val uiScope = CoroutineScope(mainDispacher + job)
    private val ioScope = CoroutineScope(ioDispatcher + job)

    private var newList = ArrayList<New>()
    private var highlightsList = ArrayList<New>()

    fun getHighlights(authManager: String):Job  {
        return uiScope.launch {
            while (isActive) {
                highlightLiveData.value = LiveDataResult.loading()

                try {
                    highlightsList.clear()
                    val highlights = fetchHighlights(authManager)
                    highlightsList.addAll(highlights)

                    highlightLiveData.value = LiveDataResult.success(highlightsList)
                } catch (e: Exception) {
                    highlightLiveData.value = LiveDataResult.error(e)
                }
                delay(30000)
            }
        }
    }

    fun getNews(authManager: String, currentPage: Int, perPage: Int):Job {
        return uiScope.launch {
            while (isActive) {
                newLiveData.value = LiveDataResult.loading()

                try {
                    newList.clear()
                    val pagination = fetchPagination(authManager, currentPage, perPage)
                    val totalPage = pagination.total_pages
                    newList.clear()

                    for (page in 1 until totalPage + 1) {
                        val news = fetchNews(authManager, page, pagination.per_page)
                        newList.addAll(news)
                    }

                    newList.sortBy { it.published_at }

                    newLiveData.value = LiveDataResult.success(newList)
                } catch (e: Exception) {
                    newLiveData.value = LiveDataResult.error(e)
                }
                delay(30000)
            }
        }
    }

    private suspend fun fetchNews(authManager: String, currentPage: Int, perPage: Int): MutableList<New> {
        return ioScope.async {
            return@async feedUseCase.fetchNews(authManager, currentPage, perPage)
        }.await()
    }

    private suspend fun fetchPagination(authManager: String, currentPage: Int, perPage: Int): Pagination {
        return ioScope.async {
            return@async feedUseCase.fetchPagination(authManager, currentPage, perPage)
        }.await()
    }

    private suspend fun fetchHighlights(authManager: String): MutableList<New> {
        return ioScope.async {
            return@async feedUseCase.fetchHighlights(authManager)
        }.await()
    }

}