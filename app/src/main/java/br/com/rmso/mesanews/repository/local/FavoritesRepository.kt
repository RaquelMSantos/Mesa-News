package br.com.rmso.mesanews.repository.local

import androidx.lifecycle.LiveData
import br.com.rmso.mesanews.model.New

class FavoritesRepository (private val newDao: NewDao) {
    val allNews: LiveData<List<New>> = newDao.getNews()

    suspend fun insert(new: New) {
        newDao.insertNew(new)
    }

    suspend fun delete(title: String) {
        newDao.deleteNew(title)
    }

    fun search(title: String): LiveData<New>{
        return newDao.searchNew(title)
    }
}