package com.namsaeng.playground

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController {
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var topicRepository: TopicRepository
    @Autowired
    lateinit var commentRepository: CommentRepository
    @Autowired
    lateinit var tailCommentRepository: TailCommentRepository

    @GetMapping("/")
    fun test(): String {
        return "<h2>API Document</h2>" +
                "<p><b>/db/user</b>: user DB 전체를 반환 (사용자, 마스터, 관리자 명단)</p>" +
                "<p><b>/db/topic</b>: topic DB 전체를 반환 (토론 주제, 세부 내용 리스트)</p>" +
                "<p><b>/db/comment</b>: comment DB 전체를 반환 (전체 의견 리스트)</p>" +
                "<p><b>/db/tailcomment</b>: tail_comment DB 전체를 반환 (전체 답글 리스트)</p>" +
                "<p><b>/titles</b>: id, 토론 주제, 토론 등록 시각 리스트 반환</p>"
    }

    // user DB 전체를 반환
    @GetMapping("/db/user")
    fun returnUserDB(): HashMap<String, Any?> {
        return try {
            val allUserRecords: Iterable<UserEntity> = userRepository.findAll()
            hashMapOf(Pair("data", allUserRecords))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // topic DB 전체를 반환
    @GetMapping("/db/topic")
    fun returnTopiocDB(): HashMap<String, Any?> {
        return try {
            val allTopicRecords: Iterable<TopicEntity> = topicRepository.findAll()
            hashMapOf(Pair("data", allTopicRecords))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // comment DB 전체를 반환
    @GetMapping("/db/comment")
    fun returnCommentDB(): HashMap<String, Any?> {
        return try {
            val allCommentRecords: Iterable<CommentEntity> = commentRepository.findAll()
            hashMapOf(Pair("data", allCommentRecords))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // tail_comment DB 전체를 반환
    @GetMapping("/db/tailcomment")
    fun returnTailCommentDB(): HashMap<String, Any?> {
        return try {
            val allTailCommentRecords: Iterable<TailCommentEntity> = tailCommentRepository.findAll()
            hashMapOf(Pair("data", allTailCommentRecords))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 토론 주제 목록의 반환
    @GetMapping("/titles")
    fun returnTitles(): HashMap<String, Any?> {
        return try {
            val idsAndTitles: Iterable<Array<Any>> = topicRepository.findTitlesAndCreated()
            val idsAndTitlesJson: Iterable<HashMap<String, Any>> =
                idsAndTitles.map {it -> hashMapOf(Pair("id", it[0]), Pair("title", it[1]), Pair("created", it[2]))}
            hashMapOf(Pair("data", idsAndTitlesJson))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }
}
