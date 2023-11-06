package com.project.blog.vo;

public class PostVO {

    private int post_seq;
    private String title;
    private String content;
    private String post_write_dt;
    private String post_chg_dt;
    private String post_deleted;

    public int getPost_seq() {
        return post_seq;
    }

    public void setPost_seq(int post_seq) {
        this.post_seq = post_seq;
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

    public String getPost_write_dt() {
        return post_write_dt;
    }

    public void setPost_write_dt(String post_write_dt) {
        this.post_write_dt = post_write_dt;
    }

    public String getPost_chg_dt() {
        return post_chg_dt;
    }

    public void setPost_chg_dt(String post_chg_dt) {
        this.post_chg_dt = post_chg_dt;
    }

    public String getPost_deleted() {
        return post_deleted;
    }

    public void setPost_deleted(String post_deleted) {
        this.post_deleted = post_deleted;
    }
}
