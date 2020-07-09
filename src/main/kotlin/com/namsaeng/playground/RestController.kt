package com.namsaeng.playground

import com.namsaeng.playground.entities.*
import com.namsaeng.playground.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
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
    fun root(): String {
        fun descToHTML(path: String, method: String, params: List<String>, desc: String): String {
            val paramsAsString = params.fold("", {acc, it -> acc + it})
            return "<p><b>$path</b> <u>$method</u> [$paramsAsString]</p> $desc<br/><br/>"
        }
        return "<h2>API Document</h2>" +
                descToHTML("/db/user", "GET", listOf(), "user DB 전체를 반환 (사용자, 마스터, 관리자 명단)") +
                descToHTML("/db/topic", "GET", listOf(), "topic DB 전체를 반환 (토론 주제, 세부 내용 리스트)") +
                descToHTML("/db/comment", "GET", listOf(), "comment DB 전체를 반환 (전체 의견 리스트)") +
                descToHTML("/db/tailcomment", "GET", listOf(), "tail_comment DB 전체를 반환 (전체 답글 리스트)") +
                descToHTML("/titles", "GET", listOf(), "id, 토론 주제, 토론 등록 시각 리스트 반환") +
                descToHTML("/user", "POST", listOf("body.user"), "json 형태의 유저 정보를 받아 user DB에 추가하고, 그 id 반환") +
                descToHTML("/user", "DELETE", listOf("id"), "user DB에서 주어진 id를 가진 레코드 삭제 후 id 반환. 실패 시 -1 반환") +
                descToHTML("/topic", "POST", listOf("body.topic"), "json 형태의 주제 정보를 받아 user DB에 추가하고, 그 id 반환") +
                descToHTML("/topic", "DELETE", listOf("id"), "topic DB에서 주어진 id를 가진 레코드 삭제 후 id 반환. 실패 시 -1 반환") +
                descToHTML("/comment", "POST", listOf("body.comment"), "json 형태의 의견 댓글 정보를 받아 user DB에 추가하고, 그 id 반환") +
                descToHTML("/comment", "DELETE", listOf("id"), "comment DB에서 주어진 id를 가진 레코드 삭제 후 id 반환. 실패 시 -1 반환") +
                descToHTML("/tailcomment", "POST", listOf("body.tailcomment"), "json 형태의 답글 정보를 받아 user DB에 추가하고, 그 id 반환") +
                descToHTML("/tailcomment", "DELETE", listOf("id"), "tail_comment DB에서 주어진 id를 가진 레코드 삭제 후 id 반환. 실패 시 -1 반환")
    }

    // user DB 전체를 반환
    @GetMapping("/db/user")
    fun readUserDB(): HashMap<String, Any?> {
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
    fun readTopiocDB(): HashMap<String, Any?> {
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
    fun readCommentDB(): HashMap<String, Any?> {
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
    fun readTailCommentDB(): HashMap<String, Any?> {
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
    fun readTitles(): HashMap<String, Any?> {
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

    // 새로운 유저 생성
    @PostMapping("/user")
    fun createUser(@RequestBody user: HashMap<String, Any>): HashMap<String, Any?> {
        return try {
            val userAsUserEntity = UserEntity(
                    user["state"] as String?,
                    user["school"] as String?,
                    user["grade"] as Int?,
                    user["class"] as Int?,
                    user["number"] as Int?,
                    user["name"] as String,
                    user["category"] as String
            )
            val resultOfSave = userRepository.save(userAsUserEntity)
            hashMapOf(Pair("data", resultOfSave.id))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 새로운 주제 생성
    @PostMapping("/topic")
    fun createTopic(@RequestBody topic: HashMap<String, Any>): HashMap<String, Any?> {
        return try {
            val topicAsTopicEntity = TopicEntity(
                    topic["userId"] as Int,
                    topic["title"] as String,
                    topic["content"] as String
            )
            val resultOfSave = topicRepository.save(topicAsTopicEntity)
            hashMapOf(Pair("data", resultOfSave.id))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

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
            hashMapOf(Pair("data", resultOfSave.id))
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
                    if(tailcomment["commentId"] is Int)
                        (tailcomment["commentId"] as Int).toLong()
                    else
                        tailcomment["commentId"] as Long,
                    tailcomment["content"] as String
            )
            val resultOfSave = tailCommentRepository.save(tailCommentAsTailCommentEntity)
            hashMapOf(Pair("data", resultOfSave.id))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 기존의 유저 제거
    @DeleteMapping("/user")
    fun deleteUser(id: Int): HashMap<String, Any?> {
        return try {
            val userWillBeDeleted = userRepository.findById(id).orElse(null)
            if (userWillBeDeleted != null) {
                userRepository.delete(userWillBeDeleted)
                hashMapOf(Pair("data", id))
            } else {
                hashMapOf(Pair("data", -1))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 기존의 주제 제거
    @DeleteMapping("/topic")
    fun deleteTopic(id: Int): HashMap<String, Any?> {
        return try {
            val topicWillBeDeleted = topicRepository.findById(id).orElse(null)
            if (topicWillBeDeleted != null) {
                topicRepository.delete(topicWillBeDeleted)
                hashMapOf(Pair("data", id))
            } else {
                hashMapOf(Pair("data", -1))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 기존의 의견 댓글 제거.
    @DeleteMapping("/comment")
    fun deleteComment(id: Long): HashMap<String, Any?> {
        return try {
            val commentWillBeDeleted = commentRepository.findById(id).orElse(null)
            if (commentWillBeDeleted != null) {
                commentRepository.delete(commentWillBeDeleted)
                hashMapOf(Pair("data", id))
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