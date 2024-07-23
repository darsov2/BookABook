package com.sorsix.intern.backend.api.dtos

import com.sorsix.intern.backend.domain.Enum.Condition

data class AddCopy(
    var bookId: Long,
    var status: Condition,
    var quantity: Int
)
