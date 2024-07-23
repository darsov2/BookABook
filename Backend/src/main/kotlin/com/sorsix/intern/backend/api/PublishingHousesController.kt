package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.config.CurrentUser
import com.sorsix.intern.backend.service.PublishingHouseService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/api/publishers")
class PublishingHousesController(
    val publishingHouseService: PublishingHouseService
) {
    @GetMapping("")
    fun getPublishers() = publishingHouseService.findAll()
}