package com.test.createdatetest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {

    private final Serv serv;
    @GetMapping("createDate")
    public void createDateTest(){
        serv.save("하이");
    }

    @GetMapping("updateDate/{id}")
    public void updateDateTest(@PathVariable Long id){

    }

    @GetMapping("test02")
    public String test02(){
        return "Test: test02 브렌치";
    }

    @GetMapping("test02_1")
    public String test02_1(){
        return "Test: test02_1 브렌치";
    }

    @GetMapping("test01")
    public String test01(){
        return "Test: test01 브렌치";
    }

    @GetMapping("test01-1")
    public String test01_1(){
        return "Test: test01 브렌치2";
    }

}
