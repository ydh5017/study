package com.project.blog.service;

import com.project.blog.vo.PostVO;

import java.util.HashMap;
import java.util.List;

public interface IPostService {

    List<PostVO> getPostList(HashMap<String, Object> hMap) throws Exception;

    List<PostVO> getSearchList(HashMap<String, Object> hMap) throws Exception;

    int postCount(String cateCode) throws Exception;

    int searchCount(String type, String keyword, String cateCode) throws Exception;

    HashMap<String, String> postAddProc(PostVO postVO) throws Exception;

    PostVO postDetail(int postSeq) throws Exception;

    HashMap<String, String> postDelete(int postSeq) throws Exception;

    HashMap<String, String> postModProc(PostVO postVO) throws Exception;

    void  postLikeInc(int postSeq) throws Exception;

    void  postLikeDec(int postSeq) throws Exception;
}
