package com.springboot.securitytest.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="예제 API", description="Swagger 테스트용 API")
@RestController
@RequestMapping
public class ExampleController {

    @Operation(summary = "문자열 반복", description="파라미터로 받은 문자열을 2번 반복한다")
    @Parameter(name="str", description="2번 반복할 문자열")
    @GetMapping("return-str")
    public String returnStr(@RequestParam String str){
        return str + " / " + str;
    }

    @Operation(summary="중복 확인", description="중복 아이디 입력 시, 중복 예외가 발생한다.")
    @ApiResponses({
            @ApiResponse(responseCode="200", description = "정상 회원가입 됨."),
            @ApiResponse(responseCode = "400",description = "아이디 중복으로 회원가입 불가.")
    })
    @GetMapping("check-duplication")
    public ResponseEntity<String> checkDuplication(@RequestParam String id){

        if(id.equals("aaa")){
            return ResponseEntity.status(400).body("aaa는 이미 있는 아이디입니다.");
        }
        return ResponseEntity.status(200).body(id +"님 회원가입이 완료되었습니다.");
    }

    @GetMapping("example")
    public String example(){
        return "예시 API";
    }

    @Hidden
    @GetMapping("ignore")
    public String ignore(){
        return "무시되는 API";
    }

}