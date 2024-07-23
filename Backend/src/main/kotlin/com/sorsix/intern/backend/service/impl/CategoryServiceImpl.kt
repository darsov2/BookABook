package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.Category
import com.sorsix.intern.backend.repository.CategoryRepository
import com.sorsix.intern.backend.service.CategoriesService
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(val repository: CategoryRepository) : CategoriesService {
    override fun findAll(): List<Category> = repository.findAll()
    override fun findAllByIdContaining(categoriesId: List<Long>): MutableList<Category> = repository.findAllByIdIn(categoriesId);
    override fun getPopularCategories() = repository.findPopularCategories()
}