package com.namsaeng.playground.repositories

import com.namsaeng.playground.entities.SurveyResultEntity
import org.springframework.data.repository.CrudRepository

// 기본 제공 함수: save(), findOne(), findAll(), count(), delete()
interface SurveyResultRepository: CrudRepository<SurveyResultEntity, Int> {
}