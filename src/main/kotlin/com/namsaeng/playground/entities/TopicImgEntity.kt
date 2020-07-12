package com.namsaeng.playground.entities

import javax.persistence.*

@Table(name="topic_img")
@Entity
class TopicImgEntity (
        @Column(name="topic_id") var topicId: Int,
        @Column(name="url") var url: String,
        @Id @GeneratedValue(strategy=GenerationType.IDENTITY) var id: Int = 0
)