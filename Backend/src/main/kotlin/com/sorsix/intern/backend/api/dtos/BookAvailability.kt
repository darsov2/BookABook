package com.sorsix.intern.backend.api.dtos

data class BookAvailability(
    var libraryId: Long?,
    var libraryName: String?,
    var storesAvailability: List<BookAvailableInStore>
)
