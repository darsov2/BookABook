package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    @Query("select u from User u where u.id = :id")
    fun findByIdKt(id: Long): User?
}