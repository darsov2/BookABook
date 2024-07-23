    package com.sorsix.intern.backend.domain

    import jakarta.persistence.Entity
    import jakarta.persistence.OneToMany
    import jakarta.persistence.OneToOne
    import jakarta.persistence.PrimaryKeyJoinColumn

    @Entity
    @PrimaryKeyJoinColumn
    data class Customer(
        @OneToOne(mappedBy = "customer")
        var wishList: WishList?,

        @OneToMany(mappedBy = "customer", targetEntity = Trade::class)
        var trades: List<Trade>,

        @OneToMany(mappedBy = "customer", targetEntity = CustomerBook::class)
        var customerBooks: List<CustomerBook>,

        @OneToMany(mappedBy = "customer", targetEntity = Review::class)
        var reviews: List<Review>,

        @OneToMany(mappedBy = "customer", targetEntity = Subscription::class)
        var subscriptions: List<Subscription>,

        @OneToMany(mappedBy = "customer", targetEntity = ReserveBook::class)
        var reserveBooks: MutableList<ReserveBook>?,

        @OneToMany(mappedBy = "customer", targetEntity = BorrowBook::class)
        var borrowedBooks: List<BorrowBook>

    ): User() {
    }