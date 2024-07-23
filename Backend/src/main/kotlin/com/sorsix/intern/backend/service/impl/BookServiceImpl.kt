package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.*
import com.sorsix.intern.backend.domain.*
import com.sorsix.intern.backend.repository.*
import com.sorsix.intern.backend.service.*
import jakarta.transaction.Transactional
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class BookServiceImpl(
    private val repository: BookRepository,
    private val publishingHouseService: PublishingHouseService,
    private val authorService: AuthorService,
    private val bookInLibraryService: BookInLibraryService,
    private val categoriesService: CategoriesService,
    private val wishListService: WishListService,
    private val reviewService: ReviewService,
    private val customerBookService: CustomerBookService,
    private val bookInLibraryRepository: BookInLibraryRepository,
    private val userRepository: CustomerRepository,
    private val librarianRepository: LibrarianRepository,
    private val bookRepository: BookRepository,
    private val borrowBookRepository: BorrowBookRepository,
    private val reserveBookRepository: ReserveBookRepository,
    private val mailingService: MailingService


    ) : BookService {

    override fun findAll(): List<Book> = repository.findAll()

    override fun findById(id: Long): Book? = repository.findById(id).get()

    override fun save(
        id: Long?,
        name: String,
        publishedYear: LocalDate,
        imgUrl: String,
        publishingHouseId: Long,
        authorsId: List<Long>,
        bookInLibraryId: List<Long>,
        categoriesId: List<Long>,
        wishListsId: List<Long>,
        reviewsId: List<Long>,
        customerBooksId: List<Long>,
        description: String
    ): Book? {
        return if (id != null) {
            val book: Book? = findById(id);
            book?.name = name
            book?.description = description
            book?.publishedYear = publishedYear
            book?.imgUrl = imgUrl
            book?.publishingHouse = publishingHouseService.findById(publishingHouseId)
            book?.authors = authorService.findAllByIdContaining(authorsId);
            book?.bookInLibrary = bookInLibraryService.findAllByIdContaining(bookInLibraryId)
            book?.categories = categoriesService.findAllByIdContaining(categoriesId)
            book?.wishLists = wishListService.findAllByIdContaining(wishListsId)
            book?.reviews = reviewService.findAllByIdContaining(reviewsId)
            book?.customerBooks = customerBookService.findAllByIdContaining(customerBooksId)
            book?.let { repository.save(it) }
        } else {
            repository.save(
                Book(
                    null, name = name, publishedYear = publishedYear, imgUrl = imgUrl,
                    authors = authorService.findAllByIdContaining(authorsId),
                    bookInLibrary = bookInLibraryService.findAllByIdContaining(bookInLibraryId),
                    categories = categoriesService.findAllByIdContaining(categoriesId),
                    wishLists = wishListService.findAllByIdContaining(wishListsId),
                    reviews = reviewService.findAllByIdContaining(reviewsId),
                    customerBooks = customerBookService.findAllByIdContaining(customerBooksId),
                    publishingHouse = publishingHouseService.findById(publishingHouseId),
                    description = description,
                    isbn = "",
                    numPages = 0
                )
            )
        }
    }

    override fun delete(id: Long): Book? {
        return findById(id)?.let {
            repository.delete(it)
            it
        }
    }

    override fun findAllByIdContaining(booksId: List<Long>): MutableList<Book> = repository.findAllByIdIn(booksId)
    override fun findAllBooksForTable(): List<BookInTable> {
        val books = repository.findAll();
        return mapBookToBookTable(books);
    }

    private fun mapBookToBookTable(books: List<Book>): List<BookInTable> {
        return books.map { it ->
            BookInTable(
                id = it.id,
                name = it.name,
                publisher = it.publishingHouse.name,
                authors = it.authors?.map { author: Author ->
                    "${author.name} ${author.lastName}"
                }?.toList(),
                categories = it.categories?.map { category: Category ->
                    category.name
                }?.toList(),
                isbn = it.isbn,
                imgUrl = it.imgUrl,
                averageRating = it.reviews.takeIf { !it.isNullOrEmpty() }?.map { it.rate }?.average() ?: 0.0,
                description = it.description
            )
        }.toList();
    }

    override fun findAllByLetter(letter: String): List<BookInTable> {
        val books = repository.findAllByNameStartsWith(letter)
        return mapBookToBookTable(books);
    }

    override fun findBookCardsByLetters(): Map<Char, List<BookCard>> {
        val books = repository.findAll();
        return books.groupBy { it.name.first() }.mapValues { (k, v) ->
            v.shuffled().take(12).map {
                BookCard(
                    id = it.id ?: 0,
                    name = it.name,
                    authors = it.authors?.map { it.name + " " + it.lastName}?.toList(),
                    imgUrl = it.imgUrl,
                )
            };
        }
    }
    override fun findAvailableBooksByLetter(letter: Char?): Map<Char, List<AvailableBooks>> {
        val books = bookInLibraryRepository.findAllByBookNameStartsWith(letter?.toString() ?: "");
        return books.groupBy { it.book?.name?.first() ?: ' ' }.mapValues { (k, v) ->
            v.groupBy { book -> book.book!! }.map {
                AvailableBooks(
                    id = it.key.id!!,
                    name = it.key.name,
                    authors = it.key.authors?.map { it.name + " " + it.lastName } ?: emptyList(),
                    categories = it.key.categories?.map { it.name } ?: emptyList(),
                    isbn = it.key.isbn,
                    imgUrl = it.key.imgUrl,
                    bookCopies = it.value.map { AvailableBook(
                        id = it.id!!,
                        status = it.condition.toString()
                    ) }
                )
            }
        }
    }
    override fun findBookCardsByLetter(letter: Char): Map<Char, List<BookCard>> {
        val books = repository.findAllByNameStartsWith(letter.toString())
        return books.groupBy { it.name.first() }.mapValues { (k, v) ->
            v.shuffled().take(12).map {
                BookCard(
                    id = it.id ?: 0,
                    name = it.name,
                    authors = it.authors?.map { it.name + " " + it.lastName}?.toList(),
                    imgUrl = it.imgUrl,
                )
            };
        }
    }

    override fun getBookDetailsById(id: Long): BookInTable? {
        return repository.findByIdOrNull(id)?.let { BookInTable(
            id = it.id,
            name = it.name,
            isbn = it.isbn,
            publisher = it.publishingHouse.name,
            authors = it.authors?.map { author -> author.name + " " + author.lastName }?.toList(),
            categories = it.categories?.map { category -> category.name }?.toList(),
            imgUrl = it.imgUrl,
            averageRating = it.reviews.takeIf { review ->
                println(review)
                !review.isNullOrEmpty() }?.map { review -> review.rate }?.average() ?: 0.0,
            description = it.description
        ) }
    }

    override fun addBook(book: AddBook): Book {
        val categories = categoriesService.findAllByIdContaining(book.categories)
        val authors = authorService.findAllByIdContaining(book.authors)
        val publishingHouse = publishingHouseService.findById(book.publisher)
        val bookToAdd = Book(
            name = book.name,
            isbn = book.isbn,
            description = book.description,
            imgUrl = book.imgUrl,
            numPages = book.numberOfPages,
            authors = authors,
            categories = categories,
            publishingHouse = publishingHouse,
            publishedYear = LocalDate.now()
        )
        return bookRepository.save(bookToAdd);
    }

    private fun getStatusCodeForQuantity(quantity: Int): Int {
        return when {
            quantity > 5 -> 1
            quantity > 0 -> 0
            else -> -1
        }
    }

    override fun getBookAvailability(id: Long): List<BookAvailability>? {
        val availableBooks = bookInLibraryService.findAllByBookId(id);
        return availableBooks.groupBy { it.libraryStore?.library }.map { BookAvailability(
            libraryId = it.key?.id,
            libraryName = it.key?.name,
            storesAvailability = it.value.groupBy { store -> store.libraryStore }.map { storeAvailability ->
                BookAvailableInStore(
                storeId = storeAvailability.key?.id,
                storeName = storeAvailability.key?.name,
                storeAddress = storeAvailability.key?.address,
                quantity = storeAvailability.value.size,
                statusCode = getStatusCodeForQuantity(storeAvailability.value.size),
            ) }.toList()
        ) }.toList()
    }
    @Transactional
    override fun lendBook(userId: Long, bookId: Long) {
        val book = bookInLibraryRepository.findByIdOrNull(bookId) ?: throw NotFoundException()
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundException()
        book.isLent = true;
//        val librarian = librarianRepository.findByIdOrNull(1) ?: throw NotFoundException()

        val newBorrowing = BorrowBook(
            id = 0,
            bookInLibrary = book,
            customer = user,
            dateFrom = LocalDate.now(),
            dateTo = LocalDate.now(),
            librarian =  null,
        )

        bookInLibraryRepository.save(book);
        borrowBookRepository.save(newBorrowing);
    }

    @Scheduled(cron = "*/30 * * * * *")
    @Transactional
    fun deleteExpiredReservations() {
        val expiredReservations = reserveBookRepository.findAllExpired()
        expiredReservations.forEach { it.dateTo = LocalDate.now() }
        reserveBookRepository.saveAll(expiredReservations);
    }

    override fun getPopularBooks(): List<BookCard> {
        return bookRepository.findPopularBookDetails().map {
            BookCard(
            id = it.id ?: 0,
            name = it.name ?: "",
            authors = it.authors?.map { it.name + " " + it.lastName }?.toList(),
            imgUrl = it.imgUrl ?: "",
        ) };
    }

    override fun getBookCopies(bookId: Long, userId: Long): List<AvailableBook> {
        val storeId = librarianRepository.findLibraryIdByUserId(userId) ?: throw NotFoundException()

        val bookCopies = bookInLibraryRepository.findAllByLibraryStore_IdAndBook_Id(storeId, bookId)
        return bookCopies.map {
            AvailableBook(
                id = it.id ?: 0,
                status = it.condition.toString()
            )
        }
    }
}