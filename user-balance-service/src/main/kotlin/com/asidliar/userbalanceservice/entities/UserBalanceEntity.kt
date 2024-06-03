package com.asidliar.userbalanceservice.entities

import jakarta.persistence.*

@Entity
@Table(name = "USER_BALANCES")
data class UserBalanceEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    var name: String? = null,

    @Column(nullable = false)
    var balance: Int = 0
)
