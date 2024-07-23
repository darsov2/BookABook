package com.sorsix.intern.backend.api.dtos

import java.time.LocalDate

data class WishlistBookDetails(
    var book: BookInTable,
    var id: Long,
)
