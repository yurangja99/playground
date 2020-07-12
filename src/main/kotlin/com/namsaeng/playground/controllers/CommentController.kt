package com.namsaeng.playground.controllers

import com.namsaeng.playground.entities.CommentEntity
import com.namsaeng.playground.repositories.CommentRepository
import com.namsaeng.playground.repositories.TailCommentRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap

@Api(tags=["3. 의견 댓글 DB"])
@CrossOrigin(origins=["http://localhost:3000"])
@RestController
class CommentController {
    @Autowired
    lateinit var commentRepository: CommentRepository
    @Autowired
    lateinit var tailCommentRepository: TailCommentRepository

    // CREATE

    // 새로운 의견 댓글 생성
    @ApiOperation(value="새 의견 댓글 추가", notes="새 의견 댓글을 데이터베이스에 추가합니다.")
    @PostMapping("/comment")
    fun createComment(
            @ApiParam("userId, topicId, content") @RequestBody comment: HashMap<String, Any>
    ): HashMap<String, Any?> {
        return try {
            // 새로운 CommentEntity 인스턴스 선언 후, DB에 추가
            val commentAsCommentEntity: CommentEntity = CommentEntity(
                    comment["userId"] as Int,
                    comment["topicId"] as Int, 0,
                    comment["content"] as String
            )
            val resultOfSave: CommentEntity = commentRepository.save(commentAsCommentEntity)
            hashMapOf(Pair("data", resultOfSave))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // READ

    // comment DB 전체를 반환
    @ApiOperation(value="의견 댓글 데이터베이스 반환", notes="의견 댓글 전체의 데이터베이스를 반환합니다.")
    @GetMapping("/db/comment")
    fun readCommentDB(): HashMap<String, Any?> {
        return try {
            // 데이터베이스의 모든 레코드 찾아서 반환.
            val allCommentRecords: Iterable<CommentEntity> = commentRepository.findAll()
            hashMapOf(Pair("data", allCommentRecords))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 특정 주제의 댓글 리스트 반환
    @ApiOperation(value="특정 토론 주제 댓글 리스트 반환", notes="특정 토론 주제에 달린 의견 댓글의 리스트를 반환합니다.")
    @GetMapping("/comments")
    fun readComments(topicId: Int): HashMap<String, Any?> {
        return try{
            // 먼저 특정 주제에 달린 의견 댓글들을 찾고, 각 댓글에 대한 답글을 찾아 의견 댓글에 달아 둠.
            val commentsOfTopic: Iterable<CommentEntity> = commentRepository.findByTopicId(topicId)
            val commentsAndTailComments: MutableList<HashMap<String, Any?>> = mutableListOf()
            for (comment in commentsOfTopic) {
                commentsAndTailComments.add(
                        hashMapOf(
                                Pair("userId", comment.userId),
                                Pair("topicId", comment.topicId),
                                Pair("like", comment.like),
                                Pair("content", comment.content),
                                Pair("modified", comment.modified),
                                Pair("id", comment.id),
                                Pair("tails", tailCommentRepository.findByCommentId(comment.id))))
            }
            return hashMapOf(Pair("data", commentsAndTailComments))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 특정 의견 댓글 정보 열람
    @ApiOperation(value="특정 의견 댓글 정보 반환", notes="특정 의견 댓글 데이터를 반환합니다.")
    @GetMapping("/comment")
    fun readComment(id: Long): HashMap<String, Any?> {
        return try {
            // 특정 id를 가진 레코드 혹은 null 반환
            val comment: CommentEntity? = commentRepository.findById(id).orElse(null)
            hashMapOf(Pair("data", comment))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }


    // UPDATE

    // 의견 댓글 정보 변경
    // userId?, topicId?, like?, content?
    @ApiOperation(value="특정 의견 댓글 정보 수정", notes="특정 의견 댓글 데이터를 수정하고, 수정된 데이터를 반환합니다.")
    @PatchMapping("/comment")
    fun updateComment(
            id: Long,
            @ApiParam("userId?, topicId?, like?, content?") @RequestBody newComment: HashMap<String, Any>
    ): HashMap<String, Any?> {
        return try {
            // 주어진 id를 가진 레코드를 찾고, 각 필드가 존재하면, 변경한다.
            val commentWillBeUpdated: CommentEntity? = commentRepository.findById(id).orElse(null)
            if (commentWillBeUpdated != null) {
                if (newComment["userId"] != null) commentWillBeUpdated.userId = newComment["userId"] as Int
                if (newComment["topicId"] != null) commentWillBeUpdated.topicId = newComment["topicId"] as Int
                if (newComment["like"] != null) commentWillBeUpdated.like = newComment["like"] as Int
                if (newComment["content"] != null) commentWillBeUpdated.content = newComment["content"] as String
                commentWillBeUpdated.modified = Date()
                commentRepository.save(commentWillBeUpdated)
                hashMapOf(Pair("data", commentWillBeUpdated))
            } else {
                hashMapOf(Pair("data", null))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // DELETE

    // 기존의 의견 댓글 제거.
    @ApiOperation(value="특정 의견 댓글 정보 삭제", notes="특정 의견 댓글 데이터를 삭제하고, 그 id를 반환합니다.")
    @DeleteMapping("/comment")
    fun deleteComment(id: Long): HashMap<String, Any?> {
        return try {
            // 주어진 id를 가진 레코드를 삭제한다.
            val commentWillBeDeleted: CommentEntity? = commentRepository.findById(id).orElse(null)
            if (commentWillBeDeleted != null) {
                commentRepository.delete(commentWillBeDeleted)
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