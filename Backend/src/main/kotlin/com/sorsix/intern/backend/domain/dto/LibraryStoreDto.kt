package com.sorsix.intern.backend.domain.dto

class LibraryStoreDto(
    val address: String,
    val name: String,
    val libraryId: Long,
    val imgUrl: String,
    val bookInLibrariesId: List<Long>
) {
}