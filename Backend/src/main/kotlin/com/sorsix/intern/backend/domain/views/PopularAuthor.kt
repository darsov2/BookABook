package com.sorsix.intern.backend.domain.views

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import net.jcip.annotations.Immutable
import org.hibernate.annotations.Subselect

@Entity
@Table(name = "popular_authors")
@Immutable
@Subselect("select * from popular_authors")
data class PopularAuthor(
    @Id
    val id: Long,
    val name: String,
    val lastName: String,
    val borrowCount: Long
)