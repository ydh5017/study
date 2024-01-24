package com.project.blog.service;

import com.project.blog.vo.CommentVO;

import java.util.HashMap;
import java.util.List;

public interface ICommentService {

    HashMap<String, String> commentAddProc(CommentVO commentVO) throws Exception;

    List<CommentVO> getCommentList(int postSeq) throws Exception;
}
