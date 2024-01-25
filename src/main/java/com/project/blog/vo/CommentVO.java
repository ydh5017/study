package com.project.blog.vo;

public class CommentVO {

    private int commentSeq;
    private int postSeq;
    private String content;
    private int commentDepth = 0;
    private int commentGroup;
    private String commentWriteId;
    private String commentWriteDt;
    private String commentChgId;
    private String commentChgDt;
    private String isDeleted;
    private boolean isReplyComment = false;
    private boolean isParentComment = false;

    public CommentVO(int commentSeq, int postSeq, String content, int commentDepth,
                     String  commentGroup, String commentWriteId, String commentWriteDt,
                     String commentChgId, String commentChgDt, String isDeleted) {
        this.commentSeq = commentSeq;
        this.postSeq = postSeq;
        this.content = content;
        this.commentDepth = commentDepth;
        if (commentGroup != null) {
            this.commentGroup = Integer.parseInt(commentGroup);
        }
        this.commentWriteId = commentWriteId;
        this.commentWriteDt = commentWriteDt;
        this.commentChgId = commentChgId;
        this.commentChgDt = commentChgDt;
        this.isDeleted = isDeleted;
        if (commentDepth == 0) {
            this.isParentComment = true;
        }else {
            this.isReplyComment = true;
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

    public int getCommentGroup() {
        return commentGroup;
    }

    public void setCommentGroup(int commentGroup) {
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
}
