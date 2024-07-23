package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.domain.views.LoansInLastDays
import com.sorsix.intern.backend.domain.views.StoreInventory
import com.sorsix.intern.backend.repository.LibrarianRepository
import com.sorsix.intern.backend.repository.LoansInLastDaysRepository
import com.sorsix.intern.backend.repository.StoreInventoryRepository
import com.sorsix.intern.backend.service.StatsService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class StatsServiceImpl(
    private val storeInventoryRepository: StoreInventoryRepository,
    private val librarianRepository: LibrarianRepository,
    private val loansInLastDays: LoansInLastDaysRepository,
) : StatsService {
    override fun getInventoryForStore(userId: Long): Double {
        val storeId = librarianRepository.findLibraryIdByUserId(userId)
        val stats = storeInventoryRepository.findByIdOrNull(storeId) ?: throw NotFoundException()
        return stats.lentBooks.toDouble() / stats.totalBooks.toDouble() * 100.0
    }

    override fun getLoansInLastDays(userId: Long): LoansInLastDays {
        val storeId = librarianRepository.findLibraryIdByUserId(userId)
        return loansInLastDays.findByIdOrNull(storeId) ?: throw NotFoundException()
    }
}