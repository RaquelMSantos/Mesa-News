package br.com.rmso.mesanews.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.rmso.mesanews.model.New
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [New::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun newDao(): NewDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getInstance(
            context: Context,
            scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "new_database"
                )
                    .addCallback(NewDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class NewDatabaseCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { appDatabase ->
                scope.launch {
                    populateDatabase(appDatabase.newDao())
                }
            }
        }

        suspend fun populateDatabase(newDao: NewDao) {
            val new = New(null, "News 1 title", "News 1 description",
            "News 1 content", "News 1 author",
             "2020-01-30T13:45:00.000Z", false,
          "https://fake.news", "https://via.placeholder.com/600x300")
        }
    }
}