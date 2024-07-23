package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.BookCard
import com.sorsix.intern.backend.api.dtos.BookInTable
import com.sorsix.intern.backend.api.dtos.LentBookDetails
import com.sorsix.intern.backend.api.dtos.UserAvatar
import com.sorsix.intern.backend.domain.BorrowBook
import com.sorsix.intern.backend.repository.BorrowBookRepository
import com.sorsix.intern.backend.repository.LibrarianRepository
import com.sorsix.intern.backend.service.BorrowedBookService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class BorrowedBookServiceImpl(
    val repository: BorrowBookRepository,
    private val librarianRepository: LibrarianRepository
) : BorrowedBookService {
    override fun findAllByIdContaining(borrowedBooksId: List<Long>): MutableList<BorrowBook> =
        repository.findAllByIdIn(borrowedBooksId);

    override fun findAllActiveByStoreId(userId: Long): List<LentBookDetails> {
        val storeId = librarianRepository.findLibraryIdByUserId(userId) ?: throw NotFoundException()
        val books = repository.findAllByBookInLibrary_LibraryStore_IdAndDateToIsNull(storeId)
        return books.map {
            LentBookDetails(
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
            )
        }
    }

    override fun finishLending(lendingId: Long) {
        val book = repository.findByIdOrNull(lendingId) ?: throw RuntimeException()
        book.dateTo = LocalDate.now()
        repository.save(book)
    }

    override fun findAllActiveByCustomerId(userId: Long): List<BookCard> {
        val books = repository.findAllByCustomer_Id(userId)
        return books.map { it.bookInLibrary.book }
            .map {
                BookCard(
                    id = it?.id ?: 0,
                    name = it?.name ?: "",
                    authors = it?.authors?.map { it.name + " " + it.lastName }?.toList(),
                    imgUrl = it?.imgUrl ?: "",
                )
            }
    }
}