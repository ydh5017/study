package com.project.blog.mapper;

import com.project.blog.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    int userRegProc(UserVO userVO) throws Exception;

    boolean idCheck(String userId) throws Exception;

    UserVO loginInfo(String userId);

    UserVO userInfo(String userSeq);

    int userModProc(UserVO userVO) throws Exception;

    int passwordModProc(UserVO userVO) throws Exception;

    int updatePassword(String userId, String password) throws Exception;
}
