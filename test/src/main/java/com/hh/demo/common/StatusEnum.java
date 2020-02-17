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
