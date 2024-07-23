package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.api.dtos.BookCard
import com.sorsix.intern.backend.api.dtos.LentBookDetails
import com.sorsix.intern.backend.domain.BorrowBook

interface BorrowedBookService {
    fun findAllByIdContaining(borrowedBooksId: List<Long>) : MutableList<BorrowBook>
    fun findAllActiveByStoreId(storeId: Long): List<LentBookDetails>
    fun finishLending(lendingId: Long)
    fun findAllActiveByCustomerId(userId: Long): List<BookCard>
}