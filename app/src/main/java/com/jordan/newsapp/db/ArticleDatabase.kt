package com.jordan.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jordan.newsapp.model.Article

@Database(
        entities = [Article::class],
        version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    //一個好的單例，必然需要滿足唯一性和線程安全性
    // 帶參單例模式，可以使用雙重檢查鎖 + @Volatile 來實現
    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        // ?: 如果左邊不是空值的話直接回傳左邊的內容，否則回傳右邊的內容
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    "article_db.db"
            ).build()

    }
}