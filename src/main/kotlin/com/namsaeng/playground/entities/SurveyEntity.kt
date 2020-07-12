package com.namsaeng.playground.entities

import java.util.*
import javax.persistence.*

@Table(name="survey")
@Entity
class SurveyEntity (
        @Column(name="type") var type: Int,
        @Column(name="number") var number: Int,
        @Column(name="content") var content: String,
        @Column(name="modified") var modified: Date = Date(),
        @Id @GeneratedValue(strategy=GenerationType.IDENTITY) var id: Int = 0
)