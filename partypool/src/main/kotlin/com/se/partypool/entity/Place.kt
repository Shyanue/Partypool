package com.se.partypool.entity

import javax.persistence.*

@Entity
class Place(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null, //placeID

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var hostId: Long,

    @Column(nullable = false)
    var loc: String,

    @Column(nullable = false)
    var address: String,

    @Column(nullable = false)
    var price: Int,

    @Column(nullable = false)
    var max: Int,

    @Column(nullable = false)
    var info: String
)