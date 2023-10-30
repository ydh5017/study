package com.project.blog.service;

import com.project.blog.vo.PostVO;

import java.util.HashMap;
import java.util.List;

public interface IPostService {

    List<PostVO> getPostList(HashMap<String, Integer> hMap) throws Exception;

    int postCount() throws Exception;

    HashMap<String, String> postAddProc(PostVO postVO) throws Exception;

    PostVO postDetail(int post_seq) throws Exception;

    HashMap<String, String> postDelete(int post_seq) throws Exception;
}
