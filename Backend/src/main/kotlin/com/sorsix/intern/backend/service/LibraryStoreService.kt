package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.api.dtos.StoreDetails
import com.sorsix.intern.backend.domain.LibraryStore
import com.sorsix.intern.backend.domain.dto.LibraryStoreDto

interface LibraryStoreService {
    fun findById(id: Long?) : LibraryStore?
    fun findAll() : List<LibraryStore>
    fun delete(id: Long) : LibraryStore?
    fun save(id: Long?, libraryStoreDto: LibraryStoreDto) : LibraryStore?
    fun getPopularStores(): List<StoreDetails>
}