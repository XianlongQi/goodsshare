package com.qixl.goodsshare.exception;

public class ServiceException extends Exception{
    
    private static final long serialVersionUID = -3367972934290458862L;
    private int code;
    private String message;
    
    public ServiceException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
    
    public ServiceException( String message) {
        super();
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    

}
