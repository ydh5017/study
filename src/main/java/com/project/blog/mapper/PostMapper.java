package com.project.blog.mapper;
import com.project.blog.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PostMapper {

    List<PostVO> getPostList(HashMap<String, Object> hMap) throws Exception;

    List<PostVO> getSearchList(HashMap<String, Object> hMap) throws Exception;

    List<PostVO> getPopularPost(String cateCode) throws Exception;

    int postCount(String cateCode) throws Exception;

    int searchCount(String type, String keyword, String cateCode) throws Exception;

    void viewIncrease(int postSeq) throws Exception;

    int postAddProc(PostVO postVO) throws Exception;

    PostVO postDetail(int postSeq) throws Exception;

    void postDelete(HashMap<String, Object> pMap) throws Exception;

    int postModProc(PostVO postVO) throws Exception;

    void likeInc(int postSeq) throws Exception;

    void likeDec(int postSeq) throws Exception;
}
