package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.StoreDetails
import com.sorsix.intern.backend.domain.LibraryStore
import com.sorsix.intern.backend.domain.dto.LibraryStoreDto
import com.sorsix.intern.backend.repository.LibraryStoreRepository
import com.sorsix.intern.backend.service.BookInLibraryService
import com.sorsix.intern.backend.service.LibraryService
import com.sorsix.intern.backend.service.LibraryStoreService
import jakarta.mail.Store
import org.springframework.stereotype.Service

@Service
class LibraryStoreServiceImpl(
    val repository: LibraryStoreRepository,
    val libraryService: LibraryService,
    val bookInLibraryService: BookInLibraryService
) : LibraryStoreService {

    override fun findById(id: Long?): LibraryStore? = id?.let { repository.findById(it).get() }

    override fun findAll(): List<LibraryStore> = repository.findAll();

    override fun delete(id: Long): LibraryStore? {
        return findById(id)?.let {
            repository.delete(it)
            it
        }
    }

    override fun save(id: Long?, libraryStoreDto: LibraryStoreDto) : LibraryStore? =
        findById(id)?.let {
            it.library = libraryService.findById(libraryStoreDto.libraryId)
            it.name = libraryStoreDto.name
            it.address = libraryStoreDto.address
            it.imgUrl = libraryStoreDto.imgUrl
            it.bookInLibrary = bookInLibraryService.findAllByIdContaining(libraryStoreDto.bookInLibrariesId);
            repository.save(it)
        } ?: repository.save(
            LibraryStore(
                id = null,
                address = libraryStoreDto.address,
                name = libraryStoreDto.name,
                imgUrl = libraryStoreDto.imgUrl,
                library = libraryService.findById(libraryStoreDto.libraryId),
                bookInLibrary = bookInLibraryService.findAllByIdContaining(libraryStoreDto.bookInLibrariesId))
        )

    override fun getPopularStores(): List<StoreDetails> {
        return repository.findPopularStores().map {
            StoreDetails(
                name = it.name,
                id = it.id ?: 0L,
                address = it.address,
                libraryName = it.library?.name ?: ""
            )
        }
    }
}

