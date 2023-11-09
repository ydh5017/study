package com.project.blog.mapper;

import com.project.blog.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    int userRegProc(UserVO userVO) throws Exception;

    boolean idCheck(String user_id) throws Exception;

    UserVO userInfo(String user_id);
}
