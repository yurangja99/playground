package com.namsaeng.playground.entities

import java.util.*
import javax.persistence.*

@Table(name="comment")
@Entity
class CommentEntity (
        @Column(name="user_id") var userId: Int,
        @Column(name="topic_id") var topicId: Int,
        @Column(name="like_cnt") var like: Int,
        @Column(name="content") var content: String,
        @Column(name="modified") var modified: Date = Date(),
        @Id @GeneratedValue(strategy=GenerationType.IDENTITY) var id: Long = 0
)