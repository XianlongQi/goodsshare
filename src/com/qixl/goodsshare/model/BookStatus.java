package com.qixl.goodsshare.model;

public enum BookStatus {
    all("all"),
    out("out"),
    in("in"),
    borrow("borrow");
    
    private String value;
    public String getValue(){
        return this.value;
    }
    private BookStatus(String value){
        this.value = value;
    }

}
