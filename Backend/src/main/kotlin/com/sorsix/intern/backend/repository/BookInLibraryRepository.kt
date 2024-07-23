package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Book
import com.sorsix.intern.backend.domain.BookInLibrary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookInLibraryRepository : JpaRepository<BookInLibrary, Long> {
    fun findAllByIdIn(bookInLibrary: List<Long>) : MutableList<BookInLibrary>
    fun findAllByBookId(bookId: Long) : MutableList<BookInLibrary>
    fun findAllByLibraryStore_Id(id: Long) : List<BookInLibrary>

    fun findAllByBookIdAndIsReservedFalseAndIsLentFalse(bookId: Long) : MutableList<BookInLibrary>
    fun findAllByBookNameStartsWith(bookNameStartsWith: String) : List<BookInLibrary>

    fun findFirstByLibraryStore_IdAndBook_Id (libraryStore_id: Long, book_id: Long): BookInLibrary
    fun findAllByLibraryStore_IdAndBook_Id (libraryStore_id: Long, book_id: Long): List<BookInLibrary>
}