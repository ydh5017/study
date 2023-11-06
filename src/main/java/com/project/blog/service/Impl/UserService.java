package com.project.blog.service.Impl;

import com.project.blog.mapper.UserMapper;
import com.project.blog.service.IUserService;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    @Resource
    private final UserMapper userMapper;

    @Override
    public HashMap<String, String> userRegProc(UserVO userVO) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        int result = userMapper.userRegProc(userVO);
        String msg, url;

        if (result==1) {
            msg = "회원가입 성공";
            url = "/user";
        } else {
            msg = "회원가입 실패";
            url = "/user";
        }
        map.put("msg", msg);
        map.put("url", url);

        return map;
    }
}
