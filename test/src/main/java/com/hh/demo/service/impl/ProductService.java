package com.hh.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hh.demo.common.ProductStatusEnum;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.dao.ProductMapper;
import com.hh.demo.entity.VO.ProductDetailVo;
import com.hh.demo.entity.VO.ProductListVO;
import com.hh.demo.entity.pojo.Product;
import com.hh.demo.exception.BusinessException;
import com.hh.demo.service.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class ProductService implements IProductService {

    @Resource
    ProductMapper productMapper;
    @Resource
    CategoryService categoryService;


    @Override
    public ServerResponse addOrUpdate(Product product) {

        //产品非空判断
        if (product == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PRODUCT_NOT_EMPTY.getStatus(),
                    StatusEnum.PRODUCT_NOT_EMPTY.getMsg());
        }
        //种类id非空判断
        if (product.getCategoryId() == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.CATEGORY_ID_NOT_EMPTY.getStatus(),
                    StatusEnum.CATEGORY_ID_NOT_EMPTY.getMsg());
        }
        //商品名称非空判断
        if (product.getName() == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PRODUCT_NAME_NOT_EMPTY.getStatus(),
                    StatusEnum.PRODUCT_NAME_NOT_EMPTY.getMsg());
        }
        //商品价格非空判断
        if (product.getPrice() == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PRODUCT_PRICE_NOT_EMPTY.getStatus(),
                    StatusEnum.PRODUCT_PRICE_NOT_EMPTY.getMsg());
        }
        //商品数量非空判断
        if (product.getStock() == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PRODUCT_STOCK_NOT_EMPTY.getStatus(),
                    StatusEnum.PRODUCT_STOCK_NOT_EMPTY.getMsg());
        }
        String imgs = product.getSubImages();
        if (imgs != null && imgs.length() > 0 && product.getMainImage() == null){
            String mainImg = imgs.split(",")[0];
            product.setMainImage(mainImg);
        }

        //产品id非空判断，空为新增，不空为更新
        if (product.getId() == null){
            int result = productMapper.insert(product);
            if (result <= 0 ){
                return ServerResponse.serverResponseByFail(
                        StatusEnum.PRODUCT_INSERT_FAIL.getStatus(),
                        StatusEnum.PRODUCT_INSERT_FAIL.getMsg());
            }
            return ServerResponse.serverResponseBySuccess("新增商品成功",null);
        }else {
            //判断产品存在
            Product p = productMapper.selectByPrimaryKey(product.getId());
            if (p == null){
                return ServerResponse.serverResponseByFail(
                        StatusEnum.PRODUCT_NOT_EXISTS.getStatus(),
                        StatusEnum.PRODUCT_NOT_EXISTS.getMsg());
            }

            //更新产品
            int result = productMapper.updateByPrimaryKey(product);
            //更新失败
            if (result <= 0){
                return ServerResponse.serverResponseByFail(
                        StatusEnum.PRODUCT_UPDATE_FAIL.getStatus(),
                        StatusEnum.PRODUCT_UPDATE_FAIL.getMsg());
            }
            return ServerResponse.serverResponseBySuccess("更新商品成功",null);
        }

    }

    @Override
    public ServerResponse list(Integer categoryId,
                               String keyword,
                               Integer pageNum,
                               Integer pageSize,
                               String orderBy) {

        //判断是否传入了categoryId和keyword
        if (categoryId == -1 && (keyword == null || "".equals(keyword))){
            PageHelper.startPage(pageNum,pageSize);
            List<Product> productList = new ArrayList<>();
            PageInfo pageInfo = new PageInfo(productList);
            return ServerResponse.serverResponseBySuccess(null,pageInfo);
        }

        //创建集合保存类别id
        List<Integer> categoryIdList = new ArrayList<>();
        //类别id非空判断，通过类别业务逻辑查询子类别的方法查出所有相关类别
        if (categoryId != -1){
            ServerResponse<Set<Integer>> response = categoryService.getDeepCategories(categoryId);
            if (response.isSuccess()){
                Set<Integer> categoryIdSet = response.getData();
                Iterator<Integer> iterator = categoryIdSet.iterator();
                while (iterator.hasNext()){
                    categoryIdList.add(iterator.next());
                }
            }
        }
        if (keyword != null){
            keyword = "%"+keyword+"%";
        }
        PageHelper.startPage(pageNum,pageSize);

        if (orderBy != null && !"".equals(orderBy)){
            /*String[] orders = orderBy.split("_");
            PageHelper.orderBy(orders[0]+" "+orders[1]);*/
            PageHelper.orderBy(orderBy);
        }

        List<Product> productList = productMapper.findProductByCategoryIdsAndKeyword(categoryIdList,keyword);

        List<ProductListVO> productListVOList = new ArrayList<>();
        for (Product p : productList){
            ProductListVO productListVO = new ProductListVO();
            productListVO.change(p);
            productListVOList.add(productListVO);
        }


        PageInfo pageInfo = new PageInfo(productListVOList);
        return ServerResponse.serverResponseBySuccess("",pageInfo);
    }

    @Override
    public ServerResponse detail(Integer productId) {

        if (productId == null) {
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PARAM_NOT_EMPTY.getStatus(),
                    StatusEnum.PARAM_NOT_EMPTY.getMsg()
            );
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PRODUCT_NOT_EXISTS.getStatus(),
                    StatusEnum.PRODUCT_NOT_EXISTS.getMsg()
            );
        }
        if (product.getStatus() != ProductStatusEnum.PRODUCT_ONLINE.getStatus()){
            //商品已经下架或被删除
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PRODUCT_OFFLINE_OR_DELETE.getStatus(),
                    StatusEnum.PRODUCT_OFFLINE_OR_DELETE.getMsg()
            );
        }

        ProductDetailVo productDateVo = new ProductDetailVo().product2vo(product);

        return ServerResponse.serverResponseBySuccess(null,productDateVo);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ServerResponse reduceStock(Integer productId, Integer quantity,int type) {

        if(productId==null||quantity==null){
            return ServerResponse.serverResponseByFail(StatusEnum.PARAM_NOT_EMPTY.getStatus(),StatusEnum.PARAM_NOT_EMPTY.getMsg());
        }

        //更新库存
        Product product=productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.serverResponseByFail(StatusEnum.PRODUCT_NOT_EXISTS.getStatus(),StatusEnum.PRODUCT_NOT_EXISTS.getMsg());
        }
        int count=productMapper.reduceStock(productId,type == 0 ? product.getStock()-quantity:product.getStock()+quantity);

//System.out.println(1/0);

        if(count<=0){
            throw new BusinessException(
                    StatusEnum.REDUCE_STOCK_FAIL.getStatus(),
                    StatusEnum.REDUCE_STOCK_FAIL.getMsg()
            );
        }

        return ServerResponse.serverResponseBySuccess(null,null);
    }
}
