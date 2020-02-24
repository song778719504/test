package com.hh.demo.service.impl;

import com.hh.demo.common.Consts;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.dao.CartMapper;
import com.hh.demo.entity.VO.CartProductVo;
import com.hh.demo.entity.VO.CartVO;
import com.hh.demo.entity.VO.ProductDetailVo;
import com.hh.demo.entity.pojo.Cart;
import com.hh.demo.service.ICartService;
import com.hh.demo.service.IProductService;
import com.hh.demo.utils.BigDecimalUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CartService implements ICartService {

    @Resource
    CartMapper cartMapper;
    @Resource
    IProductService productService;

    //查看购物车列表
    @Override
    public ServerResponse list(Integer userId) {

        CartVO cartVO = getCartVO(userId);
        return ServerResponse.serverResponseBySuccess(null,cartVO);
    }

    public CartVO getCartVO(Integer userId){
        CartVO cartVO = new CartVO();

        //根据用户id获得购物车信息
        List<Cart> cartList = cartMapper.findCartByUserId(userId);
        if (cartList == null || cartList.size() == 0){
            return cartVO;
        }

        //将List<CartVO> --> List<CartProductVO>
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        for (Cart cart : cartList){
            CartProductVo cartProductVo = new CartProductVo();

            cartProductVo.setId(cart.getId());
            cartProductVo.setProductId(cart.getProductId());
            cartProductVo.setProductChecked(cart.getChecked());
            cartProductVo.setUserId(cart.getUserId());

            //根据productId查询商品信息
            ServerResponse<ProductDetailVo> serverResponse = productService.detail(cart.getProductId());

            if (!serverResponse.isSuccess()){
                //商品下架或删除
                continue;
            }

            ProductDetailVo productDetailVo = serverResponse.getData();

            cartProductVo.setProductMainImage(productDetailVo.getMainImage());
            cartProductVo.setProductName(productDetailVo.getName());
            cartProductVo.setProductPrice(productDetailVo.getPrice());
            cartProductVo.setProductSubtitle(productDetailVo.getSubtitle());
            cartProductVo.setProductStock(productDetailVo.getStock());
            cartProductVo.setProductStatus(productDetailVo.getStatus());

            Integer quantityInCart = cart.getQuantity();
            Integer realStock = productDetailVo.getStock();

            if (realStock >= quantityInCart){
                //库存充足
                cartProductVo.setLimitQuantity("LIMIT_NUM_SUCCESS");
                cartProductVo.setQuantity(cart.getQuantity());
            }else {
                //库存不足
                //修改该商品再购物车中的数量，根据商品id修改购物车的quantity
                int count = cartMapper.updateQuantityByProductId(cartProductVo.getProductId(),realStock);

                if (count == 0){
                    //修改失败
                    continue;
                }

                cartProductVo.setLimitQuantity("LIMIT_NUM_FAIL");
                cartProductVo.setQuantity(realStock);
            }
            //价格计算
            cartProductVo.setProductTotalPrice(
                    BigDecimalUtil.multi(
                            String.valueOf(productDetailVo.getPrice().doubleValue()),
                            String.valueOf(cartProductVo.getQuantity())

                    )
            );
            if (cartProductVo.getProductChecked() == Consts.CartProductCheckEnum.CHECKED.getCheck()){
                //已选中
                cartTotalPrice = BigDecimalUtil.add(
                        String.valueOf(cartTotalPrice),
                        String.valueOf(cartProductVo.getProductTotalPrice())
                );
            }

            cartProductVoList.add(cartProductVo);
            //
        }

        cartVO.setCartProductVoList(cartProductVoList);

        //计算购物车总价格
        cartVO.setCartTotalPrice(cartTotalPrice);

        //验证时候全选

        int result = cartMapper.totalCountByUnchecked(userId);

        if (result > 0){
            cartVO.setAllChecked(false);
        }else {
            cartVO.setAllChecked(true);
        }


        return cartVO;
    }

    @Override
    public ServerResponse add(Integer userId, Integer productId, Integer count) {


        //参数非空
        if (productId == null || count == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PARAM_NOT_EMPTY.getStatus(),
                    StatusEnum.PARAM_NOT_EMPTY.getMsg()
            );
        }
        //根据productId查询商品是否存在
        ServerResponse<ProductDetailVo> serverResponse = productService.detail(productId);
        if (!serverResponse.isSuccess()){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PRODUCT_OFFLINE_OR_DELETE.getStatus(),
                    StatusEnum.PRODUCT_OFFLINE_OR_DELETE.getMsg()
            );
        }
        //根据userId和productId去购物查询
        Cart cart = cartMapper.findCartByUserIdAndProductId(userId,productId);

        if (cart == null){
            //该商品第一次加入购物车
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setChecked(Consts.CartProductCheckEnum.CHECKED.getCheck());
            newCart.setProductId(productId);
            newCart.setQuantity(count);

            int result = cartMapper.insert(newCart);
            if (result > 0){
                //添加成功
                return ServerResponse.serverResponseBySuccess(null,getCartVO(userId));
            }else {
                //添加失败
                return ServerResponse.serverResponseByFail(
                        StatusEnum.PRODUCT_ADD_CART_FAIL.getStatus(),
                        StatusEnum.PRODUCT_ADD_CART_FAIL.getMsg()
                );
            }
        }else {
            //该商品已经在购物车中
            cart.setQuantity(cart.getQuantity() + count);
            int result = cartMapper.updateByPrimaryKey(cart);
            if (result > 0){
                //添加成功
                return ServerResponse.serverResponseBySuccess(null,getCartVO(userId));
            }else {
                //添加失败
                return ServerResponse.serverResponseByFail(
                        StatusEnum.PRODUCT_UPDATE_CART_FAIL.getStatus(),
                        StatusEnum.PRODUCT_UPDATE_CART_FAIL.getMsg()
                );
            }
        }
    }

    @Override
    public ServerResponse update(Integer userId, Integer productId, Integer count) {
        //参数非空
        if (productId == null || count == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PARAM_NOT_EMPTY.getStatus(),
                    StatusEnum.PARAM_NOT_EMPTY.getMsg()
            );
        }
        //根据productId查询商品是否存在
        ServerResponse<ProductDetailVo> serverResponse = productService.detail(productId);
        if (!serverResponse.isSuccess()){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PRODUCT_OFFLINE_OR_DELETE.getStatus(),
                    StatusEnum.PRODUCT_OFFLINE_OR_DELETE.getMsg()
            );
        }
        //根据userId和productId去购物查询
        Cart cart = cartMapper.findCartByUserIdAndProductId(userId,productId);
        if (cart == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PRODUCT_UPDATE_CART_FAIL.getStatus(),
                    StatusEnum.PRODUCT_UPDATE_CART_FAIL.getMsg()
            );
        }else {
            cartMapper.updateQuantityByProductId(productId,count);
            return ServerResponse.serverResponseBySuccess(null,getCartVO(userId));
        }

    }

    @Override
    public ServerResponse delete(Integer userId, String productIds) {
        //参数非空判断
        if (productIds == null || "".equals(productIds)){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PARAM_NOT_EMPTY.getStatus(),
                    StatusEnum.PARAM_NOT_EMPTY.getMsg()
            );
        }
        String[] s = productIds.split(",");
        for (int i = 0; i<s.length-1;i++){
            System.out.println(s[i]);
            if (!isNumeric(s[i]) || s[i] == null || "".equals(s[i])){
                return ServerResponse.serverResponseByFail(
                        StatusEnum.PARAM_NOT_EMPTY.getStatus(),
                        StatusEnum.PARAM_NOT_EMPTY.getMsg()
                );
            }
        }
        productIds = "(" + productIds + ")";
        int result = cartMapper.deleteByUserIdAndProductIds(userId,productIds);
        if (result < 0 ){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PRODUCT_DELETE_CART_FAIL.getStatus(),
                    StatusEnum.PRODUCT_DELETE_CART_FAIL.getMsg()
            );
        }
        return ServerResponse.serverResponseBySuccess(null,getCartVO(userId));
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    @Override
    public ServerResponse findCartByUserIdAndChecked(Integer userId) {

        //登录判断
        //需要判断嘛？传进来不肯定是登录过的嘛
        if (userId == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.NO_LOGIN.getStatus(),
                    StatusEnum.NO_LOGIN.getMsg()
            );
        }
        List<Cart> cartList = cartMapper.findCartByUserIdAndChecked(userId);

        return ServerResponse.serverResponseBySuccess(null,cartList);
    }

    @Override
    public ServerResponse deleteBatchByIds(List<Cart> cartList) {

        if (cartList == null || cartList.size() == 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PARAM_NOT_EMPTY.getStatus(),
                    StatusEnum.PARAM_NOT_EMPTY.getMsg()
            );
        }

        int result  = cartMapper.deleteBatch(cartList);

        if (result <= 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.CART_CLEAN_FAIL.getStatus(),
                    StatusEnum.CART_CLEAN_FAIL.getMsg()
            );
        }
        return ServerResponse.serverResponseBySuccess(null,null);
    }
}
