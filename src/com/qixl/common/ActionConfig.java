package com.qixl.common;

public class ActionConfig {
    
    private String clsName;
    private String methodName;
    
    
    public ActionConfig(String clsName, String methodName) {
        this.clsName = clsName;
        this.methodName = methodName;
    }
    public String getClsName() {
        return clsName;
    }
    public void setClsName(String clsName) {
        this.clsName = clsName;
    }
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    

}
