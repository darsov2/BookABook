package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.PublishingHouse
import com.sorsix.intern.backend.repository.PublishingHouseRepository
import com.sorsix.intern.backend.service.PublishingHouseService
import org.springframework.stereotype.Service

@Service
class PublishingHouseServiceImpl(val repository: PublishingHouseRepository) : PublishingHouseService {
    override fun findById(id: Long): PublishingHouse = repository.findById(id).get()
    override fun findAll(): List<PublishingHouse> = repository.findAll()
}