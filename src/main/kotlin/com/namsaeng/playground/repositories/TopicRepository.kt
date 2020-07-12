package com.namsaeng.playground.repositories

import com.namsaeng.playground.entities.TopicEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

// 기본 제공 메서드: save(), findOne(), findAll(), count(), delete()
interface TopicRepository: CrudRepository<TopicEntity, Int> {
    // 주어진 지자체 토론 주제 리스트 반환
    fun findByState(state: String): Iterable<TopicEntity>
}