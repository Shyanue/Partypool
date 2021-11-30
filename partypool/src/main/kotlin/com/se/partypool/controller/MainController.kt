package com.se.partypool.controller

import com.se.partypool.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller
class MainController {
    @Autowired
    private lateinit var userServ: UserService

    @RequestMapping("/")    //home
    fun homeForm(model: Model):String {
        model.addAttribute("title", "home")
        return "StartPage"
    }

    @GetMapping("/{form}")
    fun pageForm(@PathVariable form:String,
                 httpServletRequest: HttpServletRequest):String{
        var resp:String = ""
        if(form.equals("signup")){
            resp = "SignUp"
        }
        else if(form.equals("login")){
            resp = "Login"
        }
        else if(form.equals("mainPageCustomer")){
            var session:HttpSession = httpServletRequest.getSession()
            resp = "MainPage_Customer"
        }
        else if(form.equals("checkReservCustomer")){
            var session:HttpSession = httpServletRequest.getSession()
            resp = "CheckReservation_Customer"
        }
        else if(form.equals("partyRoom")){
            var session:HttpSession = httpServletRequest.getSession()
            resp = "PartyRoomList"
        }
        else if(form.equals("payment")){
            var session:HttpSession = httpServletRequest.getSession()
            resp = "Payment"
        }
        else if(form.equals("roomReserv")){
            var session:HttpSession = httpServletRequest.getSession()
            resp = "RoomReservation"
        }
        else if(form.equals("host")){
            var session:HttpSession = httpServletRequest.getSession()
            resp = "host"
        }
        else if(form.equals("hostRegister")){
            var session:HttpSession = httpServletRequest.getSession()
            resp = "hostRegister"
        }
        else if(form.equals("infoRevise")){
            var session:HttpSession = httpServletRequest.getSession()
            resp = "infoRevise"
        }
        return resp
    }

/****************임시 회원가입 (아이디 중복, 비밀번호 재확인 없음)*********************/

    //회원가입
    @PostMapping("/signup",)
    fun postSignUp(model: Model, httpServletResponse: HttpServletResponse,
                   @RequestParam("user_ID") id:String,
                   @RequestParam("user_PW1") password:String,
                   @RequestParam("user_PW2") passwordcheck:String,
                   @RequestParam("UserType") type:String?
    ):String{
        var page=""
        userServ.idcheck(id, httpServletResponse)
        userServ.pwcheck(password, passwordcheck, httpServletResponse)
        if(userServ.resultcheck()){
            try {
                page=userServ.register(id, password, type, httpServletResponse)
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
        else{
            page="SignUp"
        }
        model.addAttribute("title","home")
        return page
    }

    //로그인
    @PostMapping("/login")
    fun postLogin(
        model: Model,
        session: HttpSession,
        httpServletResponse: HttpServletResponse,
        @RequestParam(value="user_ID") id:String,
        @RequestParam(value="user_PW1") password:String,
        @RequestParam(value="UserType") type:String?
    ):String{
        var page =""
        try{
            page = userServ.login(id, password, type, model, session, httpServletResponse)
        }catch (e:Exception){
            e.printStackTrace()
        }

        return page
    }

    //로그아웃
    @RequestMapping("/logout")
    fun postLogout(model: Model, httpServletRequest: HttpServletRequest):String{
        var s:HttpSession = httpServletRequest.getSession(false)
        //println("로그아웃 : "+s.getAttribute("userId"))
        httpServletRequest.getSession(false).invalidate()

        model.addAttribute("title","home")
        return "StartPage"
    }
}
