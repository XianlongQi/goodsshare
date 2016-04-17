package com.qixl.goodsshare.model;

import com.qixl.goodsshare.util.PropertiesUtils;

public class Pagenation {
    
    public static final String KEY_PAGE_SIZE = "pagenation.pageSize";
    
    private int totalCount;
    
    private int pageSize;
    private int pageCount;
    private int curretnPage;
    private int offset;
    
    
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public int getPageSize() {
        if(pageSize == 0){
            String size = PropertiesUtils.getProperty(KEY_PAGE_SIZE);
            pageSize = Integer.parseInt(size);
        }
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageCount() {
        if(totalCount < 1){
            pageCount = 0;
            return pageCount;
        }
        pageCount = (totalCount - 1) / getPageSize() + 1;
        return pageCount;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public int getCurretnPage() {
        if(curretnPage < 1){
            curretnPage = 1;
        }
        return curretnPage;
    }
    public void setCurretnPage(int curretnPage) {
        this.curretnPage = curretnPage;
    }
    public int getOffset() {
        offset = (getCurretnPage() - 1) * getPageSize();
        return offset;
    }
    public void setOffset(int offset) {
        this.offset = offset;
    }
    public static String getKeyPageSize() {
        return KEY_PAGE_SIZE;
    }
    
    
    public boolean isFirstPage(){
        if(this.curretnPage <= 1){
            return true;
        }
        return false;
    }
    
    public boolean isLastPage(){
        if(this.curretnPage >= this.getPageCount()){
            return true;
        }
        return false;
    }

}
