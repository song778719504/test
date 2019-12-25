package com.zyw.demo3.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {




    @GetMapping("/testJson")
    public String testJson(){
        System.out.println("zyw");
        return "Hello SpringBoot!";
    }
}
