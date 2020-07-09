package com.namsaeng.playground.repositories

import com.namsaeng.playground.entities.UserEntity
import org.springframework.data.repository.CrudRepository

// 기본 제공 메서드: save(), findOne(), findAll(), count(), delete()
interface UserRepository: CrudRepository<UserEntity, Int> {

}