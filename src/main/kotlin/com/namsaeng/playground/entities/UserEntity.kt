package com.namsaeng.playground.entities

import java.util.*
import javax.persistence.*

@Table(name="user")
@Entity
class UserEntity (
        @Column(name="state") var state: String?,
        @Column(name="age") var age: Int?,
        @Column(name="gender") var gender: String?,
        @Column(name="name") var name: String,
        @Column(name="nickname") var nickname: String,
        @Column(name="password") var password: String,
        @Column(name="remark") var remark: Int?,
        @Column(name="profile_url") var profileUrl: String?,
        @Column(name="category") var category: String,
        @Column(name="survey_num") var surveyNum: Int?,
        @Column(name="survey_state") var surveyState: Int?,
        @Column(name="created") var created: Date = Date(),
        @Id @GeneratedValue(strategy=GenerationType.IDENTITY) var id: Int = 0
)