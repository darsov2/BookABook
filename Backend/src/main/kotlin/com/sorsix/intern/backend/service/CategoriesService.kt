package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.Category

interface CategoriesService {
    fun findAllByIdContaining(categoriesId: List<Long>): MutableList<Category>
    fun findAll(): List<Category>
    fun getPopularCategories(): List<Category>
}