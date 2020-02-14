package com.hh.demo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServerResponse<T> {

    private int status;
    private String msg;
    private T data;

    private ServerResponse(){}

    private ServerResponse(int status,String msg,T data){
        this.data = data;
        this.msg = msg;
        this.status = status;
    }


    //调用接口成功
    public static <T> ServerResponse serverResponseBySuccess(String msg, T data){

        return new ServerResponse(0,msg,data);

    }


    //调用接口失败
    public static ServerResponse serverResponseByFail(int status,String msg){
        return new ServerResponse(status,msg,null);
    }

    //判断是否成功
    @JsonIgnore
    public boolean isSuccess(){
        return this.status == 0;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
