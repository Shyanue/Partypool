package com.se.partypool

import com.se.partypool.service.UserService
import com.se.partypool.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.MessageDigest
import org.springframework.ui.Model
import javax.servlet.http.HttpSession

@Controller
class HtmlController {
    @Autowired
    private lateinit var userServ: UserService
    private lateinit var userRepo: UserRepository

    @RequestMapping("/")
    fun test(){
        userServ.register("Admin", "1234", "Guest")
    }

    fun crypto(ss:String):String{
        val sha=MessageDigest.getInstance("SHA-256")
        val hexa=sha.digest(ss.toByteArray())
        val crypto_str=hexa.fold("",{str, it -> str + "%02x".format(it)})
        return crypto_str
    }
    @RequestMapping("/signin")
    fun signin(model: Model,
                @RequestParam(value="id") userId:String,
                @RequestParam(value="pw") password:String,
                @RequestParam(value="type") userType:String):String{

        val db_ids=userRepo.findbyUserId(userId)
        if(db_ids!=null){
            println("해당 아이디가 이미 존재합니다.")
            return "SignUp.html"
        }

        try{
            val cryptopw=crypto(password)
            userServ.register(userId, cryptopw, userType)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return "signin"
    }
    @RequestMapping("/logout")
    fun logout(model: Model, session:HttpSession):String{
        session.invalidate()
        println("정상적으로 로그아웃 되었습니다.")
        return "StartPage.html"
    }
}