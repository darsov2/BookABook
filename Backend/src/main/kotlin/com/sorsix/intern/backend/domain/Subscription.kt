package com.sorsix.intern.backend.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class Subscription(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val dateSubscribed: LocalDate,
    @ManyToOne
    val library: Library,
    @ManyToOne
    val customer: Customer
) {
}