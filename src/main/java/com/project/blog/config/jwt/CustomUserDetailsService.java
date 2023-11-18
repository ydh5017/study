package com.project.blog.config.jwt;

import com.project.blog.mapper.UserMapper;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userSeq) throws UsernameNotFoundException {
        UserVO userVO = userMapper.userInfo(userSeq);
        return new CustomUserDetails(userVO);
    }
}
