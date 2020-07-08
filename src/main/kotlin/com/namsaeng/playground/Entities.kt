package com.namsaeng.playground

import java.util.*
import javax.persistence.*

@Entity
@Table(name="user")
class UserEntity(
    @Column(name="state") var state: String?,
    @Column(name="school") var school: String?,
    @Column(name="grade") var grade: Int?,
    @Column(name="class") var classNum: Int?,
    @Column(name="number") var number: Int?,
    @Column(name="name") var name: String,
    @Column(name="category") var category: String,
    @Column(name="created") var created: Date = Date(),
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) var id: Int = 0
)

@Entity
@Table(name="topic")
class TopicEntity(
    @Column(name="user_id") var userId: Int? = null,
    @Column(name="title") var title: String,
    @Column(name="content") var content: String? = null,
    @Column(name="created") var created: Date? = Date(),
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) var id: Int = 0
)

@Entity
@Table(name="comment")
class CommentEntity(
    @Column(name="user_id") var userId: Int,
    @Column(name="topic_id") var topicId: Int,
    @Column(name="like_cnt") var like: Int,
    @Column(name="content") var content: String,
    @Column(name="created") var created: Date = Date(),
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) var id: Long = 0
)

@Entity
@Table(name="tail_comment")
class TailCommentEntity(
    @Column(name="user_id") var userId: Int,
    @Column(name="comment_id") var topicId: Long,
    @Column(name="content") var content: String,
    @Column(name="created") var created: Date = Date(),
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) var id: Long = 0
)