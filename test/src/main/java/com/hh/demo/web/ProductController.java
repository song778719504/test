package com.hh.demo.web;

import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/manage/product/")
public class ProductController {

    @Value(("${upload.uploadPath}"))
    String uploadPath;

    @RequestMapping(value = "upload",method = RequestMethod.GET)
    public ModelAndView upload(){
        return new ModelAndView("upload");
    }

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public ServerResponse upload(@RequestParam("picture") MultipartFile file){

        String name = file.getOriginalFilename();
        String ext = name.substring(name.lastIndexOf("."));
        if (!(".jpg".equals(ext) || ".bmp".equals(ext) || ".png".equals(ext) || ".gif".equals(ext) || ".tif".equals(ext))){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.FILE_KIND_FAIL.getStatus(),
                    StatusEnum.FILE_KIND_FAIL.getMsg()
            );
        }
        String filename = UUID.randomUUID().toString();
        String newFileName = filename+ext;
        File target = new File(uploadPath);
        if (!target.exists()){
            target.mkdirs();
        }
        File newFile = new File(uploadPath+"\\"+newFileName);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ServerResponse.serverResponseBySuccess("上传成功",null);

    }

}
