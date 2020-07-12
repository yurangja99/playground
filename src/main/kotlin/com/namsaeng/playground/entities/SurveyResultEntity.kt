package com.namsaeng.playground.entities

import java.util.*
import javax.persistence.*

@Table(name="survey_result")
@Entity
class SurveyResultEntity (
        @Column(name="user_id") var userId: Int,
        @Column(name="survey_id") var surveyId: Int,
        @Column(name="result") var result: Int,
        @Column(name="created") var created: Date = Date(),
        @Id @GeneratedValue(strategy=GenerationType.IDENTITY) var id: Int = 0
)