package com.sorsix.intern.backend.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Book (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var name: String,
    var publishedYear: LocalDate,
    var imgUrl: String,
    var description: String,
    var isbn: String,
    @Column(name = "numpages")
    var numPages: Int,

    @ManyToOne
    var publishingHouse: PublishingHouse,

    @ManyToMany
    @JoinTable(
        name = "book_has",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")]
    )
    @JsonIgnoreProperties("books")
    var authors: MutableList<Author>? = null,

    @OneToMany(mappedBy = "book")
    var bookInLibrary: MutableList<BookInLibrary>? = null,

    @ManyToMany
    @JoinTable(
        name = "belongs",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    var categories: MutableList<Category>? = null,

    @ManyToMany(mappedBy = "books")
    var wishLists: MutableList<WishList>? = null,

    @OneToMany(mappedBy = "book")
    var reviews: MutableList<Review>? = null,

    @OneToMany(mappedBy = "book")
    var customerBooks: MutableList<CustomerBook>? = null
){
}