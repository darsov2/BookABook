package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.BorrowBook
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BorrowBookRepository : JpaRepository<BorrowBook, Long> {
    fun findAllByIdIn(borrowedBooksId: List<Long>): MutableList<BorrowBook>
    fun findAllByBookInLibrary_LibraryStore_IdAndDateToIsNull(storeId: Long): List<BorrowBook>
    fun findAllByCustomer_Id(userId: Long): List<BorrowBook>
}