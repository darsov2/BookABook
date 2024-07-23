package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.WishList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WishListRepository : JpaRepository<WishList, Long> {
    fun findAllByIdIn(wishListsId: List<Long>) : MutableList<WishList>
    fun findAllByCustomer_Id(customerId: Long) : MutableList<WishList>
}