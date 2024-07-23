package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.CustomerBook

interface CustomerBookService {
    fun findAllByIdContaining(customerBooksId: List<Long>) : MutableList<CustomerBook>
}