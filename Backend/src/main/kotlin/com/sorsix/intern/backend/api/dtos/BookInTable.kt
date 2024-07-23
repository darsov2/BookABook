package com.sorsix.intern.backend.api.dtos

data class BookInTable(
    var id: Long?,
    var name: String?,
    var isbn: String?,
    var publisher: String?,
    var authors: List<String>? = emptyList(),
    var categories: List<String>? = emptyList(),
    var imgUrl: String?,
    var averageRating: Double? = 0.0,
    var description: String,
) {
}