package com.project.blog.mapper;

import com.project.blog.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    int commentAddProc(CommentVO commentVO) throws Exception;

    int replyCommentAddProc(CommentVO commentVO) throws Exception;

    List<CommentVO> getCommentList(int postSeq) throws Exception;

    List<CommentVO> getReplyList(int commentSeq) throws Exception;

     int commentModProc(CommentVO commentVO) throws Exception;

     int commentDelete(int commentSeq) throws Exception;
}
