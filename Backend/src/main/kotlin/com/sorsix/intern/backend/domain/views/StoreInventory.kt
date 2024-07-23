package com.sorsix.intern.backend.domain.views

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import net.jcip.annotations.Immutable
import org.hibernate.annotations.Subselect

@Entity
@Table(name = "stores_inventory")
@Immutable
@Subselect("select * from stores_inventory")
data class StoreInventory(
    @Id
    var id: Long,
    var name: String,
    var totalBooks: Long,
    var lentBooks: Long
)
