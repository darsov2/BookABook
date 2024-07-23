package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.CustomerBook
import com.sorsix.intern.backend.repository.CustomerBookRepository
import com.sorsix.intern.backend.service.CustomerBookService
import org.springframework.stereotype.Service

@Service
class CustomerBookServiceImpl(val repository: CustomerBookRepository) : CustomerBookService {
    override fun findAllByIdContaining(customerBooksId: List<Long>): MutableList<CustomerBook> = repository.findAllByIdIn(customerBooksId)
}