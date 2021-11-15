package com.se.partypool.repository

import jdk.jfr.DataAmount
import javax.persistence.*

@DataAmount
@Entity
class ImageFile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var oriName: String,

    @Column(nullable = false)
    var uri: String
)