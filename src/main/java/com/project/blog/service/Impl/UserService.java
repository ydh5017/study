package com.project.blog.service.Impl;

import com.project.blog.config.jwt.JwtTokenProvider;
import com.project.blog.mapper.UserMapper;
import com.project.blog.service.IUserService;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    @Resource
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public HashMap<String, String> userRegProc(UserVO userVO) throws Exception {
        int result;
        String msg, url;
        HashMap<String, String> map = new HashMap<>();

        if (userMapper.idCheck(userVO.getUser_id())) {
            msg = "중복된 아이디입니다.";
            url = "/user/userReg";
        }else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            log.info("password : " + userVO.getPassword());
            userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));

            result = userMapper.userRegProc(userVO);

            if (result==1) {
                msg = "회원가입 성공";
                url = "/user";
            } else {
                msg = "회원가입 실패";
                url = "/user";
            }
        }


        map.put("msg", msg);
        map.put("url", url);

        return map;
    }

    @Override
    public String login(UserVO userVO) throws Exception {
        String user_id = userVO.getUser_id();
        String password = userVO.getPassword();

        userVO = userMapper.login(user_id);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(password, userVO.getPassword())) {
            String token = jwtTokenProvider.createToken(userVO.getUser_id(), userVO.getRole());

            return token;
        }

        return "x";
    }


}
