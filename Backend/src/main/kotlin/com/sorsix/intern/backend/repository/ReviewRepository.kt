package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {
    fun findAllByIdIn(reviewsId: List<Long>) : MutableList<Review>
}