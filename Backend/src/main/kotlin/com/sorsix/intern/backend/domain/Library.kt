package com.sorsix.intern.backend.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Library(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val number: String,
    @OneToMany(mappedBy = "library")
    val libraryStore: List<LibraryStore>,
    @OneToMany(mappedBy = "library")
    val subscriptions: List<Subscription>
) {
}