package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.api.dtos.BookInTable
import com.sorsix.intern.backend.domain.WishList

interface WishListService {
    fun findAllByIdContaining(wishListsId: List<Long>): MutableList<WishList>
    fun addBookToWishList(bookId: Long, customerId: Long)

    fun bookExistInWishList(bookId: Long, customerId: Long): Boolean
    fun findAllByCustomerId(customerId: Long): List<BookInTable>
}