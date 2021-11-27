package com.se.partypool.entity

import javax.persistence.*

@Entity
class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var placeId: Long,

    @Column(nullable = false)
    var path: String
)