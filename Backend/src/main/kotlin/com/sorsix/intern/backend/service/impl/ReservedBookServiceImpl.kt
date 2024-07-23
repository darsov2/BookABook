package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.BookInTable
import com.sorsix.intern.backend.api.dtos.LentBookDetails
import com.sorsix.intern.backend.api.dtos.ReservedBookDetails
import com.sorsix.intern.backend.api.dtos.UserAvatar
import com.sorsix.intern.backend.domain.*
import com.sorsix.intern.backend.repository.*
import com.sorsix.intern.backend.service.ReservedBookService
import com.sorsix.intern.backend.service.UserService
import jakarta.transaction.Transactional
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReservedBookServiceImpl(
    val repository: ReserveBookRepository,
    val bookRepository: BookRepository,
    val libraryStoreRepository: LibraryStoreRepository,
    val userService: UserService,
    val bookInLibraryRepository: BookInLibraryRepository,
    private val librarianRepository: LibrarianRepository,
    private val borrowBookRepository: BorrowBookRepository,
) : ReservedBookService {
    override fun findAllByIdContaining(reservedBooks: List<Long>): MutableList<ReserveBook> =
        repository.findAllByIdIn(reservedBooks);

    override fun reserveBook(bookId: Long, storeId: Long, userId: Long) {

        val customer: Customer = userService.findById(userId) ?: run {
            throw Exception()
        }

        val bookInLibrary: BookInLibrary = bookInLibraryRepository.findFirstByLibraryStore_IdAndBook_Id(storeId, bookId)

        repository.save(
            ReserveBook(
                id = 0,
                dateFrom = LocalDate.now(),
                dateTo = null,
                customer = customer,
                bookInLibrary = bookInLibrary
            )
        )
    }

    override fun reservationExist(bookId: Long, userId: Long): Boolean {
        return repository.countByCustomer_IdAndBookInLibrary_Book_IdAndDateToNull(
            customerId = userId,
            bookId = bookId
        ) > 0
    }

    override fun findAllByCustomerId(customerId: Long): List<ReservedBookDetails> {
        val books = repository.findAllByCustomer_Id(customerId)
        return books.map {
            ReservedBookDetails(
                dateFrom = it.dateFrom,
                dateTo = it.dateTo,
                id = it.id,
                customer = UserAvatar(
                    id = it.customer.id,
                    name = it.customer.name,
                    email = it.customer.email,
                    imgUrl = it.customer.imgUser ?: "",
                    lastName = ""
                ),
                book = BookInTable(
                    id = it.bookInLibrary.book?.id,
                    name = it.bookInLibrary.book?.name,
                    description = it.bookInLibrary.book?.description ?: "",
                    imgUrl = it.bookInLibrary.book?.imgUrl,
                    isbn = it.bookInLibrary.book?.isbn ?: "",
                    publisher = it.bookInLibrary.book?.publishingHouse?.name ?: ""
                ),
                store = it.bookInLibrary.libraryStore?.name ?: ""
            )
        }
    }

    override fun cancelReservation(reservationId: Long) {
        val reservation = repository.findByIdOrNull(reservationId) ?: throw RuntimeException("Reservation with id $reservationId not found")
        reservation.dateTo = LocalDate.now()
        repository.save(reservation)
    }

    override fun getReservationsForStore(userId: Long): List<LentBookDetails> {
        val storeId = librarianRepository.findLibraryIdByUserId(userId) ?: throw NotFoundException()
        val books = repository.findAllByBookInLibrary_LibraryStore_Id(storeId)

        return books.map {  LentBookDetails(
            dateFrom = it.dateFrom,
            dateTo = it.dateTo,
            id = it.id,
            customer = UserAvatar(
                id = it.customer.id,
                name = it.customer.name,
                email = it.customer.email,
                imgUrl = it.customer.imgUser ?: "",
                lastName = ""
            ),
            book = BookInTable(
                id = it.bookInLibrary.book?.id,
                name = it.bookInLibrary.book?.name,
                description = it.bookInLibrary.book?.description ?: "",
                imgUrl = it.bookInLibrary.book?.imgUrl,
                isbn = it.bookInLibrary.book?.isbn ?: "",
                publisher = it.bookInLibrary.book?.publishingHouse?.name ?: ""
            )
        ) }
    }

    @Transactional
    override fun finishReservation(userId: Long, reservationId: Long) {
        val reservation = repository.findByIdOrNull(reservationId) ?: throw NotFoundException()
        val storeId = librarianRepository.findLibraryIdByUserId(userId) ?: throw NotFoundException()
        reservation.dateTo = LocalDate.now()
        reservation.bookInLibrary.isReserved = false
        reservation.bookInLibrary.isLent = true
        val borrow = BorrowBook(
            id = 0,
            dateFrom = LocalDate.now(),
            dateTo = LocalDate.now().plusYears(1),
            bookInLibrary = reservation.bookInLibrary,
            customer = reservation.customer,
            librarian = null
        )
        borrowBookRepository.save(borrow)
        repository.save(reservation)
    }
}