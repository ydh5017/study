package com.project.blog.vo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserVO {

    private int userSeq;
    private String  userId;
    private String  password;
    private String  userName;
    private String  regId;
    private String  regDt;
    private String  chgId;
    private String  chgDt;
    private String  role;
    private boolean isReleased = false;

    public List<String> getRolesList() {
        if (this.role.length() > 0) {
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }

    public int getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(int userSeq) {
        this.userSeq = userSeq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isReleased() {
        return isReleased;
    }

    public void setReleased() {
        isReleased = true;
    }
}
