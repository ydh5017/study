package com.project.blog.mapper;
import com.project.blog.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PostMapper {

    List<PostVO> getPostList(HashMap<String, Object> hMap) throws Exception;

    List<PostVO> getSearchList(HashMap<String, Object> hMap) throws Exception;

    List<PostVO> getPopularPost(HashMap<String, Object> hMap) throws Exception;

    int postCount(HashMap<String, Object> map) throws Exception;

    int searchCount(HashMap<String, Object> map) throws Exception;

    int popularCount(HashMap<String, Object> map) throws Exception;

    void viewIncrease(int postSeq) throws Exception;

    int postAddProc(PostVO postVO) throws Exception;

    PostVO postDetail(int postSeq) throws Exception;

    void postDelete(HashMap<String, Object> pMap) throws Exception;

    int postModProc(PostVO postVO) throws Exception;

    void likeInc(int postSeq) throws Exception;

    void likeDec(int postSeq) throws Exception;
}
