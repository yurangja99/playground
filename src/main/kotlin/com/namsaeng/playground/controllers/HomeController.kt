package com.namsaeng.playground.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags=["0. 홈"])
@CrossOrigin(origins=["http://localhost:3000"])
@RestController
class HomeController {
    // 웹 서비스 설명 글
    @ApiOperation(value="웹 서비스 설명문", notes="웹 서비스에 대한 설명 텍스트를 반환한다.")
    @GetMapping("/introduction")
    fun introduction(): HashMap<String, Any?> {
        return hashMapOf(Pair("data", "이 사이트는 좋은 사이트입니다."))
    }

    // 개인정보처리방침
    @ApiOperation(value="개인정보처리방침", notes="개인정보처리방침 텍스트를 반환한다.")
    @GetMapping("/infopolicy")
    fun infoPolicy(): HashMap<String, Any?> {
        return hashMapOf(Pair("data", "웹 서비스 유저의 정보는 사용하지 않을 것입니다."))
    }
}