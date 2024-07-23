package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun findAllByIdIn(booksId: List<Long>): MutableList<Book>
    fun findAllByNameStartsWith(nameStartsWith: String): List<Book>
    @Query("select b from Book b join PopularBook pb ON b.id = pb.id")
    fun findPopularBookDetails(): List<Book>
}