package com.hh.demo.web;

import com.hh.demo.common.Consts;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.entity.pojo.User;
import com.hh.demo.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category/")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @RequestMapping("add_category.do")
    public ServerResponse addCategory(@RequestParam(value = "parentId",defaultValue = "0") Integer parentId,
                                      @RequestParam("categoryName") String categoryName,
                                      HttpSession session){
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

        return categoryService.addCategory(parentId,categoryName);

    }

    //设置商品名字
    @RequestMapping("set_category_name.do")
    public ServerResponse setCategoryName(HttpSession session,
                                          @RequestParam("categoryId") Integer categoryId,
                                          @RequestParam("categoryName") String categoryName){
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

        return categoryService.setCategoryName(categoryId,categoryName);

    }

    //获得一级子类
    @RequestMapping("get_category.do")
    public ServerResponse getChildCategories(@RequestParam("categoryId") Integer categoryId){

        return categoryService.getCategoriesByParentId(categoryId);
    }

    //获得所有子类
    @RequestMapping("get_deep_category.do")
    public ServerResponse getDeepCategories(@RequestParam("categoryId") Integer categoryId){

        return categoryService.getDeepCategories(categoryId);
    }

}
