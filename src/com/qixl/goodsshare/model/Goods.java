package com.qixl.goodsshare.model;

public class Goods {
    
    private int id;
    private String name;
    private String author;
    private String desc;
    private int ownerId;
    private int currentId;
    private String ownerName;
    private String currentOwnerName;
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
    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
