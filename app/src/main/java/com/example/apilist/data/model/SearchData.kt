package com.example.apilist.data.model

data class SearchData(
    val data: List<Card>,
    val has_more: Boolean,
    val next_page: String,
    val `object`: String,
    val total_cards: Int
)