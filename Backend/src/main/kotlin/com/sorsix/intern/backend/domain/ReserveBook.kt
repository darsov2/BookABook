package com.sorsix.intern.backend.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class ReserveBook(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    var id: Long,
    var dateFrom: LocalDate,
    var dateTo: LocalDate?,
    @ManyToOne
    var customer: Customer,
    @ManyToOne
    var bookInLibrary: BookInLibrary,
) {
}