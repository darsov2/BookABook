package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.views.LoansInLastDays
import com.sorsix.intern.backend.domain.views.StoreInventory

interface StatsService {
    fun getInventoryForStore(userId: Long): Double
    fun getLoansInLastDays(userId: Long): LoansInLastDays
}