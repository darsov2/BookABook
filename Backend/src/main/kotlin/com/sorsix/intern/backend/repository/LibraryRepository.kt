package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Library
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LibraryRepository : JpaRepository<Library, Long>{
}