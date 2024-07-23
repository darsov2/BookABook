package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.views.LoansInLastDays
import org.springframework.data.jpa.repository.JpaRepository

interface LoansInLastDaysRepository : JpaRepository<LoansInLastDays, Long> {
}