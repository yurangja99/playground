package com.namsaeng.playground.repositories

import com.namsaeng.playground.entities.TopicEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

// 기본 제공 메서드: save(), findOne(), findAll(), count(), delete()
interface TopicRepository: CrudRepository<TopicEntity, Int> {
    // 토론 주제 id, 제목, 수정 시각 리스트 반환
    @Query(value = "select id, title, created from topic", nativeQuery = true)
    fun findTitlesAndCreated(): Iterable<Array<Any>>
}