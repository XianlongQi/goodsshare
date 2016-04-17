package com.qixl.goodsshare.model;

import java.sql.Date;

public class BorrowRequest {
    
    private int goodsId;
    private String name;
    private String author;
    private String desc;
    private int ownerId;
    private int currentId;
    private String ownerName;
    private String currentOwnerName;
    
    private int borrowRequestId;
    
    private int borrowUserId;
    private String borrowUserName;
    private String borrowUserPhone;
    private Date borrowTime;
    
    public int getBorrowRequestId() {
        return borrowRequestId;
    }
    public void setBorrowRequestId(int borrowRequestId) {
        this.borrowRequestId = borrowRequestId;
    }
    public int getBorrowUserId() {
        return borrowUserId;
    }
    public void setBorrowUserId(int borrowUserId) {
        this.borrowUserId = borrowUserId;
    }
    public String getBorrowUserName() {
        return borrowUserName;
    }
    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }
    public String getBorrowUserPhone() {
        return borrowUserPhone;
    }
    public void setBorrowUserPhone(String borrowUserPhone) {
        this.borrowUserPhone = borrowUserPhone;
    }
    public Date getBorrowTime() {
        return borrowTime;
    }
    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }
    
    
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getCurrentOwnerName() {
        return currentOwnerName;
    }
    public void setCurrentOwnerName(String currentOwnerName) {
        this.currentOwnerName = currentOwnerName;
    }
    
    public int getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
    
    private String picture;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public int getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    public int getCurrentId() {
        return currentId;
    }
    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    

}
