package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.CustomerBook
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerBookRepository : JpaRepository<CustomerBook, Long> {
    fun findAllByIdIn(customerBooksId: List<Long>) : MutableList<CustomerBook>
}