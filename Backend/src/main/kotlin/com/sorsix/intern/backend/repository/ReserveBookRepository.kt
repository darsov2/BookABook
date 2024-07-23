package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.ReserveBook
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ReserveBookRepository : JpaRepository<ReserveBook, Long> {
    fun findAllByIdIn(reservedBooks: List<Long>) : MutableList<ReserveBook>

    @Query("select * from reserve_book r where age(now(), r.date_from) >= '2 days' and r.date_to is not null", nativeQuery = true)
    fun findAllExpired() : MutableList<ReserveBook>

    fun countByCustomer_IdAndBookInLibrary_Book_IdAndDateToNull(customerId: Long, bookId: Long): Int
    fun findAllByCustomer_Id(customerId: Long): List<ReserveBook>
    fun findAllByBookInLibrary_LibraryStore_Id(libraryStoreId: Long): List<ReserveBook>


}