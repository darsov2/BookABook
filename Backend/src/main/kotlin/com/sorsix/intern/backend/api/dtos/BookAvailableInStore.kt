package com.sorsix.intern.backend.api.dtos

data class BookAvailableInStore(
    var storeId: Long?,
    var storeName: String?,
    var storeAddress: String?,
    var quantity: Int?,
    var statusCode: Int?
)
