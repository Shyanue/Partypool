package com.se.partypool.service

import com.se.partypool.entity.User
import com.se.partypool.repository.UserRepository
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.ui.Model
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@ExtendWith(MockKExtension::class)
class UserServiceTest{
    private val userRepo = mockk<UserRepository>()
    private val userServ = UserService(userRepo)

    private val model = mockk<Model>(relaxed = true)
    private val session = mockk<HttpSession>(relaxed = true)
    private val response = mockk<HttpServletResponse>(relaxed = true)

    @Test
    fun registerSuccessfully() {
        //given
        val id = "tester"
        val pw = "test1234"
        val type = "Host"

        val user = User(userId = id, userPw = pw, userType = type)
        every {
            userRepo.save(any())
        }returns user

        //when
        userServ.register(id, pw, type)

        //then
        verify{ userRepo.save(any()) }
    }

    @Test
    fun registerFailWithNoId(){
        //given
        val id = ""
        val pw = "test1234"
        val type = "Host"

        every {
            userRepo.save(any())
        }throws(Exception("Can't Saved"))

        //when
        var isThrownException = false
        try{
            userServ.register(id, pw, type)
        }catch(e: Exception){
            isThrownException = true
        }

        //then
        assertFalse(isThrownException)
        verify(exactly = 0){ userRepo.save(any()) }
    }

    @Test
    fun registerFailWithNoPw(){
        //given
        val id = "tester"
        val pw = ""
        val type = "Host"

        every {
            userRepo.save(any())
        }throws(Exception("Can't Saved"))

        //when
        var isThrownException = false
        try{
            userServ.register(id, pw, type)
        }catch(e: Exception){
            isThrownException = true
        }

        //then
        assertFalse(isThrownException)
        verify(exactly = 0){ userRepo.save(any()) }
    }

    @Test
    fun registerFailWithNullType(){
        //given
        val id = "tester"
        val pw = "test1234"
        val type = null

        every {
            userRepo.save(any())
        }throws(Exception("Can't Saved"))

        //when
        var isThrownException = false
        try{
            userServ.register(id, pw, type)
        }catch(e: Exception){
            isThrownException = true
        }

        //then
        assertFalse(isThrownException)
        verify(exactly = 0){ userRepo.save(any()) }
    }

    @Test
    fun registerFailWithNoType(){
        //given
        val id = "tester"
        val pw = "test1234"
        val type = ""

        every {
            userRepo.save(any())
        }throws(Exception("Can't Saved"))

        //when
        var isThrownException = false
        try{
            userServ.register(id, pw, type)
        }catch(e: Exception){
            isThrownException = true
        }

        //then
        assertFalse(isThrownException)
        verify(exactly = 0){ userRepo.save(any()) }
    }

    @Test
    fun loginHost() {
        //given
        val id = "tester"
        val pw = "test1234"
        val type = "Host"

        val user = User(userId = id, userPw = pw, userType = type)
        every {
            userRepo.findByUserId(any())
        }returns user

        val expectedPage = "host"

        //when
        val actualPage = userServ.login(id, pw, type, model, session, response)

        //then
        assertEquals(expectedPage, actualPage)
    }

    @Test
    fun loginGuest() {
        //given
        val id = "tester"
        val pw = "test1234"
        val type = "Guest"

        val user = User(userId = id, userPw = pw, userType = type)
        every {
            userRepo.findByUserId(any())
        }returns user

        val expectedPage = "MainPage_Customer"

        //when
        val actualPage = userServ.login(id, pw, type, model, session, response)

        //then
        assertEquals(expectedPage, actualPage)
    }

    @Test
    fun loginWithNotCorrectID(){
        //given
        val id = "tester"
        val pw = "test1234"
        val type = "Host"

        every {
            userRepo.findByUserId(any())
        }returns null

        val expectedPage = "Login"

        //when
        val actualPage = userServ.login(id, pw, type, model, session, response)

        //then
        assertEquals(expectedPage, actualPage)
    }

    @Test
    fun loginWithNotCorrectType(){
        //given
        val id = "tester"
        val pw = "test1234"
        var type = "Host"

        val user = User(userId = id, userPw = pw, userType = type)
        every {
            userRepo.findByUserId(any())
        }returns user
        type = "Guest"

        val expectedPage = "Login"

        //when
        val actualPage = userServ.login(id, pw, type, model, session, response)

        //then
        assertEquals(expectedPage, actualPage)
    }

    @Test
    fun loginWithNotCorrectPW(){
        //given
        val id = "tester"
        var pw = "test1234"
        val type = "Host"

        val user = User(userId = id, userPw = pw, userType = type)
        every {
            userRepo.findByUserId(any())
        }returns user
        pw = "test4321"

        val expectedPage = "Login"

        //when
        val actualPage = userServ.login(id, pw, type, model, session, response)

        //then
        assertEquals(expectedPage, actualPage)
    }
}