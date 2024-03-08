package com.project.blog.service;

import com.project.blog.vo.CategoryVO;
import com.project.blog.vo.PostVO;

import java.util.HashMap;
import java.util.List;

public interface IPostService {

    List<PostVO> getPostList(HashMap<String, Object> hMap) throws Exception;

    List<PostVO> getPopularPost(String postType, String cateCode) throws Exception;

    List<PostVO> getMypagePost(String mypageType) throws Exception;

    int postCount(HashMap<String, Object> map) throws Exception;

    HashMap<String, String> postAddProc(PostVO postVO) throws Exception;

    PostVO postDetail(int postSeq) throws Exception;

    HashMap<String, String> postDelete(int postSeq) throws Exception;

    HashMap<String, String> postModProc(PostVO postVO) throws Exception;

    void  postLikeInc(int postSeq) throws Exception;

    void  postLikeDec(int postSeq) throws Exception;

    List<CategoryVO> getCateList(String cateCode) throws Exception;
}
