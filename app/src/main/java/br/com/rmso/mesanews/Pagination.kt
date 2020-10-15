package br.com.rmso.mesanews

data class Pagination (
    val current_page: Int,
    var per_page: Int,
    var total_pages: Int,
    var total_items: Int
)