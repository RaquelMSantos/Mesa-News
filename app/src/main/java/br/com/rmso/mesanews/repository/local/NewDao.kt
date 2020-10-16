package br.com.rmso.mesanews.repository.local

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.rmso.mesanews.model.New

@Dao
interface NewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNew(new: New)

    @Update
    fun updateNew(new: New)

    @Query("DELETE FROM new_table where title = :nameNew")
    suspend fun deleteNew(nameNew: String)

    @Query("DELETE FROM new_table")
    suspend fun deleteAll()

    @Query("SELECT * from new_table")
    fun getNews(): LiveData<List<New>>

    @Query("SELECT * FROM new_table where title = :nameNew")
    fun searchNew(nameNew: String): LiveData<New>
}