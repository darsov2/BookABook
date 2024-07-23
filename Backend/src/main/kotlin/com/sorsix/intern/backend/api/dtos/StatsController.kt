package com.sorsix.intern.backend.api.dtos

import com.sorsix.intern.backend.config.CurrentUser
import com.sorsix.intern.backend.domain.views.LoansInLastDays
import com.sorsix.intern.backend.domain.views.StoreInventory
import com.sorsix.intern.backend.security.UserPrincipal
import com.sorsix.intern.backend.service.StatsService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/stats")
@CrossOrigin
class StatsController(
    private val statsService: StatsService
) {
    @GetMapping("/inventory")
    fun getInventory(@CurrentUser userPrincipal: UserPrincipal): Double {
        return statsService.getInventoryForStore(userPrincipal.id)
    }

    @GetMapping("/loansPerDays")
    fun getLoansPerDays(@CurrentUser userPrincipal: UserPrincipal): LoansInLastDays {
        return statsService.getLoansInLastDays(userPrincipal.id)
    }
}
