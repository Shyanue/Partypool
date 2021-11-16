package com.se.partypool.repository

import org.springframework.data.repository.CrudRepository

interface UserRepository:CrudRepository<User, Long> {
    fun findbyUserId(id:String):User?
}