package com.hh.demo.common;

public enum StatusEnum {

    NO_LOGIN(100,"未登录"),
    NO_AUTHORITY(101,"无权限操作"),

    //用户
    PARAM_NOT_EMPTY(1,"参数不能为空"),
    USERNAME_NOT_EMPTY(2,"用户名不能为空"),
    PASSWORD_NOT_EMPTY(3,"密码不能为空"),
    EMAIL_NOT_EMPTY(4,"邮箱不能为空"),
    PHONE_NOT_EMPTY(5,"手机号不能为空"),
    QUESTION_NOT_EMPTY(6,"密保问题不能为空"),
    ANSWER_NOT_EMPTY(7,"密保答案不能为空"),
    USERNAME_EXISTS(8,"用户名已存在"),
    EMAIL_EXISTS(9,"邮箱已存在"),
    REGISTER_FAIL(10,"注册失败"),
    USERNAME_NOT_EXISTS(11,"用户名不存在"),
    PASSWORD_WRONG(12,"密码错误"),

    //种类
    CATEGORY_NAME_NOT_EMPTY(13,"商品类别名称不能为空"),
    CATEGORY_INSERT_FAIL(14,"商品类别添加失败"),
    CATEGORY_NOT_EXISTS(15,"商品类别不存在"),
    CATEGORY_UPDATE_FAIL(16,"商品类别数据更新失败"),
    CATEGORY_ID_NOT_EMPTY(17,"商品类别id为空"),
    CATEGORY_SELECT_FAIL(18,"商品类别查询失败"),

    //产品
    FILE_KIND_FAIL(19,"图片类别错误"),
    PRODUCT_NOT_EMPTY(20,"商品不能为空"),
    PRODUCT_NOT_EXISTS(21,"商品不存在"),
    PRODUCT_UPDATE_FAIL(22,"商品更新失败"),
    PRODUCT_INSERT_FAIL(23,"商品添加失败"),
    PRODUCT_NAME_NOT_EMPTY(24,"商品名称不能为空"),
    PRODUCT_PRICE_NOT_EMPTY(25,"商品价格不能为空"),
    PRODUCT_STOCK_NOT_EMPTY(26,"商品数量不能为空"),
    PRODUCT_OFFLINE_OR_DELETE(27,"商品已下架或删除"),


    PRODUCT_ADD_CART_FAIL(28,"商品加入购物车失败"),
    PRODUCT_UPDATE_CART_FAIL(29,"购物车商品更新失败"),
    PRODUCT_DELETE_CART_FAIL(30,"购物车商品删除失败"),

    //订单
    ADDRESS_NOT_EMPTY(31,"收货地址不能为空"),
    USER_CART_EXISTS(32,"用户购物车为空"),
    PRODUCT_STOCK_NOT_FULL(33,"商品库存不足"),
    ORDER_CREATE_FAIL(34,"订单拆功能键失败"),
    ORDER_ITEM_CREATE_FAIL(35,"订单明细创建失败"),
    REDUCE_STOCK_FAIL(36,"商品扣库存失败"),
    CART_CLEAN_FAIL(37,"购物车清空失败"),

    ORDER_NOT_EXISTS(38,"订单不存在"),
    ORDER_NOT_CANCEL(39,"订单无法取消"),
    ORDER_CANCEL_FAIL(40,"订单取消失败"),
    ADDRESS_ADD_FAIL(41,"地址添加失败"),

    ALIPAY_ORDER_FAIL(42,"支付宝预下单失败"),
    ORDER_STATUS_FAIL(43,"订单状态修改失败"),



    ;

    private int status;
    private String msg;

    StatusEnum(int status,String msg){
       this.msg = msg;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
