package com.hh.demo.common;

public class Consts {

    public static final String USER="CURRENT_USER";

    public enum CartProductCheckEnum{

        CHECKED(1,"已选中"),
        UNCHECKED(0,"未选中"),

        ;

        private int check;
        private String desc;
        CartProductCheckEnum(int check,String desc){
            this.check = check;
            this.desc = desc;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}
