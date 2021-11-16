package com.se.partypool

import com.se.partypool.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession


@Controller
class HtmlController {
    @Autowired
    private lateinit var userServ: UserService

/**************기능 동작 확인 위한 임시 html*********************/
    @RequestMapping("/")    //home
    fun index(model: Model):String {
        model.addAttribute("title", "home")
        return "testhome.html"
    }
    @GetMapping("/signup")
    fun signForm(model: Model):String{
        model.addAttribute("title","signup")
        return "testSignUp.html"
    }
    @GetMapping("/login")
    fun loginForm(model: Model):String {
        model.addAttribute("title", "login")
        return "testLogin.html"
    }
    @GetMapping("/logout")
    fun logout(model: Model):String{
        model.addAttribute("title","logout")
        return "testLogout.html"
    }
/**************기능 동작 확인 위한 임시 html*********************/

    //회원가입
    @PostMapping("/signup",)
    fun postSignUp(model: Model,
        @RequestParam("user_ID") id:String,
        @RequestParam("user_PW1") password:String,
        @RequestParam("UserType") type:String?
    ):String{
        try {
            userServ.register(id, password, type)
        }catch(e:Exception){
            e.printStackTrace()
        }

        model.addAttribute("title","home")
        return "testhome.html"
    }

    //로그인
    @PostMapping("/login")
    fun postLogin(
        model: Model,
        session: HttpSession,
        @RequestParam(value="user_ID") id:String,
        @RequestParam(value="user_PW1") password:String,
        @RequestParam(value="UserType") type:String
    ):String{
        var page =""
        try{
            page = userServ.login(id, password, type, model, session)
        }catch (e:Exception){
            e.printStackTrace()
        }

        return page
    }

    //로그아웃
    @PostMapping("/logout")
    fun postLogout(model: Model, httpServletRequest: HttpServletRequest):String{
        var s:HttpSession = httpServletRequest.getSession()
        httpServletRequest.getSession(false).invalidate()

        model.addAttribute("title","home")
        return "testhome.html"
    }
}