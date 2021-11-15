package com.se.partypool

import com.se.partypool.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class MainController {
    @Autowired
    private lateinit var userServ: UserService

    @RequestMapping("/")
    fun test(){
        userServ.register("Admin", "1234", "Guest")
    }
}