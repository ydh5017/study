package com.project.blog.mapper;

import com.project.blog.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    int commentAddProc(CommentVO commentVO) throws Exception;
}
