package com.sorsix.intern.backend.domain.dto

import com.sorsix.intern.backend.domain.Enum.Condition

class BookInLibraryDto(
    var bookId: Long,
    var condition: Condition,
    var libraryStoreId: Long,
    var reservedBooksId: List<Long>,
    var borrowedBooksId: List<Long>
) {

}