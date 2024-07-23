package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.Library

interface LibraryService {

    fun findById(id: Long): Library?
}