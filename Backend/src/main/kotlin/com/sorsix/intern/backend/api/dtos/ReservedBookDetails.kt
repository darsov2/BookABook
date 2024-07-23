package com.sorsix.intern.backend.api.dtos

import com.sorsix.intern.backend.domain.BookInLibrary
import com.sorsix.intern.backend.domain.LibraryStore
import java.time.LocalDate
import java.time.LocalDateTime

data class ReservedBookDetails(
    var customer: UserAvatar,
    var book: BookInTable,
    var dateFrom: LocalDate,
    var dateTo: LocalDate?,
    var id: Long,
    var store: String
)
