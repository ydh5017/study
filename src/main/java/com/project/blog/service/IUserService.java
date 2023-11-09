package com.project.blog.service;

import com.project.blog.vo.UserVO;

import java.util.HashMap;

public interface IUserService {
    HashMap<String, String> userRegProc(UserVO userVO) throws Exception;

    HashMap<String, String> login(UserVO userVO) throws Exception;

    UserVO userInfo(String user_id) throws Exception;
}
