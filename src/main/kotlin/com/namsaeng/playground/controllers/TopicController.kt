package com.namsaeng.playground.controllers

import com.namsaeng.playground.entities.TopicEntity
import com.namsaeng.playground.repositories.TopicRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Api(tags=["2. 토론 주제"])
@CrossOrigin(origins=["http://localhost:3000"])
@RestController
class TopicController {
    @Autowired
    lateinit var topicRepository: TopicRepository

    // CREATE

    // 새로운 주제 생성
    @ApiOperation(value="새 토론 주제 추가", notes="새 토론 주제 데이터베이스에 추가합니다.")
    @PostMapping("/topic")
    fun createTopic(@RequestBody topic: HashMap<String, Any>): HashMap<String, Any?> {
        return try {
            val topicAsTopicEntity = TopicEntity(
                    topic["userId"] as Int,
                    topic["title"] as String,
                    topic["content"] as String
            )
            val resultOfSave = topicRepository.save(topicAsTopicEntity)
            hashMapOf(Pair("data", resultOfSave))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // READ

    // topic DB 전체를 반환
    @ApiOperation(value="토론 주제 데이터베이스 반환", notes="토론 주제 전체의 데이터베이스를 반환합니다.")
    @GetMapping("/db/topic")
    fun readTopicDB(): HashMap<String, Any?> {
        return try {
            val allTopicRecords: Iterable<TopicEntity> = topicRepository.findAll()
            hashMapOf(Pair("data", allTopicRecords))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 특정 주제 정보 열람
    @ApiOperation(value="특정 토론 주제 정보 반환", notes="특정 토론 주제의 데이터를 반환합니다.")
    @GetMapping("/topic")
    fun readTopic(id: Int): HashMap<String, Any?> {
        return try {
            val topic = topicRepository.findById(id).orElse(null)
            hashMapOf(Pair("data", topic))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // 토론 주제 목록의 반환
    @ApiOperation(value="토론 주제 리스트 반환", notes="토론 주제의 id, 제목, 수정 시각 리스트를 반환합니다. ")
    @GetMapping("/titles")
    fun readTitles(): HashMap<String, Any?> {
        return try {
            val idsAndTitles: Iterable<Array<Any>> = topicRepository.findTitlesAndCreated()
            val idsAndTitlesJson: Iterable<HashMap<String, Any>> =
                    idsAndTitles.map { hashMapOf(Pair("id", it[0]), Pair("title", it[1]), Pair("created", it[2]))}
            hashMapOf(Pair("data", idsAndTitlesJson))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // UPDATE

    // 주제 정보 변경
    // userId?, title?, content?
    @ApiOperation(value="특정 토론 주제 정보 수정", notes="특정 토론 주제 데이터를 수정하고, 수정된 데이터를 반환합니다.")
    @PatchMapping("/topic")
    fun updateTopic(id: Int, @RequestBody newTopic: HashMap<String, Any>): HashMap<String, Any?> {
        return try {
            val topicWillBeUpdated = topicRepository.findById(id).orElse(null)
            if (topicWillBeUpdated != null) {
                if (newTopic["userId"] != null) topicWillBeUpdated.userId = newTopic["userId"] as Int
                if (newTopic["title"] != null) topicWillBeUpdated.title = newTopic["title"] as String
                if (newTopic["content"] != null) topicWillBeUpdated.content = newTopic["content"] as String
                topicRepository.save(topicWillBeUpdated)
                hashMapOf(Pair("data", topicWillBeUpdated))
            } else {
                hashMapOf(Pair("data", null))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("data", e.message))
        }
    }

    // DELETE

    // 기존의 주제 제거
    @ApiOperation(value="특정 토론 주제 정보 삭제", notes="특정 토론 주제 데이터를 삭제하고, 그 id를 반환합니다.")
    @DeleteMapping("/topic")
    fun deleteTopic(id: Int): HashMap<String, Any?> {
        return try {
            val topicWillBeDeleted = topicRepository.findById(id).orElse(null)
            if (topicWillBeDeleted != null) {
                topicRepository.delete(topicWillBeDeleted)
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