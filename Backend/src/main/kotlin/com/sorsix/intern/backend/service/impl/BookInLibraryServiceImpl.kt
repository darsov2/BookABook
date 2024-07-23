package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.AddCopy
import com.sorsix.intern.backend.domain.BookInLibrary
import com.sorsix.intern.backend.domain.dto.BookInLibraryDto
import com.sorsix.intern.backend.repository.BookInLibraryRepository
import com.sorsix.intern.backend.repository.BookRepository
import com.sorsix.intern.backend.repository.LibrarianRepository
import com.sorsix.intern.backend.repository.LibraryStoreRepository
import com.sorsix.intern.backend.service.*
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookInLibraryServiceImpl(
    val repository: BookInLibraryRepository,
    val bookRepository: BookRepository,
    val borrowedBookService: BorrowedBookService,
    val reservedBookService: ReservedBookService,
    val libraryStoreRepository: LibraryStoreRepository,
    private val bookInLibraryRepository: BookInLibraryRepository,
    private val librarianRepository: LibrarianRepository

) : BookInLibraryService {
    override fun findAllByIdContaining(bookInLibrariesId: List<Long>): MutableList<BookInLibrary> =
        repository.findAllByIdIn(bookInLibrariesId)

    override fun findById(id: Long): BookInLibrary? = repository.findById(id).get()

    override fun findAll(): List<BookInLibrary> = repository.findAll();
    override fun findAllByBookId(id: Long): List<BookInLibrary> {
        return bookInLibraryRepository.findAllByBookIdAndIsReservedFalseAndIsLentFalse(id)
    }

    override fun delete(id: Long): BookInLibrary? {
        return findById(id)?.let {
            repository.delete(it)
            it
        }
    }

    override fun save(id: Long?, bookInLibraryDto: BookInLibraryDto): BookInLibrary? {
        return if (id != null) {
            val bookInLibrary: BookInLibrary? = findById(id);
            bookInLibrary?.book = bookRepository.findById(bookInLibraryDto.bookId).get()
            bookInLibrary?.borrowedBooks =  borrowedBookService.findAllByIdContaining(bookInLibraryDto.borrowedBooksId)
            bookInLibrary?.reservedBooks =  reservedBookService.findAllByIdContaining(bookInLibraryDto.borrowedBooksId)
            bookInLibrary?.condition = bookInLibraryDto.condition
            bookInLibrary?.libraryStore = libraryStoreRepository.findById(bookInLibraryDto.libraryStoreId).get()
            bookInLibrary?.let { repository.save(it) }
        }
        else {
            repository.save(
                BookInLibrary(
                    id = null,
                    book = bookRepository.findById(bookInLibraryDto.bookId).get(),
                    borrowedBooks = borrowedBookService.findAllByIdContaining(bookInLibraryDto.borrowedBooksId),
                    reservedBooks = reservedBookService.findAllByIdContaining(bookInLibraryDto.borrowedBooksId),
                    condition = bookInLibraryDto.condition,
                    libraryStore = libraryStoreRepository.findById(bookInLibraryDto.libraryStoreId).get(),
                )
            )
        }
    }

    override fun addCopies(addCopy: AddCopy, userId: Long) {
        val storeId = librarianRepository.findLibraryIdByUserId(userId) ?: throw NotFoundException()
        val store = libraryStoreRepository.findByIdOrNull(storeId) ?: throw NotFoundException()
        val book = bookRepository.findByIdOrNull(addCopy.bookId) ?: throw NotFoundException();
        (0..addCopy.quantity).forEach {
            repository.save(BookInLibrary(
                id = 0,
                condition = addCopy.status,
                book = book,
                libraryStore = store
            ))
        }
    }

    override fun deleteCopy(id: Long) {
        val copy = bookInLibraryRepository.findByIdOrNull(id) ?: throw NotFoundException()
        if(copy.isLent || copy.isReserved) {
            throw RuntimeException("Cannot delete a book which is lent or reserved!")
        }
    }

}