package com.sorsix.intern.backend.repository

import com.sorsix.intern.backend.domain.Trade
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TradeRepository : JpaRepository<Trade, Long> {
}