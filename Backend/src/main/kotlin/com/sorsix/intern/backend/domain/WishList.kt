package com.sorsix.intern.backend.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToOne
import java.time.LocalDate

@Entity
class WishList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val dateAdded: LocalDate,
    @ManyToMany
    @JoinTable(
        name = "in_wish_list",
        joinColumns = [JoinColumn(name = "wish_list_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id")]
    )
    val books: MutableList<Book>,
    @OneToOne
    val customer: Customer

) {
}