package com.project.blog.mapper;

import com.project.blog.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int userRegProc(UserVO userVO) throws Exception;
}
