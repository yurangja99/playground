package com.namsaeng.playground.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name="comment")
class CommentEntity(
        @Column(name="user_id") var userId: Int,
        @Column(name="topic_id") var topicId: Int,
        @Column(name="like_cnt") var like: Int,
        @Column(name="content") var content: String,
        @Column(name="created") var created: Date = Date(),
        @Id @GeneratedValue(strategy= GenerationType.IDENTITY) var id: Long = 0
)