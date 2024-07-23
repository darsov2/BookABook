package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.api.dtos.*
import com.sorsix.intern.backend.config.CurrentUser
import com.sorsix.intern.backend.security.UserPrincipal
import com.sorsix.intern.backend.service.BookService
import com.sorsix.intern.backend.service.ReservedBookService
import com.sorsix.intern.backend.service.ReviewService
import com.sorsix.intern.backend.service.WishListService
import com.sorsix.intern.backend.service.impl.BookInLibraryServiceImpl
import com.sorsix.intern.backend.service.impl.BorrowedBookServiceImpl
import com.sorsix.intern.backend.service.impl.CategoryServiceImpl
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/books")
class BooksController(
    private val bookService: BookService,
    private val reviewService: ReviewService,
    private val wishListService: WishListService,
    private val reservedBookService: ReservedBookService,
    private val borrowedBookService: BorrowedBookServiceImpl,
    private val categoryService: CategoryServiceImpl,
    private val bookInLibraryService: BookInLibraryServiceImpl
) {
    @GetMapping("")
    fun getBooks(): List<BookInTable> {
        return bookService.findAllBooksForTable()
    }

    @GetMapping("getTopByLetters")
    fun getTopByLetters() = bookService.findBookCardsByLetters();

    @GetMapping("getAllByLetters")
    fun getAllByLetters(@RequestParam(required = false) letter: Char?): Map<Char, List<AvailableBooks>> {
        return if(letter == null) bookService.findAvailableBooksByLetter(null)
        else bookService.findAvailableBooksByLetter(letter);
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long) = bookService.getBookDetailsById(id)

    @PostMapping("/add")
    fun addBook(@RequestBody book: AddBook): Unit {
        bookService.addBook(book);
    }

    @GetMapping("/availability/{id}")
    fun getBookAvailability(@PathVariable id: Long) = bookService.getBookAvailability(id)

    @DeleteMapping("/delete/{id}")
    fun deleteBook(@PathVariable id: Long) = bookService.delete(id)

    @PostMapping("/review/{id}")
    fun leaveReview(@PathVariable id: Long, @RequestBody review: AddReview) = reviewService.createReview(id, review)
    
    @GetMapping("/reviews/{id}")
    fun getReviewsByBook(@PathVariable id: Long) = reviewService.getReviewsByBook(id);

    @PostMapping("/lend")
    fun lendBook(@RequestBody lendBook: LendBook) {
        bookService.lendBook(lendBook.userId, lendBook.copyId)
    }

    @GetMapping("/wishlist/add/{id}")
    fun addBookToWishList(@PathVariable id: Long) {
        wishListService.addBookToWishList(id, 1)
    }

    @GetMapping("/wishlist/exist/{id}")
    fun bookExistInWishList(@PathVariable id: Long) : Boolean =
        wishListService.bookExistInWishList(id, 1)

    @GetMapping("/reserve/{bookId}/{storeId}")
    fun reserveBook(@PathVariable bookId: Long, @PathVariable storeId: Long){
        return reservedBookService.reserveBook(bookId = bookId, storeId = storeId, userId = 1)
    }

    @GetMapping("/reserve/exist/{bookId}")
    fun reservationExist(@PathVariable bookId: Long) : Boolean{
        return reservedBookService.reservationExist(bookId = bookId, userId = 1)
    }

    @GetMapping("/lent/active")
    fun getActiveLendingsByStoreId(@CurrentUser userPrincipal: UserPrincipal): List<LentBookDetails> {
        return borrowedBookService.findAllActiveByStoreId(userPrincipal.id);
    }

    @PostMapping("/lent/{lendingId}/finish")
    fun finishLending(@PathVariable lendingId: Long) {
        borrowedBookService.finishLending(lendingId)
    }

    @GetMapping("/reservationsForUser")
    fun getReservationsForUser(@CurrentUser userPrincipal: UserPrincipal): List<ReservedBookDetails> {
        return reservedBookService.findAllByCustomerId(userPrincipal.id)
        }

    @PostMapping("/reservations/{reservationId}/cancel")
    fun cancelReservation(@PathVariable reservationId: Long) {
        reservedBookService.cancelReservation(reservationId)
    }

    @GetMapping("/wishlist")
    fun getWishListForCustomer(@CurrentUser userPrincipal: UserPrincipal): List<BookInTable> {
        return wishListService.findAllByCustomerId(userPrincipal.id);
    }

    @GetMapping("/loans")
    fun getLoansForUser(@CurrentUser userPrincipal: UserPrincipal): List<BookCard> {
        return borrowedBookService.findAllActiveByCustomerId(userPrincipal.id);
    }

    @GetMapping("/popular")
    fun getPopularBooks(): List<BookCard> {
        return bookService.getPopularBooks();
    }

    @GetMapping("/{bookId}/copies")
    fun getCopies(@PathVariable bookId: Long, @CurrentUser userPrincipal: UserPrincipal): List<AvailableBook> {
        return bookService.getBookCopies(bookId, userPrincipal.id)
    }

    @PostMapping("/copies/add")
    fun addCopies(@RequestBody addCopy: AddCopy, @CurrentUser userPrincipal: UserPrincipal) {
        return bookInLibraryService.addCopies(addCopy, userPrincipal.id)
    }

    @GetMapping("/copies/{id}/delete")
    fun deleteCopy(@PathVariable id: Long) {
        return bookInLibraryService.deleteCopy(id)
    }

    @GetMapping("/reservations")
    fun getReservationsForStore(@CurrentUser userPrincipal: UserPrincipal): List<LentBookDetails> {
        return reservedBookService.getReservationsForStore(userPrincipal.id)
    }

    @GetMapping("/reservations/{id}/finish")
    fun finishReservation(@PathVariable id: Long, @CurrentUser userPrincipal: UserPrincipal) {
        reservedBookService.finishReservation(id, userPrincipal.id)
    }
}