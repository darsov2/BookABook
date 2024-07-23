package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.PublishingHouse

interface PublishingHouseService {
    fun findById(id: Long): PublishingHouse
    fun findAll(): List<PublishingHouse>
}