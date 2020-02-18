package com.hh.demo.common;

public enum ProductStatusEnum {

    //商品状态
    PRODUCT_ONLINE(1,"在售"),
    PRODUCT_OFFLINE(2,"下架"),
    PRODUCT_DELETE(3,"已删除"),

    ;
    private int status;
    private String msg;

    ProductStatusEnum(int status,String msg){
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
