package com.sorsix.intern.backend.domain

import jakarta.persistence.*

@Entity
class Trade(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    val customer: Customer,

    @ManyToOne
    val customerBook: CustomerBook,


) {
}