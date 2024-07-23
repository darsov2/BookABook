package com.sorsix.intern.backend.api.dtos

data class AddBook(
    var name: String,
    var authors: List<Long>,
    var categories: List<Long>,
    var publisher: Long,
    var description: String,
    var numberOfPages: Int,
    var imgUrl: String,
    var isbn: String
) {}