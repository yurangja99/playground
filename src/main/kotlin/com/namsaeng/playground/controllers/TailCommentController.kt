package com.namsaeng.playground.controllers

import com.namsaeng.playground.entities.TailCommentEntity
import com.namsaeng.playground.repositories.TailCommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class TailCommentController {
    @Autowired
    lateinit var tailCommentRepository: TailCommentRepository

    // commentId의 값은 Int 일 수도, Long 일 수도 있음. 따라서 Long 으로 통일해야함.
    fun anyToLong(commentId: Any?): Long {
        return if (commentId is Int) (commentId as Int).toLong()
        else commentId as Long
    }

    // tail_comment DB 전체를 반환
    @GetMapping("/db/tailcomment")
    fun readTailCommentDB(): HashMap<String, Any?> {
        return try {
            val allTailCommentRecords: Iterable<TailCommentEntity> = tailCommentRepository.findAll()
            hashMapOf(Pair("data", allTailCommentRecords))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 새로운 답글 생성
    @PostMapping("/tailcomment")
    fun createTailComment(@RequestBody tailcomment: HashMap<String, Any>): HashMap<String, Any?> {
        return try {
            val tailCommentAsTailCommentEntity = TailCommentEntity(
                    tailcomment["userId"] as Int,
                    anyToLong(tailcomment["commentId"]),
                    tailcomment["content"] as String
            )
            val resultOfSave = tailCommentRepository.save(tailCommentAsTailCommentEntity)
            hashMapOf(Pair("data", resultOfSave.id))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 특정 답글 정보 열람
    @GetMapping("/tailcomment")
    fun readTailComment(id: Long): HashMap<String, Any?> {
        return try {
            val tailComment = tailCommentRepository.findById(id).orElse(null)
            hashMapOf(Pair("data", tailComment))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 답글 정보 변경
    // userId?, commentId?, content?
    @PatchMapping("/tailcomment")
    fun updateTailComment(id: Long, @RequestBody newTailComment: HashMap<String, Any>): HashMap<String, Any?> {
        return try {
            val tailCommentWillBeUpdated = tailCommentRepository.findById(id).orElse(null)
            if (tailCommentWillBeUpdated != null) {
                if (newTailComment["userId"] != null) tailCommentWillBeUpdated.userId = newTailComment["userId"] as Int
                if (newTailComment["commentId"] != null) tailCommentWillBeUpdated.commentId = anyToLong(newTailComment["commentId"])
                if (newTailComment["content"] != null) tailCommentWillBeUpdated.content = newTailComment["content"] as String
                tailCommentRepository.save(tailCommentWillBeUpdated)
                hashMapOf(Pair("data", tailCommentWillBeUpdated))
            } else {
                hashMapOf(Pair("data", -1))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 기존의 답글 제거
    @DeleteMapping("/tailcomment")
    fun deleteTailComment(id: Long): HashMap<String, Any?> {
        return try {
            val tailCommentWillBeDeleted = tailCommentRepository.findById(id).orElse(null)
            if (tailCommentWillBeDeleted != null) {
                tailCommentRepository.delete(tailCommentWillBeDeleted)
                hashMapOf(Pair("data", id))
            } else {
                hashMapOf(Pair("data", -1))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }
}