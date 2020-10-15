package br.com.rmso.mesanews.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.rmso.mesanews.repository.remote.feed.FeedUseCase
import kotlinx.coroutines.CoroutineDispatcher
import java.lang.IllegalArgumentException

class FeedViewModelFactory constructor(
    private val mainDispather: CoroutineDispatcher,
    private val ioDispatcher: CoroutineDispatcher,
    private val feedUseCase: FeedUseCase)
    :ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            with(modelClass) {
                when {
                    isAssignableFrom(FeedViewModel::class.java) ->
                        FeedViewModel(mainDispather, ioDispatcher, feedUseCase)
                    else ->
                        throw IllegalArgumentException("unknown class")
                }
            } as T
}