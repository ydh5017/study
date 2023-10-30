package com.project.blog.mapper;
import com.project.blog.vo.PostVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PostMapper {

    List<PostVO> getPostList(HashMap<String, Integer> hMap) throws Exception;

    int postCount() throws Exception;

    int postAddProc(PostVO postVO) throws Exception;

    PostVO postDetail(int post_seq) throws Exception;

    int postDelete(int result) throws Exception;
}
