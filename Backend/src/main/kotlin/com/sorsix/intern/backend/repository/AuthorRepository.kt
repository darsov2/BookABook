package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Author
import com.sorsix.intern.backend.domain.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<Author, Long> {
    fun findAllByIdIn(ids: List<Long>) : MutableList<Author>
    @Query("select a from Author a join PopularAuthor pa ON a.id = pa.id")
    fun findPopularAuthors(): List<Author>
}