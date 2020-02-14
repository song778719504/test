package com.hh.demo.service.impl;

import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.dao.CategoryMapper;
import com.hh.demo.entity.Category;
import com.hh.demo.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService implements ICategoryService {

    @Resource
    CategoryMapper categoryMapper;


    @Override
    public ServerResponse addCategory(Integer parentId, String categoryName) {

        //商品名称非空判断
        if (categoryName == null || "".equals(categoryName)){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.CATEGORY_NAME_NOT_EMPTY.getStatus(),
                    StatusEnum.CATEGORY_NAME_NOT_EMPTY.getMsg());
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        int result = categoryMapper.insert(category);
        if (result <= 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.CATEGORY_INSERT_FAIL.getStatus(),
                    StatusEnum.CATEGORY_INSERT_FAIL.getMsg());
        }

        return ServerResponse.serverResponseBySuccess("添加成功",null);
    }

    @Override
    public ServerResponse setCategoryName(Integer categoryId, String categoryName) {

        //商品id非空判断
        if (categoryId == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.CATEGORY_NAME_NOT_EMPTY.getStatus(),
                    StatusEnum.CATEGORY_NAME_NOT_EMPTY.getMsg());
        }
        //商品名称非空判断
        if (categoryName == null || "".equals(categoryName)){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.CATEGORY_NAME_NOT_EMPTY.getStatus(),
                    StatusEnum.CATEGORY_NAME_NOT_EMPTY.getMsg());
        }
        //判断表中有此id数据
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.CATEGORY_NOT_EXISTS.getStatus(),
                    StatusEnum.CATEGORY_NOT_EXISTS.getMsg());
        }
        category.setName(categoryName);
        System.out.println(category.getId());
        System.out.println(category.getName());
        int result = categoryMapper.updateByPrimaryKey(category);
        if (result <= 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.CATEGORY_UPDATE_FAIL.getStatus(),
                    StatusEnum.CATEGORY_UPDATE_FAIL.getMsg());
        }

        return ServerResponse.serverResponseBySuccess("修改成功",null);
    }

    @Override
    public ServerResponse getCategoriesByParentId(Integer parentId) {

        //参数非空判断
        if (parentId == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.CATEGORY_ID_NOT_EMPTY.getStatus(),
                    StatusEnum.CATEGORY_ID_NOT_EMPTY.getMsg()
            );
        }
        List<Category> categories = categoryMapper.selectByParentId(parentId);
        if (categories == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.CATEGORY_SELECT_FAIL.getStatus(),
                    StatusEnum.CATEGORY_SELECT_FAIL.getMsg());
        }

        return ServerResponse.serverResponseBySuccess("查询成功",categories);
    }

    @Override
    public ServerResponse getDeepCategories(Integer categoryId) {

        //参数非空判断
        if (categoryId == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.CATEGORY_ID_NOT_EMPTY.getStatus(),
                    StatusEnum.CATEGORY_ID_NOT_EMPTY.getMsg());
        }
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        //判断类别存在
        if (category == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.CATEGORY_NOT_EXISTS.getStatus(),
                    StatusEnum.CATEGORY_NOT_EXISTS.getMsg());
        }
        Set<Integer> resultSet = new HashSet<>();
        resultSet.add(categoryId);
        findAllChildCategories(resultSet,categoryId);

        return ServerResponse.serverResponseBySuccess(null,resultSet);
    }

    private Set<Integer> findAllChildCategories(Set<Integer> resultSet,
                                                Integer categoryId){

        List<Category> categories = categoryMapper.selectByParentId(categoryId);
        if (categories == null){
            return resultSet;
        }
        for (Category c : categories){
            resultSet.add(c.getId());
            findAllChildCategories(resultSet,c.getId());
        }
        return resultSet;
    }

}
