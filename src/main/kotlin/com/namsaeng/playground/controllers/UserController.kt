package com.namsaeng.playground.controllers

import com.namsaeng.playground.entities.UserEntity
import com.namsaeng.playground.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import kotlin.collections.HashMap

@RestController
class UserController {
    @Autowired
    lateinit var userRepository: UserRepository

    // CREATE

    // 새로운 유저 생성
    @PostMapping("/user")
    fun createUser(@RequestBody user: HashMap<String, Any>): HashMap<String, Any?> {
        return try {
            val userAsUserEntity = UserEntity(
                    user["state"] as String?,
                    user["school"] as String?,
                    user["grade"] as Int?,
                    user["class"] as Int?,
                    user["number"] as Int?,
                    user["name"] as String,
                    user["category"] as String
            )
            val resultOfSave = userRepository.save(userAsUserEntity)
            hashMapOf(Pair("data", resultOfSave))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // READ

    // user DB 전체를 반환
    @GetMapping("/db/user")
    fun readUserDB(): HashMap<String, Any?> {
        return try {
            val allUserRecords: Iterable<UserEntity> = userRepository.findAll()
            hashMapOf(Pair("data", allUserRecords))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 특정 유저 정보 열람
    @GetMapping("/user")
    fun readUser(id: Int): HashMap<String, Any?> {
        return try {
            val user = userRepository.findById(id).orElse(null)
            hashMapOf(Pair("data", user))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // UPDATE

    // 유저 정보 변경
    // state?, school?, grade?, class?, number?, name?, category?
    @PatchMapping("/user")
    fun updateUser(id: Int, @RequestBody newUser: HashMap<String, Any>): HashMap<String, Any?> {
        return try {
            val userWillBeUpdated = userRepository.findById(id).orElse(null)
            if (userWillBeUpdated != null) {
                if (newUser["state"] != null) userWillBeUpdated.state = newUser["state"] as String
                if (newUser["school"] != null) userWillBeUpdated.school = newUser["school"] as String
                if (newUser["grade"] != null) userWillBeUpdated.grade = newUser["grade"] as Int
                if (newUser["class"] != null) userWillBeUpdated.classNum = newUser["class"] as Int
                if (newUser["number"] != null) userWillBeUpdated.number = newUser["number"] as Int
                if (newUser["name"] != null) userWillBeUpdated.name = newUser["name"] as String
                if (newUser["category"] != null) userWillBeUpdated.category = newUser["category"] as String
                userRepository.save(userWillBeUpdated)
                hashMapOf(Pair("data", userWillBeUpdated))
            } else {
                hashMapOf(Pair("data", null))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    //DELETE

    // 기존의 유저 제거
    @DeleteMapping("/user")
    fun deleteUser(id: Int): HashMap<String, Any?> {
        return try {
            val userWillBeDeleted = userRepository.findById(id).orElse(null)
            if (userWillBeDeleted != null) {
                userRepository.delete(userWillBeDeleted)
                hashMapOf(Pair("data", id))
            } else {
                hashMapOf(Pair("data", null))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }
}