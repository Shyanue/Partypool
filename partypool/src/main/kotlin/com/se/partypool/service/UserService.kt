package com.se.partypool.service

import com.se.partypool.repository.User
import com.se.partypool.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    private val userRepo: UserRepository
) {
    fun register(id: String, pw: String, type: String) =
        userRepo.save(User(userId = id, userPw = pw, userType = type))
}