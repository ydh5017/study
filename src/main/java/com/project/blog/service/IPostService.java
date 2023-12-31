package com.project.blog.service;

import com.project.blog.vo.PostVO;

import java.util.HashMap;
import java.util.List;

public interface IPostService {

    List<PostVO> getPostList(HashMap<String, Integer> hMap) throws Exception;

    int postCount() throws Exception;

    HashMap<String, String> postAddProc(PostVO postVO) throws Exception;

    PostVO postDetail(int postSeq) throws Exception;

    HashMap<String, String> postDelete(int postSeq) throws Exception;

    HashMap<String, String> postModProc(PostVO postVO) throws Exception;
}
