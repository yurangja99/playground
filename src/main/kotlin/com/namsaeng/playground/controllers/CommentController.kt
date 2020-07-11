package com.namsaeng.playground.controllers

import com.namsaeng.playground.entities.CommentEntity
import com.namsaeng.playground.repositories.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins= ["http://localhost:3000"])
@RestController
class CommentController {
    @Autowired
    lateinit var commentRepository: CommentRepository

    // CREATE

    // 새로운 의견 댓글 생성
    @PostMapping("/comment")
    fun createComment(@RequestBody comment: HashMap<String, Any>): HashMap<String, Any?> {
        return try {
            val commentAsCommentEntity = CommentEntity(
                    comment["userId"] as Int,
                    comment["topicId"] as Int, 0,
                    comment["content"] as String
            )
            val resultOfSave = commentRepository.save(commentAsCommentEntity)
            hashMapOf(Pair("data", resultOfSave))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // READ

    // comment DB 전체를 반환
    @GetMapping("/db/comment")
    fun readCommentDB(): HashMap<String, Any?> {
        return try {
            val allCommentRecords: Iterable<CommentEntity> = commentRepository.findAll()
            hashMapOf(Pair("data", allCommentRecords))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 특정 의견 댓글 정보 열람
    @GetMapping("/comment")
    fun readComment(id: Long): HashMap<String, Any?> {
        return try {
            val comment = commentRepository.findById(id).orElse(null)
            hashMapOf(Pair("data", comment))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // UPDATE

    // 의견 댓글 정보 변경
    // userId?, topicId?, like?, content?
    @PatchMapping("/comment")
    fun updateComment(id: Long, @RequestBody newComment: HashMap<String, Any>): HashMap<String, Any?> {
        return try {
            val commentWillBeUpdated = commentRepository.findById(id).orElse(null)
            if (commentWillBeUpdated != null) {
                if (newComment["userId"] != null) commentWillBeUpdated.userId = newComment["userId"] as Int
                if (newComment["topicId"] != null) commentWillBeUpdated.topicId = newComment["topicId"] as Int
                if (newComment["like"] != null) commentWillBeUpdated.like = newComment["like"] as Int
                if (newComment["content"] != null) commentWillBeUpdated.content = newComment["content"] as String
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
    @DeleteMapping("/comment")
    fun deleteComment(id: Long): HashMap<String, Any?> {
        return try {
            val commentWillBeDeleted = commentRepository.findById(id).orElse(null)
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