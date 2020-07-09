package com.namsaeng.playground.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {
    private final fun descToHTML(
            method: String,
            path: String,
            desc: String,
            queryParams: List<Pair<String, String>>,
            bodyParam: Pair<String, String>?
    ): String {
        val queryParamsAsString =
                if (queryParams.isNotEmpty())
                    queryParams.fold("<u>query parameters</u><br/><ul>",
                            {acc, it -> "$acc<li>${it.first} : ${it.second}</li>"}) + "</ul>"
                else
                    ""
        val bodyParamAsString =
                if (bodyParam != null)
                    "<u>body parameter</u></br><ul><li>${bodyParam.first} : ${bodyParam.second}</li></ul>"
                else
                    ""
        return "<p><mark>$method</mark>   <b>$path</b>   $desc<br/>$queryParamsAsString$bodyParamAsString</p>"
    }

    val apiDescList = listOf(
            descToHTML("GET", "/db/user", "user DB 전체를 반환 (사용자, 마스터, 관리자 명단)", listOf(), null),
            descToHTML("GET", "/db/topic", "topic DB 전체를 반환 (토론 주제, 세부 내용 리스트)", listOf(), null),
            descToHTML("GET", "/db/comment", "comment DB 전체를 반환 (전체 의견 리스트)", listOf(), null),
            descToHTML("GET", "/db/tailcomment", "tail_comment DB 전체를 반환 (전체 답글 리스트)", listOf(), null),
            descToHTML("GET", "/titles", "id, 토론 주제, 토론 등록 시각 리스트 반환", listOf(), null),
            descToHTML("GET", "/user", "user DB에서 주어진 id를 가진 레코드 반환. 실패 시 null 반환", listOf(Pair("id", "user의 고유 id")), null),
            descToHTML("GET", "/topic", "topic DB에서 주어진 id를 가진 레코드 반환. 실패 시 null 반환", listOf(Pair("id", "topic의 고유 id")), null),
            descToHTML("GET", "/comment", "comment DB에서 주어진 id를 가진 레코드 반환. 실패 시 null 반환", listOf(Pair("id", "comment의 고유 id")), null),
            descToHTML("GET", "/tailcomment", "tail_comment DB에서 주어진 id를 가진 레코드 반환. 실패 시 null 반환", listOf(Pair("id", "tail_comment의 고유 id")), null),
            descToHTML("POST", "/user", "json 형태의 유저 정보를 받아 user DB에 추가하고, 그 id 반환", listOf(), Pair("user", "json 형태(state?, school?, grade?, class?, number?, name, category)")),
            descToHTML("POST", "/topic", "json 형태의 주제 정보를 받아 topic DB에 추가하고, 그 id 반환", listOf(), Pair("topic", "json 형태(userId, title, content)")),
            descToHTML("POST", "/comment", "json 형태의 의견 댓글 정보를 받아 comment DB에 추가하고, 그 id 반환", listOf(), Pair("comment", "json 형태(userId, topicId, content)")),
            descToHTML("POST", "/tailcomment", "json 형태의 답글 정보를 받아 tail_comment DB에 추가하고, 그 id 반환", listOf(), Pair("tailcomment", "json 형태(userId, commentId, content)")),
            descToHTML("PATCH", "/user", "json 형태의 유저 정보를 받아 주어진 id를 가진 레코드의 정보 수정 후 수정된 레코드 반환, 실패 시 -1 반환", listOf(Pair("id", "user의 고유 id")), Pair("newUser", "json 형태(state?, school?, grade?, class?, number?, name?, category?)")),
            descToHTML("PATCH", "/topic", "json 형태의 주제 정보를 받아 주어진 id를 가진 레코드의 정보 수정 후 수정된 레코드 반환, 실패 시 -1 반환", listOf(Pair("id", "topic의 고유 id")), Pair("newTopicr", "json 형태(userId?, title?, content?)")),
            descToHTML("PATCH", "/comment", "json 형태의 의견 댓글 정보를 받아 주어진 id를 가진 레코드의 정보 수정 후 수정된 레코드 반환, 실패 시 -1 반환", listOf(Pair("id", "comment의 고유 id")), Pair("newComment", "json 형태(userId?, topicId?, like?, content?)")),
            descToHTML("PATCH", "/tailcomment", "json 형태의 답글 정보를 받아 주어진 id를 가진 레코드의 정보 수정 후 수정된 레코드 반환, 실패 시 -1 반환", listOf(Pair("id", "tail_comment의 고유 id")), Pair("newTailComment", "json 형태(userId?, commentId?, content?)")),
            descToHTML("DELETE", "/user", "user DB에서 주어진 id를 가진 레코드 삭제 후 그 id 반환. 실패 시 -1 반환", listOf(Pair("id", "user의 고유 id")), null),
            descToHTML("DELETE", "/topic", "topic DB에서 주어진 id를 가진 레코드 삭제 후 그 id 반환. 실패 시 -1 반환", listOf(Pair("id", "topic의 고유 id")), null),
            descToHTML("DELETE", "/comment", "comment DB에서 주어진 id를 가진 레코드 삭제 후 그 id 반환. 실패 시 -1 반환", listOf(Pair("id", "comment의 고유 id")), null),
            descToHTML("DELETE", "/tailcomment", "tail_comment DB에서 주어진 id를 가진 레코드 삭제 후 그 id 반환. 실패 시 -1 반환", listOf(Pair("id", "tail_comment의 고유 id")), null)
    )

    @GetMapping("/")
    fun root(): String {
        return "<title>API Document</title><h2>API Document</h2>" +
                apiDescList.fold("", {acc, it -> acc + it})
    }
}