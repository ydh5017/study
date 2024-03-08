package com.project.blog.mapper;

import com.project.blog.vo.LikeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikeMapper {

    void postLikeInc(int userSeq, int postSeq) throws Exception;

    void postLikeDec(int userSeq, int postSeq) throws Exception;

    boolean postLikeCheck(int userSeq, int postSeq) throws Exception;

    void commentLikeInc(int userSeq, int commentSeq) throws Exception;

    void commentLikeDec(int userSeq, int commentSeq) throws Exception;

    boolean commentLikeCheck(int userSeq, int commentSeq) throws Exception;

    List<LikeVO> getLikeInfo(String userSeq) throws Exception;
}
