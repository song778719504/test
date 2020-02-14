package com.hh.demo.web;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage/product/")
public class ProductController {


    String uploadPath;

    @RequestMapping("upload")
    public String upuload(){
        return "upload";
    }

}
