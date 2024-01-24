package com.project.blog.vo;

public class CommentVO {

    private int commentSeq;
    private int postSeq;
    private String content;
    private int commentDepth = 0;
    private String commentGroup;
    private String commentWriteId;
    private String commentWriteDt;
    private String commentChgId;
    private String commentChgDt;
    private String isDeleted;
    private boolean isCommentForComment = false;

    public CommentVO() {
        if (this.commentDepth == 1) {
            this.isCommentForComment = true;
        }
    }

    public int getCommentSeq() {
        return commentSeq;
    }

    public void setCommentSeq(int commentSeq) {
        this.commentSeq = commentSeq;
    }

    public int getPostSeq() {
        return postSeq;
    }

    public void setPostSeq(int postSeq) {
        this.postSeq = postSeq;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentDepth() {
        return commentDepth;
    }

    public void setCommentDepth(int commentDepth) {
        this.commentDepth = commentDepth;
    }

    public String getCommentGroup() {
        return commentGroup;
    }

    public void setCommentGroup(String commentGroup) {
        this.commentGroup = commentGroup;
    }

    public String getCommentWriteId() {
        return commentWriteId;
    }

    public void setCommentWriteId(String commentWriteId) {
        this.commentWriteId = commentWriteId;
    }

    public String getCommentWriteDt() {
        return commentWriteDt;
    }

    public void setCommentWriteDt(String commentWriteDt) {
        this.commentWriteDt = commentWriteDt;
    }

    public String getCommentChgId() {
        return commentChgId;
    }

    public void setCommentChgId(String commentChgId) {
        this.commentChgId = commentChgId;
    }

    public String getCommentChgDt() {
        return commentChgDt;
    }

    public void setCommentChgDt(String commentChgDt) {
        this.commentChgDt = commentChgDt;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isCommentForComment() {
        return isCommentForComment;
    }

    public void setCommentForComment(boolean commentForComment) {
        isCommentForComment = commentForComment;
    }
}
