package com.sorsix.intern.backend.domain

import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.PrimaryKeyJoinColumn

@Entity
@PrimaryKeyJoinColumn
class Librarian(
    @OneToMany(mappedBy = "librarian")
    var borrowedBooks: MutableList<BorrowBook>,
    @ManyToOne
    var libraryStore: LibraryStore
) : User() {
}