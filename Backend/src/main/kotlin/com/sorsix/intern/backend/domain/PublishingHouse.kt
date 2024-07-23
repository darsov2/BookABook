package com.sorsix.intern.backend.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class PublishingHouse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val address: String,
    @JsonIgnore
    @OneToMany(mappedBy = "publishingHouse")
    val books: List<Book>
) {
}