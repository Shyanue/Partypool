package com.se.partypool.service

import com.se.partypool.entity.User
import com.se.partypool.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import java.io.PrintWriter
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Service
class UserService(
    @Autowired
    private val userRepo: UserRepository,
    var idcheck_result:Boolean=true,
    var pwcheck_result:Boolean=true
) {

    // 회원가입 기능
    fun register(id: String, pw: String, type: String?, httpServletResponse: HttpServletResponse):String {
        var page=""
        httpServletResponse.characterEncoding="UTF-8"
        var out:PrintWriter=httpServletResponse.writer
        if(id.isNotBlank()) {
            if(pw.isNotBlank()) {
                if(!type.isNullOrBlank()) {
                    userRepo.save(User(userId = id, userPw = pw, userType = type))
                    page="StartPage"
                }
                else{
                    out.println("<script>alert('타입을 선택해주세요.'); </script>")
                    out.flush()
                    page="SignUp"
                } //type 선택하지 않은 경우
            }
            else{
                out.println("<script>alert('비밀번호를 입력해주세요.'); </script>")
                out.flush()
                page="SignUp"
            } //pw를 입력하지 않은 경우
        }
        else{
            out.println("<script>alert('아이디를 입력해주세요.'); </script>")
            out.flush()
            page="SignUp"
        } //id를 입력하지 않은 경우

        return page
    }
    fun idcheck(id:String, httpServletResponse: HttpServletResponse){
        val dbUser=userRepo.findByUserId(id)
        if(dbUser!=null){
            httpServletResponse.characterEncoding = "UTF-8"
            var out:PrintWriter = httpServletResponse.writer
            out.println("<script>alert('해당 아이디는 이미 존재합니다.'); </script>")
            out.flush()
            idcheck_result=false
        }
    }
    fun pwcheck(pw1:String, pw2:String, httpServletResponse: HttpServletResponse){
        if(!pw1.equals(pw2)){
            httpServletResponse.characterEncoding = "UTF-8"
            var out:PrintWriter = httpServletResponse.writer
            out.println("<script>alert('비밀번호가 일치하지 않습니다.'); </script>")
            out.flush()
            pwcheck_result=false
        }
    }
    fun resultcheck():Boolean{
        var totalresult=false
        if(idcheck_result&&pwcheck_result){
            totalresult=true
        }
        else{
            totalresult=false
        }
        return totalresult
    }

    //로그인 기능
    fun login(id: String, pw: String, type: String?,
              model: Model, session: HttpSession, httpServletResponse: HttpServletResponse
    ):String {
        val dbUser = userRepo.findByUserId(id)
        var page=""
        if(dbUser != null){
            if(pw.equals(dbUser.userPw)){
                if(type.equals(dbUser.userType)) {
                    if (type.equals("Host")) { //type = 호스트
                        session.setAttribute("userId", dbUser.userId)
                        session.setAttribute("userType", dbUser.userType)
                        model.addAttribute("title", "host")
                        println("호스트 로그인 성공 " + session.getAttribute("userId"))
                        page = "host"
                    } else { //type = 회원
                        session.setAttribute("userId", dbUser.userId)
                        session.setAttribute("userType", dbUser.userType)
                        model.addAttribute("title", "logout")
                        println("회원 로그인 성공 " + session.getAttribute("userId"))
                        page = "MainPage_Customer"
                    }
                }
                else{   //type 맞지 않은 경우
                    httpServletResponse.characterEncoding = "UTF-8"
                    var out:PrintWriter = httpServletResponse.writer
                    out.println("<script>alert('호스트/회원 타입이 다릅니다.'); </script>")
                    out.flush()
                    page= "Login"
                }
            }
            else { //pw 맞지 않은 경우
                httpServletResponse.characterEncoding = "UTF-8"
                var out:PrintWriter = httpServletResponse.writer
                out.println("<script>alert('비밀번호가 다릅니다.'); </script>")
                out.flush()
                page= "Login"
            }
       }
        else{ //id가 맞지 않은 경우
            httpServletResponse.characterEncoding = "UTF-8"
            var out:PrintWriter = httpServletResponse.writer
            out.println("<script>alert('해당하는 아이디가 없습니다.'); </script>")
            out.flush()
            page= "Login"
        }
        return page
    }

    fun showHostMain(){

    }
}
