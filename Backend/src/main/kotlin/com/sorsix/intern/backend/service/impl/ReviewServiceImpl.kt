package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.AddReview
import com.sorsix.intern.backend.domain.Book
import com.sorsix.intern.backend.domain.Review
import com.sorsix.intern.backend.repository.BookRepository
import com.sorsix.intern.backend.repository.ReviewRepository
import com.sorsix.intern.backend.service.ReviewService
import com.sorsix.intern.backend.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReviewServiceImpl(
    val repository: ReviewRepository,
    val bookRepository: BookRepository,
    val userService: UserService) : ReviewService {
    override fun findAllByIdContaining(reviewsId: List<Long>): MutableList<Review> = repository.findAllByIdIn(reviewsId)
    override fun createReview(bookId: Long, review: AddReview): Unit {
        var book: Book? = bookRepository.findByIdOrNull(bookId)
        val newReview: Review = Review(id = null, dateReviewed = LocalDate.now(), description = review.description ?: "",
            rate = review.value ?: 0.toFloat(), customer =  userService.findById(review.userId ?: 0), book = book)
        repository.save(newReview)
    }

    override fun getReviewsByBook(bookId: Long) : List<AddReview>? {
        val book: Book? = bookRepository.findByIdOrNull(bookId);
        return book?.reviews?.map {
            AddReview(userId = it.customer?.id, value = it.rate, dateReviewed = it.dateReviewed, description = it.description)
        }
    }

}