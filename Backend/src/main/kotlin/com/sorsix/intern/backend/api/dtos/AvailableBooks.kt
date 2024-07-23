package com.sorsix.intern.backend.api.dtos

import java.net.URL

data class AvailableBooks(
    val id: Long,
    val name: String,
    val isbn: String,
    val imgUrl: String,
    val authors: List<String>,
    val categories: List<String>,
    val bookCopies: List<AvailableBook>
) {}