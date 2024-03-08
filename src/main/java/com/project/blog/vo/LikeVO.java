package com.project.blog.vo;

public class LikeVO {

    private int likeSeq;
    private int userSeq;
    private int postSeq;
    private int commentSeq;
    private String likeDt;

    public int getLikeSeq() {
        return likeSeq;
    }

    public void setLikeSeq(int likeSeq) {
        this.likeSeq = likeSeq;
    }

    public int getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(int userSeq) {
        this.userSeq = userSeq;
    }

    public int getPostSeq() {
        return postSeq;
    }

    public void setPostSeq(int postSeq) {
        this.postSeq = postSeq;
    }

    public int getCommentSeq() {
        return commentSeq;
    }

    public void setCommentSeq(int commentSeq) {
        this.commentSeq = commentSeq;
    }

    public String getLikeDt() {
        return likeDt;
    }

    public void setLikeDt(String likeDt) {
        this.likeDt = likeDt;
    }
}
