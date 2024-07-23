package com.sorsix.intern.backend.api.dtos

import com.sorsix.intern.backend.domain.BookInLibrary
import java.time.LocalDate
import java.time.LocalDateTime

data class LentBookDetails(
    var customer: UserAvatar,
    var book: BookInTable,
    var dateFrom: LocalDate,
    var dateTo: LocalDate?,
    var id: Long,
)
