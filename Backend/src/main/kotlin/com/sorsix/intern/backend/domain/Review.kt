package com.sorsix.intern.backend.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val dateReviewed: LocalDate,
    val description: String,
    val rate: Float,
    @ManyToOne
    val customer: Customer?,
    @ManyToOne
    val book: Book?
) {
}