package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.BookInTable
import com.sorsix.intern.backend.domain.*
import com.sorsix.intern.backend.repository.BookRepository
import com.sorsix.intern.backend.repository.WishListRepository
import com.sorsix.intern.backend.service.BookService
import com.sorsix.intern.backend.service.UserService
import com.sorsix.intern.backend.service.WishListService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WishListServiceImpl(
    val repository: WishListRepository,
    val customerService: UserService,
    val bookService: BookRepository,
    val wishListRepository: WishListRepository
) : WishListService {
    override fun findAllByIdContaining(wishListsId: List<Long>): MutableList<WishList> = repository.findAllByIdIn(wishListsId);
    override fun addBookToWishList(bookId: Long, customerId: Long) {
        val customer: Customer = customerService.findById(customerId) ?: run {
            throw Exception()
        }
        val book: Book = bookService.findByIdOrNull(bookId) ?: run {
            throw Exception()
        }

        val wishList: WishList = customer.wishList ?: run { WishList(id = null, dateAdded = LocalDate.now(), books = mutableListOf(), customer) }
        wishList.books.add(book)

        wishListRepository.save(wishList)

    }

    override fun bookExistInWishList(bookId: Long, customerId: Long) : Boolean {
        val customer: Customer = customerService.findById(customerId) ?: run {
            throw Exception()
        }
        val book: Book = bookService.findByIdOrNull(bookId) ?: run {
            throw Exception()
        }

        return customer.wishList?.books?.contains(book) ?: false
    }

    override fun findAllByCustomerId(customerId: Long): List<BookInTable> {
        val wishList = repository.findAllByCustomer_Id(customerId);
        return wishList.first().books.map {
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
        }
    }
}