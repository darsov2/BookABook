package com.sorsix.intern.backend.domain.views

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import net.jcip.annotations.Immutable
import org.hibernate.annotations.Subselect

@Entity
@Table(name = "loans_in_last_days")
@Immutable
@Subselect("select * from loans_in_last_days")
data class LoansInLastDays(
    @Id
    var id: Long,
    var name: String,
    var day0: Long,
    var day1: Long,
    var day2: Long,
    var day3: Long,
    var day4: Long,
)
