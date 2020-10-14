package br.com.rmso.mesanews

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "new_table")
data class New (
    var title: String,
    var description: String,
    var content: String,
    var author: String,
    var published_at: String,
    var highlight: Boolean,
    var url: String,
    var image_url: String
)