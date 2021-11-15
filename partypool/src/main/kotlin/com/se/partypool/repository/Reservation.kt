package com.se.partypool.repository

import javax.persistence.*

@Entity
class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var placeId: Long,

    @Column(nullable = false)
    var hostId: Long,

    @Column(nullable = false)
    var guestId: Long,

    @Column(nullable = false)
    var date: String, //yyyy-mm-dd

    @Column(nullable = false)
    var num: Int
)