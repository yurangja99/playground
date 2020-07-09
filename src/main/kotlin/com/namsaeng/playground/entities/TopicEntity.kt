package com.namsaeng.playground.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name="topic")
class TopicEntity(
        @Column(name="user_id") var userId: Int,
        @Column(name="title") var title: String,
        @Column(name="content") var content: String,
        @Column(name="created") var created: Date = Date(),
        @Id @GeneratedValue(strategy= GenerationType.IDENTITY) var id: Int = 0
)