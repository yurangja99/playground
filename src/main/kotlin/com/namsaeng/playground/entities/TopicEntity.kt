package com.namsaeng.playground.entities

import java.util.*
import javax.persistence.*

@Table(name="topic")
@Entity
class TopicEntity (
        @Column(name="user_id") var userId: Int,
        @Column(name="state") var state: String?,
        @Column(name="title") var title: String,
        @Column(name="content") var content: String,
        @Column(name="thumb_url") var thumbUrl: String?,
        @Column(name="modified") var modified: Date = Date(),
        @Id @GeneratedValue(strategy=GenerationType.IDENTITY) var id: Int = 0
)