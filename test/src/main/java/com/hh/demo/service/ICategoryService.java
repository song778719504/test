package com.hh.demo.service;

import com.hh.demo.common.ServerResponse;

public interface ICategoryService {

    ServerResponse addCategory(Integer parentId, String categoryName);

    ServerResponse setCategoryName(Integer categoryId,String categoryName);

    ServerResponse getCategoriesByParentId(Integer parentId);

    ServerResponse getDeepCategories(Integer categoryId);

}
