package com.sorsix.intern.backend.api.dtos

data class BookCard(
    var id: Long,
    var name: String,
    var authors: List<String>?,
    var imgUrl: String
) {}