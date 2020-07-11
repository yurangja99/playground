package com.namsaeng.playground.repositories

import com.namsaeng.playground.entities.TailCommentEntity
import org.springframework.data.repository.CrudRepository

// 기본 제공 메서드: save(), findOne(), findAll(), count(), delete()
interface TailCommentRepository: CrudRepository<TailCommentEntity, Long> {
    // 특정 의견 댓를에 달린 답글 리스트 반환
    fun findByCommentId(commentId: Long): Iterable<TailCommentEntity>
}