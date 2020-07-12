package com.namsaeng.playground.controllers

import com.namsaeng.playground.entities.UserEntity
import com.namsaeng.playground.repositories.UserRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import kotlin.collections.HashMap

@Api(tags=["1. 사용자 DB"])
@CrossOrigin(origins=["http://localhost:3000"])
@RestController
class UserController {
    @Autowired
    lateinit var userRepository: UserRepository

    // CREATE

    // 새로운 유저 생성
    @ApiOperation(value="새 사용자 추가", notes="새 사용자를 데이터베이스에 추가합니다.")
    @PostMapping("/user")
    fun createUser(
            @ApiParam("state?, age?, gender?, name, nickname, password, remark?, profileUrl?, category, surveyNum?, surveyState?") @RequestBody user: HashMap<String, Any>
    ): HashMap<String, Any?> {
        return try {
            // 새로운 UserEntity 인스턴스 선언 후, DB에 추가
            val userAsUserEntity: UserEntity = UserEntity(
                    user["state"] as String?,
                    user["age"] as Int?,
                    user["gender"] as String?,
                    user["name"] as String,
                    user["nickname"] as String,
                    user["password"] as String,
                    user["remark"] as Int?,
                    user["profileUrl"] as String?,
                    user["category"] as String,
                    user["surveyNum"] as Int?,
                    user["surveyState"] as Int?
            )
            val resultOfSave: UserEntity = userRepository.save(userAsUserEntity)
            hashMapOf(Pair("data", resultOfSave))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // READ

    // user DB 전체를 반환
    @ApiOperation(value="사용자 데이터베이스 반환", notes="사용자 전체의 데이터베이스를 반환합니다.")
    @GetMapping("/db/user")
    fun readUserDB(): HashMap<String, Any?> {
        return try {
            // 데이터베이스의 모든 레코드 찾아서 반환.
            val allUserRecords: Iterable<UserEntity> = userRepository.findAll()
            hashMapOf(Pair("data", allUserRecords))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 특정 지자체 유저 리스트 열람
    @ApiOperation(value="특정 지자체 사용자 리스트 반환", notes="특정 지자체 사용자 리스트를 반환합니다.")
    @GetMapping("/users")
    fun readUsers(state: String): HashMap<String, Any?> {
        return try {
            // 특정 지자체를 가진 레코드를 찾고, 그 리스트를 반환한다.
            val usersOfState: Iterable<UserEntity> = userRepository.findByState(state)
            hashMapOf(Pair("data", usersOfState))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 특정 유저 정보 열람
    @ApiOperation(value="특정 사용자 정보 반환", notes="특정 사용자의 데이터를 반환합니다.")
    @GetMapping("/user")
    fun readUser(id: Int): HashMap<String, Any?> {
        return try {
            // 특정 id를 가진 레코드 혹은 null 반환
            val user: UserEntity? = userRepository.findById(id).orElse(null)
            hashMapOf(Pair("data", user))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 아이디가 유일한지 확인
    @ApiOperation(value="아이디 유일성 반환", notes="데이터베이스 안에서 아이디가 유일한지 판단합니다.")
    @GetMapping("/login/unique")
    fun isUnique(nickname: String): HashMap<String, Any?> {
        return try {
            // 특정 nickname 레코드를 가져온다. 없으면 유일, 있으면 중복.
            val user: UserEntity? = userRepository.findByNickname(nickname)
            hashMapOf(Pair("data", user == null))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 아이디/비밀번호를 통한 로그인
    @ApiOperation(value="아이디/비번으로 로그인", notes="아이디/비밀번호를 받아 일치하는 레코드 혹은 null 반환")
    @GetMapping("/login")
    fun loginUser(nickname: String, password: String): HashMap<String, Any?> {
        return try {
            // 특정 nickname, password를 가진 레코드를 가져온다. 없으면 null
            val user: UserEntity? = userRepository.findByNicknameAndPassword(nickname, password)
            hashMapOf(Pair("data", user))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // UPDATE

    // 유저 정보 변경
    // state?, school?, grade?, class?, number?, name?, category?
    @ApiOperation(value="특정 사용자 정보 수정", notes="특정 사용자 데이터를 수정하고, 수정된 데이터를 반환합니다.")
    @PatchMapping("/user")
    fun updateUser(
            id: Int,
            @ApiParam("state?, age?, gender?, name?, nickname?, password?, remark?, profileUrl?, cateogry?, surveyNum?, surveyState?") @RequestBody newUser: HashMap<String, Any>
    ): HashMap<String, Any?> {
        return try {
            // 주어진 id를 가진 레코드를 찾고, 각 필드가 존재하면, 변경한다.
            val userWillBeUpdated: UserEntity? = userRepository.findById(id).orElse(null)
            if (userWillBeUpdated != null) {
                if (newUser["state"] != null) userWillBeUpdated.state = newUser["state"] as String
                if(newUser["age"] != null) userWillBeUpdated.age = newUser["age"] as Int
                if(newUser["gender"] != null) userWillBeUpdated.gender = newUser["gender"] as String
                if (newUser["name"] != null) userWillBeUpdated.name = newUser["name"] as String
                if (newUser["nickname"] != null) userWillBeUpdated.nickname = newUser["nickname"] as String
                if (newUser["password"] != null) userWillBeUpdated.password = newUser["password"] as String
                if (newUser["remark"] != null) userWillBeUpdated.remark = newUser["remark"] as Int
                if (newUser["profileUrl"] != null) userWillBeUpdated.profileUrl = newUser["profileUrl"] as String
                if (newUser["category"] != null) userWillBeUpdated.category = newUser["category"] as String
                if (newUser["surveyNum"] != null) userWillBeUpdated.surveyNum = newUser["surveyNum"] as Int
                if (newUser["surveyState"] != null) userWillBeUpdated.surveyState = newUser["surveyState"] as Int
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
    @ApiOperation(value="특정 사용자 정보 삭제", notes="특정 사용자 데이터를 삭제하고, 그 id를 반환합니다.")
    @DeleteMapping("/user")
    fun deleteUser(id: Int): HashMap<String, Any?> {
        return try {
            // 주어진 id를 가진 레코드를 삭제한다.
            val userWillBeDeleted: UserEntity? = userRepository.findById(id).orElse(null)
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