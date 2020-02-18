package com.hh.demo.web;

import com.hh.demo.common.Consts;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.entity.pojo.Product;
import com.hh.demo.entity.pojo.User;
import com.hh.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/manage/product/")
public class ProductController {

    @Value(("${upload.uploadPath}"))
    String uploadPath;

    @Autowired
    IProductService productService;

    //通过视图来访问上传地址
    @RequestMapping(value = "upload",method = RequestMethod.GET)
    public ModelAndView upload(){
        return new ModelAndView("upload");
    }

    //上传图片
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public ServerResponse upload(@RequestParam("picture") MultipartFile file){

        if (file == null){
            return null;
        }


        //获得文件扩展名
        String name = file.getOriginalFilename();
        String ext = name.substring(name.lastIndexOf("."));
        //判断文件类型，只能是图片
        if (!(".jpg".equals(ext) || ".bmp".equals(ext) || ".png".equals(ext) || ".gif".equals(ext) || ".tif".equals(ext))){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.FILE_KIND_FAIL.getStatus(),
                    StatusEnum.FILE_KIND_FAIL.getMsg()
            );
        }
        //生成新的文件名
        String filename = UUID.randomUUID().toString();
        String newFileName = filename+ext;
        //通过定义的文件存储位置生成指定文件夹
        File target = new File(uploadPath);
        if (!target.exists()){
            target.mkdirs();
        }
        //传入新的文件
        File newFile = new File(uploadPath+"\\"+newFileName);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ServerResponse.serverResponseBySuccess("上传成功",null);

    }


    //添加或跟新产品
    @RequestMapping("/save.do")
    public ServerResponse addOrUpdate(Product product, HttpSession session){

        //登录判断
        User user = (User)session.getAttribute(Consts.USER);
        if (user == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.NO_LOGIN.getStatus(),
                    StatusEnum.NO_LOGIN.getMsg()
            );
        }
        //管理员权限认证
        if (user.getRole() != 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.NO_AUTHORITY.getStatus(),
                    StatusEnum.NO_AUTHORITY.getMsg()
            );
        }
        return productService.addOrUpdate(product);
    }
}
