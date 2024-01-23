package com.project.blog.service;

import com.project.blog.vo.CommentVO;

import java.util.HashMap;

public interface ICommentService {

    HashMap<String, String> commentAddProc(CommentVO commentVO) throws Exception;
}
