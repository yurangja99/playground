package com.namsaeng.playground.repositories

import com.namsaeng.playground.entities.UserEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

// 기본 제공 메서드: save(), findOne(), findAll(), count(), delete()
interface UserRepository: CrudRepository<UserEntity, Int> {
    // 특정 지자체의 사용자 id 리스트 반환
    @Query(value="select id from user where state=:state", nativeQuery=true)
    fun findIdByState(state: String): Iterable<Int>

    // 특정 지자체의 사용자 리스트 반환
    fun findByState(state: String): Iterable<UserEntity>
}