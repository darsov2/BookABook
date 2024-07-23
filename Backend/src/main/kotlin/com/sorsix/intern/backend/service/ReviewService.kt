package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.api.dtos.AddReview
import com.sorsix.intern.backend.domain.Review

interface ReviewService {
    fun findAllByIdContaining(reviewsId: List<Long>) : MutableList<Review>
    fun createReview(bookId: Long, review: AddReview)
    fun getReviewsByBook(bookId: Long): List<AddReview>?
}