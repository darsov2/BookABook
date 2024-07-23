package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.Library
import com.sorsix.intern.backend.repository.LibraryRepository
import com.sorsix.intern.backend.service.LibraryService
import org.springframework.stereotype.Service

@Service
class LibraryServiceImpl(val repository: LibraryRepository) : LibraryService {
    override fun findById(id: Long): Library? = repository.findById(id).get()
}