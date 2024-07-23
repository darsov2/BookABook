package com.sorsix.intern.backend.domain

import com.sorsix.intern.backend.domain.Enum.Condition
import jakarta.persistence.*

@Entity
class BookInLibrary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @Enumerated(EnumType.STRING)
    @Column(name = "condition_book")
    var condition: Condition,
    @ManyToOne
    var libraryStore: LibraryStore?,
    @ManyToOne
    var book: Book?,
    @OneToMany(mappedBy = "bookInLibrary")
    var reservedBooks: MutableList<ReserveBook>? = null,
    @OneToMany(mappedBy = "bookInLibrary")
    var borrowedBooks: MutableList<BorrowBook>? = null,
    var isReserved: Boolean = false,
    var isLent: Boolean = false,
) {
}
