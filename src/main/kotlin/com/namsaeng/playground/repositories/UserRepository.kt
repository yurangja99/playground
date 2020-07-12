package com.namsaeng.playground.repositories

import com.namsaeng.playground.entities.UserEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

// 기본 제공 메서드: save(), findOne(), findAll(), count(), delete()
interface UserRepository: CrudRepository<UserEntity, Int> {
    // 특정 지자체의 사용자 리스트 반환
    fun findByState(state: String): Iterable<UserEntity>

    // 아이디로 사용자 정보 찾기 (아이디 유일성 검사에 사용)
    fun findByNickname(nickname: String): UserEntity?

    // 아이디/비번으로 사용자 정보 찾기
    fun findByNicknameAndPassword(nickname: String, password: String): UserEntity?
}