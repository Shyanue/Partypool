package com.se.partypool.entity

import javax.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var userId: String,

    @Column(nullable = false)
    var userPw: String,

    @Column(nullable = false)
    var userType: String //Host or Guest
)