package com.hh.demo.entity.VO;

import com.hh.demo.entity.pojo.Cart;

import java.math.BigDecimal;
import java.util.List;

public class CartVO {

    private boolean allChecked;
    private BigDecimal cartTotalPrice;
    private List<CartProductVo> cartProductVoList;

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }


}
