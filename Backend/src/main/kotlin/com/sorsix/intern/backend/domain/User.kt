package com.sorsix.intern.backend.domain

import jakarta.persistence.*

@Entity
@Table(name = "app_user")
@Inheritance(strategy = InheritanceType.JOINED)
open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var number: String? = null,
    var name: String = "",
    var address: String? = null,
    var email: String = "",
    var password: String = "",
    var imgUser: String? = null
)