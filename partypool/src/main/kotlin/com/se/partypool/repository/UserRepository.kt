package com.se.partypool.repository

import com.se.partypool.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository:CrudRepository<User, Long> {
    fun findByUserId(userId:String): User?
}