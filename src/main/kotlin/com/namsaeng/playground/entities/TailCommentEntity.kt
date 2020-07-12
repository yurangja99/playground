package com.namsaeng.playground.entities

import java.util.*
import javax.persistence.*

@Table(name="tail_comment")
@Entity
class TailCommentEntity (
        @Column(name="user_id") var userId: Int,
        @Column(name="comment_id") var commentId: Long,
        @Column(name="content") var content: String,
        @Column(name="modified") var modified: Date = Date(),
        @Id @GeneratedValue(strategy=GenerationType.IDENTITY) var id: Long = 0
)