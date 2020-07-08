package com.namsaeng.playground

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

// 기본 제공 메서드: save(), findOne(), findAll(), count(), delete()
interface UserRepository: CrudRepository<UserEntity, Int> {

}

interface TopicRepository: CrudRepository<TopicEntity, Int> {
    @Query(value = "select id, title, created from topic", nativeQuery = true)
    fun findTitlesAndCreated(): Iterable<Array<Any>>

}

interface CommentRepository: CrudRepository<CommentEntity, Int> {

}

interface TailCommentRepository: CrudRepository<TailCommentEntity, Int> {

}