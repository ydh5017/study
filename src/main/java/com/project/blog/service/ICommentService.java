package com.project.blog.service;

import com.project.blog.vo.CommentVO;

import java.util.HashMap;
import java.util.List;

public interface ICommentService {

    HashMap<String, String> commentAddProc(CommentVO commentVO) throws Exception;

    List<CommentVO> getCommentList(int postSeq) throws Exception;

    List<CommentVO> getReplyList(int commentSeq) throws Exception;

    List<CommentVO> getMypageComment(String mypageType) throws Exception;

    HashMap<String, String> commentModProc(CommentVO commentVO) throws Exception;

    HashMap<String, String> commentDelete(int commentSeq, int postSeq) throws Exception;

    void commentLikeInc(int commentSeq) throws Exception;

    void commentLikeDec(int commentSeq) throws Exception;
}
