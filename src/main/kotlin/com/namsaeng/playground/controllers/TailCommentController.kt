package com.namsaeng.playground.controllers

import com.namsaeng.playground.entities.TailCommentEntity
import com.namsaeng.playground.repositories.TailCommentRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap

@Api(tags=["4. 답글 DB"])
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
    fun createTailComment(
            @ApiParam("userId, commentId, content") @RequestBody tailcomment: HashMap<String, Any>
    ): HashMap<String, Any?> {
        return try {
            // 새로운 TailCommentEntity 인스턴스 선언 후, DB에 추가
            val tailCommentAsTailCommentEntity: TailCommentEntity = TailCommentEntity(
                    tailcomment["userId"] as Int,
                    anyToLong(tailcomment["commentId"]),
                    tailcomment["content"] as String
            )
            val resultOfSave: TailCommentEntity = tailCommentRepository.save(tailCommentAsTailCommentEntity)
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
            // 데이터베이스의 모든 레코드 찾아서 반환.
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
            // 특정 id를 가진 레코드 혹은 null 반환
            val tailComment: TailCommentEntity? = tailCommentRepository.findById(id).orElse(null)
            hashMapOf(Pair("data", tailComment))
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
    fun updateTailComment(
            id: Long,
            @ApiParam("userId?, commentId?, content?") @RequestBody newTailComment: HashMap<String, Any>
    ): HashMap<String, Any?> {
        return try {
            // 주어진 id를 가진 레코드를 찾고, 각 필드가 존재하면, 변경한다.
            val tailCommentWillBeUpdated: TailCommentEntity? = tailCommentRepository.findById(id).orElse(null)
            if (tailCommentWillBeUpdated != null) {
                if (newTailComment["userId"] != null) tailCommentWillBeUpdated.userId = newTailComment["userId"] as Int
                if (newTailComment["commentId"] != null) tailCommentWillBeUpdated.commentId = anyToLong(newTailComment["commentId"])
                if (newTailComment["content"] != null) tailCommentWillBeUpdated.content = newTailComment["content"] as String
                tailCommentWillBeUpdated.modified = Date()
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
            // 주어진 id를 가진 레코드를 삭제한다.
            val tailCommentWillBeDeleted: TailCommentEntity? = tailCommentRepository.findById(id).orElse(null)
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