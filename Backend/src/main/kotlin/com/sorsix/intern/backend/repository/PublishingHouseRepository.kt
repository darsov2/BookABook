package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.PublishingHouse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublishingHouseRepository : JpaRepository<PublishingHouse, Long> {
}