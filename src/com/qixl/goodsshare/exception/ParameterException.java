package com.qixl.goodsshare.exception;

import java.util.HashMap;
import java.util.Map;

public class ParameterException extends Exception {

    private static final long serialVersionUID = -1515804795019947063L;
    
    Map<String,String> errorField = new HashMap<String,String>();

    public Map<String, String> getErrorField() {
        return errorField;
    }

    public void setErrorField(Map<String, String> errorField) {
        this.errorField = errorField;
    }
    
    public void addErrorField (String filedName,String message) {
        errorField.put(filedName, message);
    }
    
    public boolean isErrorField () {
        return errorField.isEmpty();
    }

}
