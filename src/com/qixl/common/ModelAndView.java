package com.qixl.common;
//为了把response从userController中移出来
public class ModelAndView {
    private String view;
    private boolean rediret = false;
    public String getView() {
        return view;
    }
    public void setView(String view) {
        this.view = view;
    }
    public boolean isRediret() {
        return rediret;
    }
    public void setRediret(boolean rediret) {
        this.rediret = rediret;
    }
    
    
    
}
