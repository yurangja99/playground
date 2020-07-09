package com.namsaeng.playground.entities

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
        @Id @GeneratedValue(strategy= GenerationType.IDENTITY) var id: Int = 0
)