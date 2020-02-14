package com.hh.demo.common;

public enum  RoleEnum {


    ADMIN(0,"管理员"),
    USER(1,"普通用户")
    ;

    private int status;
    private String role;

    RoleEnum(){};

    RoleEnum(int status,String role){
        this.role = role;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
