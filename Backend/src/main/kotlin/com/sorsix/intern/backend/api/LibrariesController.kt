package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.api.dtos.StoreDetails
import com.sorsix.intern.backend.service.impl.LibraryStoreServiceImpl
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/api/libraries")
class LibrariesController(private val libraryStoreServiceImpl: LibraryStoreServiceImpl) {
    @GetMapping("/stores/popular")
    fun getPopularStores(): List<StoreDetails> {
        return libraryStoreServiceImpl.getPopularStores()
    }
}