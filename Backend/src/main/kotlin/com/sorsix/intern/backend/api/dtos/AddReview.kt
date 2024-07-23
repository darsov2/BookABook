package com.sorsix.intern.backend.api.dtos

import java.time.LocalDate

data class AddReview(
    var value: Float?,
    var description: String?,
    var dateReviewed: LocalDate?,
    var userId: Long?,
) {
}
