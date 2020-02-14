package com.hh.demo.service;

import com.hh.demo.common.ServerResponse;
import com.hh.demo.entity.Category;

public interface ICategoryService {

    ServerResponse addCategory(Integer parentId, String categoryName);

    ServerResponse setCategoryName(Integer categoryId,String categoryName);

    ServerResponse getCategoriesByParentId(Integer parentId);

    ServerResponse getDeepCategories(Integer categoryId);

}
