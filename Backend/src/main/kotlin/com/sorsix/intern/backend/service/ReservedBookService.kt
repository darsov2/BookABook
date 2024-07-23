package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.api.dtos.LentBookDetails
import com.sorsix.intern.backend.api.dtos.ReservedBookDetails
import com.sorsix.intern.backend.domain.ReserveBook
import jakarta.transaction.Transactional

interface ReservedBookService {
    fun findAllByIdContaining(reservedBooks: List<Long>) : MutableList<ReserveBook>
    fun reserveBook(bookId: Long, storeId: Long, userId: Long)

    fun reservationExist(bookId: Long, userId: Long): Boolean
    fun findAllByCustomerId(customerId: Long): List<ReservedBookDetails>
    fun cancelReservation(reservationId: Long)
    fun getReservationsForStore(userId: Long): List<LentBookDetails>
    @Transactional
    fun finishReservation(userId: Long, reservationId: Long)
}