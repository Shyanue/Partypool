package com.se.partypool.service

import com.se.partypool.repository.User
import com.se.partypool.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import javax.servlet.http.HttpSession

@Service
class UserService(
    @Autowired
    private val userRepo: UserRepository
) {
    // 회원가입 기능
    fun register(id: String, pw: String, type: String?) {
        if(id.isNotBlank()) {
            if(pw.isNotBlank()) {
                if(!type.isNullOrBlank()) {
                    userRepo.save(User(userId = id, userPw = pw, userType = type))
                }
                else{ println("type 없음")  } //type 선택하지 않은 경우
            }
            else{ println("pw 없음")  } //pw를 입력하지 않은 경우
        }
        else{ println("id 없음") } //id를 입력하지 않은 경우
    }

    //로그인 기능
    fun login(id: String, pw: String, type: String,
              model: Model, session: HttpSession
    ):String {
        val dbUser = userRepo.findByUserId(id)
        var page=""
        if(dbUser != null){
            if(pw.equals(dbUser.userPw)){
                if(type.equals(dbUser.userType)){
                    session.setAttribute("userId", dbUser.userId)
                    session.setAttribute("userType", dbUser.userType)
                    model.addAttribute("title","logout")
                    println("로그인 성공 "+session.getAttribute("userId"))
                    page= "testLogout.html"
                }
                else{
                    println("타입이 다릅니다") //type 맞지 않은 경우
                    page= "testLogin.html"
                }
            }
            else {
                println("비밀번호가 다릅니다") //pw가 맞지 않은 경우
                page= "testLogin.html"
            }
       }
        else{
            println("해당하는 아이디가 없습니다") //id가 맞지 않은 경우
            page= "testLogin.html"
        }

        return page
    }


}
