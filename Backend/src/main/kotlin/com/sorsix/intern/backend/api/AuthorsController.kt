package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.service.AuthorService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/authors")
@CrossOrigin
class AuthorsController(
    val authorService: AuthorService
) {
    @GetMapping("")
    fun getAuthors() = authorService.findAll();

    @GetMapping("/popular")
    fun getPopularAuthors() = authorService.findAll()
}