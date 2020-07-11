package com.namsaeng.playground.controllers

import com.namsaeng.playground.entities.TailCommentEntity
import com.namsaeng.playground.repositories.TailCommentRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Api(tags=["4. 답글"])
@CrossOrigin(origins=["http://localhost:3000"])
@RestController
class TailCommentController {
    @Autowired
    lateinit var tailCommentRepository: TailCommentRepository

    // commentId의 값은 Int 일 수도, Long 일 수도 있음. 따라서 Long 으로 통일해야함.
    fun anyToLong(commentId: Any?): Long {
        return if (commentId is Int) (commentId as Int).toLong()
        else commentId as Long
    }

    // CREATE

    // 새로운 답글 생성
    @ApiOperation(value="새 답글 추가", notes="새 답글을 데이터베이스에 추가합니다.")
    @PostMapping("/tailcomment")
    fun createTailComment(@RequestBody tailcomment: HashMap<String, Any>): HashMap<String, Any?> {
        return try {
            val tailCommentAsTailCommentEntity = TailCommentEntity(
                    tailcomment["userId"] as Int,
                    anyToLong(tailcomment["commentId"]),
                    tailcomment["content"] as String
            )
            val resultOfSave = tailCommentRepository.save(tailCommentAsTailCommentEntity)
            hashMapOf(Pair("data", resultOfSave))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // READ

    // tail_comment DB 전체를 반환
    @ApiOperation(value="답글 데이터베이스 반환", notes="답글 전체의 데이터베이스를 반환합니다.")
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

    // 특정 답글 정보 열람
    @ApiOperation(value="특정 답글 정보 반환", notes="특정 답글의 데이터를 반환합니다.")
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

    // 특정 의견 댓글의 답글 리스트 반환
    @ApiOperation(value="특정 의견 댓글의 답글 리스트 반환", notes="특정 의견 댓글에 달린 답글의 리스트를 반환합니다.")
    @GetMapping("/tailcomments")
    fun readTailComments(commentId: Long): HashMap<String, Any?> {
        return try{
            val tailCommentsOfTopic = tailCommentRepository.findByCommentId(commentId)
            return hashMapOf(Pair("data", tailCommentsOfTopic))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // UPDATE

    // 답글 정보 변경
    // userId?, commentId?, content?
    @ApiOperation(value="특정 답글 정보 수정", notes="특정 답글 데이터를 수정하고, 수정된 데이터를 반환합니다.")
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
                hashMapOf(Pair("data", null))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // DELETE

    // 기존의 답글 제거
    @ApiOperation(value="특정 답글 정보 삭제", notes="특정 답글 데이터를 삭제하고, 그 id를 반환합니다.")
    @DeleteMapping("/tailcomment")
    fun deleteTailComment(id: Long): HashMap<String, Any?> {
        return try {
            val tailCommentWillBeDeleted = tailCommentRepository.findById(id).orElse(null)
            if (tailCommentWillBeDeleted != null) {
                tailCommentRepository.delete(tailCommentWillBeDeleted)
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