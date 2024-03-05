package com.project.blog.vo;

public class CategoryVO {

    private int cateSeq;
    private String cateCode;
    private String cateName;
    private String cateDepth;
    private String cateParent;
    private String isDeleted;
    private boolean isCate = false;

    public int getCateSeq() {
        return cateSeq;
    }

    public void setCateSeq(int cateSeq) {
        this.cateSeq = cateSeq;
    }

    public String getCateCode() {
        return cateCode;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateDepth() {
        return cateDepth;
    }

    public void setCateDepth(String cateDepth) {
        this.cateDepth = cateDepth;
    }

    public String getCateParent() {
        return cateParent;
    }

    public void setCateParent(String cateParent) {
        this.cateParent = cateParent;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isCate() {
        return isCate;
    }

    public void setCate(boolean cate) {
        isCate = cate;
    }
}
