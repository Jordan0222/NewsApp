package com.jordan.newsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// 最後要儲存到資料庫的物件
@Entity(tableName = "articles")
data class Article(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        val source: Source,
        val author: String,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: String,
        val content: String
)