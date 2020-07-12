package com.namsaeng.playground.controllers

import com.namsaeng.playground.entities.UserEntity
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File

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

    // 사진입력 테스트
    @ApiOperation(value="파일 업로드 테스트", notes="사진 등의 파일을 C:\\에 업로드한 후, 경로를 반환합니다.")
    @PostMapping("/picture")
    fun pictureTest(
            @RequestParam("filename") mFile: MultipartFile
    ): HashMap<String, Any> {
        return try {
            // 적당한 중복되지 않은 path를 찾은 후, 저장. 저장된 path 반환
            var filename = mFile.originalFilename
            while (File("C:/$filename").exists())
                filename = "A$filename"
            val file = File("C:/$filename")
            mFile.transferTo(file)
            hashMapOf(Pair("data", file.absolutePath))
        } catch (e: Exception) {
            e.printStackTrace()
            hashMapOf(Pair("error", "error"))
        }
    }
}