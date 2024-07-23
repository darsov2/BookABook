package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
}
