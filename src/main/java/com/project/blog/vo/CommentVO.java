package com.project.blog.vo;

public class CommentVO {

    private int commentSeq;
    private int postSeq;
    private String content;
    private int likes;
    private String liker = null;
    private boolean isLikeUser = false;
    private int commentDepth = 0;
    private int commentGroup;
    private int replyCount;
    private boolean isCount = false;
    private String commentWriteId;
    private String commentWriteDt;
    private String commentChgId;
    private String commentChgDt;
    private String isDeleted;
    private boolean isReplyComment = false;
    private boolean isParentComment = false;
    private String writer = null;
    private boolean isWrite = false;

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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getLiker() {
        return liker;
    }

    public void setLiker(boolean isLikeUser) {
        if (isLikeUser){
            this.liker = "1";
            this.isLikeUser = true;
        }
    }

    public int getCommentDepth() {
        return commentDepth;
    }

    public void setCommentDepth(int commentDepth) {
        this.commentDepth = commentDepth;
    }

    public int getCommentGroup() {
        return commentGroup;
    }

    public void setCommentGroup(int commentGroup) {
        this.commentGroup = commentGroup;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        if (replyCount != 0) {
            this.isCount = true;
        }
        this.replyCount = replyCount;
    }

    public boolean isCount() {
        return isCount;
    }

    public void setCount(boolean count) {
            isCount = count;
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

    public boolean isReplyComment() {
        return isReplyComment;
    }

    public void setReplyComment(boolean replyComment) {
        isReplyComment = replyComment;
    }

    public boolean isParentComment() {
        return isParentComment;
    }

    public void setParentComment(boolean parentComment) {
        isParentComment = parentComment;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(boolean isWriter) {
        if (isWriter) {
            this.writer = "1";
            this.isWrite = true;
        }
    }
}
