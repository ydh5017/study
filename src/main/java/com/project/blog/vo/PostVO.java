package com.project.blog.vo;

public class PostVO {

    private int postSeq;
    private String title;
    private String content;
    private String views;
    private String likes;
    private boolean isLikeUser = false;
    private String writeId;
    private String writeDt;
    private String chgId;
    private String chgDt;
    private String isDeleted;
    private boolean isWriter = false;

    public int getPostSeq() {
        return postSeq;
    }

    public void setPostSeq(int postSeq) {
        this.postSeq = postSeq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public boolean isLikeUser() {
        return isLikeUser;
    }

    public void setLikeUser(boolean likeUser) {
        isLikeUser = likeUser;
    }

    public String getWriteId() {
        return writeId;
    }

    public void setWriteId(String writeId) {
        this.writeId = writeId;
    }

    public String getWriteDt() {
        return writeDt;
    }

    public void setWriteDt(String writeDt) {
        this.writeDt = writeDt;
    }

    public String getChgId() {
        return chgId;
    }

    public void setChgId(String chgId) {
        this.chgId = chgId;
    }

    public String getChgDt() {
        return chgDt;
    }

    public void setChgDt(String chgDt) {
        this.chgDt = chgDt;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isWriter() {
        return isWriter;
    }

    public void setWriter() {
        isWriter = true;
    }
}
