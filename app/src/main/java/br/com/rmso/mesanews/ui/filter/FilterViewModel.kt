package br.com.rmso.mesanews.ui.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.com.rmso.mesanews.model.New
import br.com.rmso.mesanews.repository.local.AppDatabase
import br.com.rmso.mesanews.repository.local.FavoritesRepository
import kotlinx.coroutines.launch

class FilterViewModel(application: Application): AndroidViewModel(application) {
    private val favoritesRepository: FavoritesRepository
    val allNews: LiveData<List<New>>

    init {
        val newDao = AppDatabase.getInstance(application, viewModelScope).newDao()
        favoritesRepository = FavoritesRepository(newDao)
        allNews = favoritesRepository.allNews
    }

    fun insert(new: New) = viewModelScope.launch {
        favoritesRepository.insert(new)
    }

    fun delete(title: String) = viewModelScope.launch {
        favoritesRepository.delete(title)
    }

    fun search(title: String): LiveData<New> {
        return favoritesRepository.search(title)
    }
}