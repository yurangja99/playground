package com.namsaeng.playground.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name="tail_comment")
class TailCommentEntity(
        @Column(name="user_id") var userId: Int,
        @Column(name="comment_id") var commentId: Long,
        @Column(name="content") var content: String,
        @Column(name="created") var created: Date = Date(),
        @Id @GeneratedValue(strategy=GenerationType.IDENTITY) var id: Long = 0
)